/*
 * Copyright (C) 2018-2019 LEIDOS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package gov.usdot.cv.msg.rsmconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import static gov.usdot.cv.msg.kml.KmlDocumentBuilder.*;

import gov.usdot.cv.msg.rsm.AreaType;
import gov.usdot.cv.msg.rsm.NodePointLle;
import gov.usdot.cv.msg.rsm.Path;
import gov.usdot.cv.msg.rsm.Position3D;
import gov.usdot.cv.msg.rsm.RoadsideSafetyMessage;
import gov.usdot.cv.msg.rsm.RsmLane;

/**
 * Converter for {@link RoadsideSafetyMessage} into a formatted KML document
 * <p>
 * Renders the reference point of an RSM as a single point, then renders each lane
 * in both the approach geometry and reduced speed zone as a line string and points.
 */
public class RsmKmlGenerator {
    protected static final double MICRODEGREES_TO_DEGREES = 1.0 / 10000000.0;
    protected static final String WZ_STYLE_ID = "workzonestyle";
    protected static final String APPROACH_STYLE_ID = "approachstyle";
    protected static final String WZ_STYLE_COLOR = "641400FF";
    protected static final String APPROACH_STYLE_COLOR = "64F00A14";

    /**
     * Convert a single RSM lane into a line string placemark with point placemarks at each vertex
     * 
     * @param kmlDoc The document being built
     * @param lane The lane from the RSM to be converted
     * @param refPoint The reference point from the RSM
     * @param scaleFactor The offset scaling factor from the RSM
     * @param approach True if the RSMlane is an approach, false o.w.
     */
    protected static void convertRsmLane(Document kmlDoc, RsmLane lane, Position3D refPoint, int scaleFactor, boolean approach) {
        String laneName = lane.getLaneName().orElse("Lane ID=(" + lane.getLaneId() + ")");
        Element folderEle = createFolder(kmlDoc, laneName);
        String styleId = (approach ? APPROACH_STYLE_ID : WZ_STYLE_ID);

        List<NodePointLle> points = lane.getLaneGeometry().map(laneGeo -> {
            return laneGeo.getNodeSet().map(nodeSet -> {
                return nodeSet.stream().map(node -> {
                    return node.getNodePoint();
                }).collect(Collectors.toList());
            });
        }).get().orElse(new ArrayList<NodePointLle>());

        Element linePlacemark = createPlacemark(folderEle, laneName + " trace");
        assignStyleToPlacemark(linePlacemark, styleId);
        Element traceEle = addPolylinetoPlacemark(linePlacemark);

        Position3D prev[] = { refPoint };
        for (int i = 0; i < points.size(); i++) {
            Element placemarkEle = createPlacemark(folderEle, laneName + ": Point #" + (i + 1) + "/" + points.size());

            NodePointLle point = points.get(i);
            point.getOffset().ifPresent(offset -> {
                int newLat = prev[0].getLat() + offset.getLatOffset() * scaleFactor;
                int newLon = prev[0].getLon() + offset.getLonOffset() * scaleFactor;
                int newElev = prev[0].getElev() + offset.getElevOffset() * scaleFactor;

                addPointToPlacemark(placemarkEle, newLat * MICRODEGREES_TO_DEGREES, newLon * MICRODEGREES_TO_DEGREES,
                        newElev);

                addPointToPolyline(traceEle, newLat * MICRODEGREES_TO_DEGREES, newLon * MICRODEGREES_TO_DEGREES,
                        newElev);

                prev[0] = new Position3D(newLat, newLon, newElev);
            });

            point.getPosition().ifPresent(pos -> {
                addPointToPlacemark(placemarkEle, pos.getLat() * MICRODEGREES_TO_DEGREES,
                        pos.getLon() * MICRODEGREES_TO_DEGREES, pos.getElev());

                addPointToPolyline(traceEle, pos.getLat() * MICRODEGREES_TO_DEGREES,
                        pos.getLon() * MICRODEGREES_TO_DEGREES, pos.getElev());

                prev[0] = pos;
            });
        }
    }

    /**
     * Convert the {@link AreaType} element from the RSM depending on what type is present
     * 
     * @param kml The KML document being built
     * @param areaType The parsed AreaType from the RSM
     * @param refPoint The reference point from the RSM
     * @param approach True if this is an approach region, false o.w.
     */
    protected static void convertAreaType(Document kml, AreaType areaType, Position3D refPoint, boolean approach) {
        String styleId = (approach ? APPROACH_STYLE_ID : WZ_STYLE_ID);
        areaType.getRoadwayGeometry().ifPresent(rg -> {
            for (RsmLane lane : rg.getRsmLanes()) {
                convertRsmLane(kml, lane, refPoint, rg.getScale().orElse(1), approach);
            }
        });

        areaType.getPaths().ifPresent(paths -> {
            for (int i = 0; i < paths.size(); i++) {
                Path p = paths.get(i);
                Element pathFolder = createFolder(kml, "Path #" + i);
                Element linePlacemark = createPlacemark(pathFolder, "Path #" + i + " trace");
                assignStyleToPlacemark(linePlacemark, styleId);

                Element traceEle = addPolylinetoPlacemark(linePlacemark);
                for (int j = 0; j < p.getPathPoints().size(); j++) {
                    Element placemarkEle = createPlacemark(pathFolder,
                            "Path #" + i + " Point #" + j + "/" + p.getPathPoints().size());
                    Position3D pos = p.getPathPoints().get(j);
                    addPointToPlacemark(placemarkEle, pos.getLat() * MICRODEGREES_TO_DEGREES,
                            pos.getLon() * MICRODEGREES_TO_DEGREES, pos.getElev());

                    addPointToPolyline(traceEle, pos.getLat() * MICRODEGREES_TO_DEGREES,
                            pos.getLon() * MICRODEGREES_TO_DEGREES, pos.getElev());
                }
            }
        });

        areaType.getBroadRegion().ifPresent(br -> {
            // CIRCLES CANNOT BE RENDERED IN KML, SO CIRCLE IS NOT HANDLED HERE
            br.getPolygon().ifPresent(poly -> {
                Element pathFolder = createFolder(kml, "Broad Region: Polygon");
                Element linePlacemark = createPlacemark(pathFolder, "Broad Region: Polgyon trace");
                assignStyleToPlacemark(linePlacemark, styleId);
                Element traceEle = addPolylinetoPlacemark(linePlacemark);
                Position3D prev[] = { refPoint };
                for (int i = 0; i < poly.size(); i++) {
                    Element placemarkEle = createPlacemark(pathFolder,
                            "Broad Region: Polygon Point #" + i + "/" + poly.size());

                    NodePointLle point = poly.get(i);
                    point.getOffset().ifPresent(offset -> {
                        int newLat = prev[0].getLat() + offset.getLatOffset();
                        int newLon = prev[0].getLon() + offset.getLonOffset();
                        int newElev = prev[0].getElev() + offset.getElevOffset();

                        addPointToPlacemark(placemarkEle, newLat * MICRODEGREES_TO_DEGREES,
                                newLon * MICRODEGREES_TO_DEGREES, newElev);

                        addPointToPolyline(traceEle, newLat * MICRODEGREES_TO_DEGREES, newLon * MICRODEGREES_TO_DEGREES,
                                newElev);

                        prev[0] = new Position3D(newLat, newLon, newElev);
                    });

                    point.getPosition().ifPresent(pos -> {
                        addPointToPlacemark(placemarkEle, pos.getLat() * MICRODEGREES_TO_DEGREES,
                                pos.getLon() * MICRODEGREES_TO_DEGREES, pos.getElev());

                        addPointToPolyline(traceEle, pos.getLat() * MICRODEGREES_TO_DEGREES,
                                pos.getLon() * MICRODEGREES_TO_DEGREES, pos.getElev());

                        prev[0] = pos;
                    });
                }
            });
        });
    }

    /**
     * Convert the parsed RSM to a KML document
     * 
     * @param rsm The RSM to convert
     * @return The KML document containing the reference point and all lanes from the RSM
     */
    protected static Document convertRsmToKml(RoadsideSafetyMessage rsm) {
        Document kml = buildKmlDocumentRoot();
        Element approachStyle = createStyle(kml, APPROACH_STYLE_ID);
        addLineColorToStyle(approachStyle, APPROACH_STYLE_COLOR);
        Element wzStyle = createStyle(kml, WZ_STYLE_ID);
        addLineColorToStyle(wzStyle, WZ_STYLE_COLOR);

        Position3D refPoint = rsm.getCommonContainer().getRegionInfo().getReferencePoint();
        Element refPointEle = createPlacemark(kml, "Reference Point");
        addPointToPlacemark(refPointEle, refPoint.getLat() * MICRODEGREES_TO_DEGREES,
                refPoint.getLon() * MICRODEGREES_TO_DEGREES, refPoint.getElev());

        return convertRsmToKml(kml, rsm);
    }

    /**
     * Convert a second RSM into an existing document
     * <p>
     * Used to combine multipart RSMs into a single KML document
     * 
     * @param kml The document in progress after being initialized by {@link RsmKmlGenerator#convertRsmToKml(RoadsideSafetyMessage)}
     * @param rsm The new RSM to include in this document
     * @return The document after the new RSM has been added
     */
    protected static Document convertRsmToKml(Document kml, RoadsideSafetyMessage rsm) {
        Position3D refPoint = rsm.getCommonContainer().getRegionInfo().getReferencePoint();
        Optional<AreaType> areaType = rsm.getCommonContainer().getRegionInfo().getAreaType();

        areaType.ifPresent(at -> {
            convertAreaType(kml, at, refPoint, true);
        });

        // Handle RSZ Container
        rsm.getReducedSpeedZoneContainer().ifPresent(rsz -> {
            rsz.getRszRegion().ifPresent(rszRegion -> convertAreaType(kml, rszRegion, refPoint, false));
        });

        return kml;
    }

    /**
     * Stitch together the segments of a multipart RSM and generate a single KML document
     * 
     * @param rsmSegments The list of segments to convert
     * @return A KML document containing all the RSM segments
     */
    protected static Document convertMultipartRsmToKml(List<RoadsideSafetyMessage> rsmSegments) {
        if (rsmSegments.size() < 1) {
            throw new RuntimeException("Segment based KML conversion called with no segments");
        }

        Document kmlDoc = convertRsmToKml(rsmSegments.get(0));
        for (int i = 1; i < rsmSegments.size(); i++) {
            convertRsmToKml(kmlDoc, rsmSegments.get(i));
        }

        return kmlDoc;
    }

    /**
     * Convert the RSM to KML and return the formatted String representation of that document
     * 
     * @param rsm The parsed RSM to convert
     * @return The formatted String containing the KML
     */
    public static String convertRsmToKmlString(RoadsideSafetyMessage rsm) {
        Document kmlDoc = convertRsmToKml(rsm);
        return new XMLOutputter(Format.getPrettyFormat()).outputString(kmlDoc);
    }

    /**
     * Convert the RSM to KML and write it to afile
     * 
     * @param rsm The parsed RSM to convert
     * @param outputFile The file pointer to write to
     * 
     * @throws FileNotFoundException If the file cannot be accessed
     * @throws IOException if the write to disk fails
     * 
     */
    public static void convertRsmToKmlFile(RoadsideSafetyMessage rsm, File outputFile) throws FileNotFoundException, IOException {
        Document kmlDoc = convertRsmToKml(rsm);
        new XMLOutputter(Format.getPrettyFormat()).output(kmlDoc, new FileOutputStream(outputFile));
    }

    /**
     * Convert the group of segmented RSMs to KML and write it to afile
     * 
     * @param rsmSegments The parsed RSM segments to convert
     * @param outputFile The file pointer to write to
     * 
     * @throws FileNotFoundException If the file cannot be accessed
     * @throws IOException if the write to disk fails
     * 
     */
    public static void convertRsmToKmlFile(List<RoadsideSafetyMessage> rsmSegments, File outputFile) throws FileNotFoundException, IOException {
        Document kmlDoc = convertMultipartRsmToKml(rsmSegments);
        new XMLOutputter(Format.getPrettyFormat()).output(kmlDoc, new FileOutputStream(outputFile));
    }

    /**
     * Convert the RSM to KML and return the formatted String representation of that document
     * 
     * @param rsmSegments The parsed RSM segments to convert
     * @return The formatted String containing the KML
     */
    public static String convertRsmToKmlString(List<RoadsideSafetyMessage> rsmSegments) {
        Document kmlDoc = convertMultipartRsmToKml(rsmSegments);
        return new XMLOutputter(Format.getPrettyFormat()).outputString(kmlDoc);
    }
}
