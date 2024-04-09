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

package gov.usdot.cv.msg.rsmparser;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import gov.usdot.cv.msg.rsm.*;

/**
 * JDOM based implementation class for {@link RsmParser}
 */
public class JdomRsmParser {
    private static final String COMMON_CONTAINER_START_TAG = "commonContainer";
    private static final String VERSION_TAG = "version";
    private static final String REDUCED_SPEED_ZONE_CONTAINER_START_TAG = "rszContainer";

    /**
     * Load a file into the JdomRsmParser
     * 
     * @param path A string path to the file to be loaded
     * @return A Document instance representing the XML structure of the file
     * @throws IOException           If the file is unable to be opened
     * @throws JDOMException Thrown if the XML is not properly formatted for processing 
     */
    protected Document loadFile(String path) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        Document rsmDoc = builder.build(new File(path));

        return rsmDoc;
    }

    /**
     * Load a file into the JdomRsmParser
     * 
     * @param rsmFile The {@link File} instance already opened for the RSM data
     * @return A Document instance representing the XML structure of the file
     * @throws IOException           If the file is unable to be opened
     * @throws JDOMException Thrown if the XML is not properly formatted for processing 
     */
    protected Document loadFile(File rsmFile) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        Document rsmDoc = builder.build(rsmFile);
        return rsmDoc;
    }


    protected Document loadString(String rsmXmlString) throws JDOMException {
        SAXBuilder builder = new SAXBuilder();
        Document rsmDoc;
        try {
            rsmDoc = builder.build(new StringReader(rsmXmlString));
        } catch (IOException e) {
            // Should never hit this branch because of using a StringReader
            throw new JDOMException("Input data format invalid.");
        }
        return rsmDoc;
    }

    /**
     * Locate the first element of the specified name inside the XML document
     * 
     * @param rsmDoc The document to search
     * @param elementName The string name of the element to look for
     * 
     * @return An {@link Optional} instance containing the desired {@link Element} if it was found
     */
    protected Optional<Element> locateElementByName(Document rsmDoc, String elementName) {
        XPathFactory xPathFactory = XPathFactory.instance();
        XPathExpression<Element> rsmGeometryXpe = xPathFactory.compile("//*[local-name() = '" + elementName + "']", Filters.element());
        return Optional.ofNullable(rsmGeometryXpe.evaluateFirst(rsmDoc));
    }

    /**
     * Helper method to destructure the RSM XML document after it's been loaded
     * 
     * @param rsmDoc The JDOM document to begin decoding
     * 
     * @return The parsed contents of the document
     */
    protected RoadsideSafetyMessage parseDocument(Document rsmDoc) throws JDOMException {
        int version = Integer.parseInt(locateElementByName(rsmDoc, VERSION_TAG).get().getText());
        Element commonContainerElement = locateElementByName(rsmDoc, COMMON_CONTAINER_START_TAG).get();
        CommonContainer commonContainer = convertCommonContainer(commonContainerElement);
        Optional<ReducedSpeedZoneContainer> reducedSpeedZoneContainer = locateElementByName(rsmDoc, REDUCED_SPEED_ZONE_CONTAINER_START_TAG)
            .map(this::convertReducedSpeedZoneContainer);

        return new RoadsideSafetyMessage(version, commonContainer, reducedSpeedZoneContainer);
    }

    /**
     * Convert an input XML formatted RSM file into a parsed RSM representation
     * containing the data from the CommonContainer and from the ReducedSpeedZoneContainer
     * 
     * @param f The XML file to be parsed
     * @return An {@link RsmGeometry} instance containing the data of the XML file
     *         if the parse was successful
     * @throws IOException           If the file is unable to be opened
     * @throws JDOMException Thrown if the XML is not properly formatted for processing 
     */
    public RoadsideSafetyMessage parse(File f) throws JDOMException, IOException {
        return parseDocument(loadFile(f));
    }

    /**
     * Convert an input XML formatted RSM string into a parsed RSM representation
     * containing the data from the CommonContainer and from the ReducedSpeedZoneContainer
     * 
     * @param rsmString The String containing the RSM XML
     * @return An {@link RsmGeometry} instance containing the data of the XML file
     *         if the parse was successful
     * @throws IOException           If the file is unable to be opened
     * @throws JDOMException Thrown if the XML is not properly formatted for processing 
     */
    public RoadsideSafetyMessage parse(String rsmString) throws JDOMException {
        return parseDocument(loadString(rsmString));
    }

    /**
     * Convert the XML element representing the RSM CommonContainer into a Java object
     * 
     * @param commonContainerElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected CommonContainer convertCommonContainer(Element commonContainerElement) {
        EventInfo eventInfo = convertEventInfo(commonContainerElement.getChild("eventInfo"));
        RegionInfo regionInfo = convertRegionInfo(commonContainerElement.getChild("regionInfo"));
        return new CommonContainer(eventInfo, regionInfo);
    }

    /**
     * Convert the XML element representing the RSM EventInfo element into a Java object
     * 
     * @param eventInfoElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected EventInfo convertEventInfo(Element eventInfoElement) {
        String eventId = eventInfoElement.getChildText("eventId");
        Optional<MsgSegmentInfo> msgSegmentInfo = Optional.ofNullable(eventInfoElement.getChild("msgSegmentInfo"))
            .map(this::convertMsgSegmentInfo);
        DDateTime startDateTime = convertDDateTime(eventInfoElement.getChild("startDateTime"));
        Optional<DDateTime> endDateTime = Optional.ofNullable(eventInfoElement.getChild("endDateTime"))
            .map(this::convertDDateTime);
        Optional<List<EventRecurrence>> eventRecurrence = Optional.ofNullable(eventInfoElement.getChild("eventRecurrence"))
            .map(el -> el.getChildren())
            .map(children -> children.stream()
                .map(this::convertEventRecurrence)
                .collect(Collectors.toList()));

        Integer causeCode = Integer.parseInt(eventInfoElement.getChildText("causeCode"));
        Optional<Integer> subCauseCode = Optional.ofNullable(eventInfoElement.getChildText("subCauseCode"))
            .map(Integer::parseInt);
        
        return new EventInfo(eventId, msgSegmentInfo, startDateTime, endDateTime, eventRecurrence, causeCode, subCauseCode);
    }

    /**
     * Convert the XML element representing the RSM EventRecurrence element into a java object
     * 
     * @param eventRecurrenceElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected EventRecurrence convertEventRecurrence(Element eventRecurrenceElement) {
        Optional<DTime> startTime = Optional.ofNullable(eventRecurrenceElement.getChild("startTime"))
            .map(this::convertDTime);
        Optional<DTime> endTime = Optional.ofNullable(eventRecurrenceElement.getChild("endTime"))
            .map(this::convertDTime);
        Optional<DDate> startDate = Optional.ofNullable(eventRecurrenceElement.getChild("startDate"))
            .map(this::convertDDate);
        Optional<DDate> endDate = Optional.ofNullable(eventRecurrenceElement.getChild("endDate"))
            .map(this::convertDDate);

        boolean monday = Boolean.parseBoolean(eventRecurrenceElement.getChildText("monday"));
        boolean tuesday = Boolean.parseBoolean(eventRecurrenceElement.getChildText("tuesday"));
        boolean wednesday = Boolean.parseBoolean(eventRecurrenceElement.getChildText("wednesday"));
        boolean thursday = Boolean.parseBoolean(eventRecurrenceElement.getChildText("thursday"));
        boolean friday = Boolean.parseBoolean(eventRecurrenceElement.getChildText("friday"));
        boolean saturday = Boolean.parseBoolean(eventRecurrenceElement.getChildText("saturday"));
        boolean sunday = Boolean.parseBoolean(eventRecurrenceElement.getChildText("sunday"));
        boolean exclusion = Boolean.parseBoolean(eventRecurrenceElement.getChildText("exclusion"));

        return new EventRecurrence(startTime, endTime, startDate, endDate, monday, tuesday, wednesday, 
            thursday, friday, saturday, sunday, exclusion);
    }

    /**
     * Convert the XML element representing the J2735 DDate into a Java object
     * 
     * @param dDateElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected DDate convertDDate(Element dDateElement) {
        int year = Integer.parseInt(dDateElement.getChildText("year"));
        int month = Integer.parseInt(dDateElement.getChildText("month"));
        int day = Integer.parseInt(dDateElement.getChildText("day"));

        return new DDate(year, month, day);
    }

    /**
     * Convert the XML element representing the J2735 DTime into a Java object
     * 
     * @param dTimeElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected DTime convertDTime(Element dTimeElement) {
        int hour = Integer.parseInt(dTimeElement.getChildText("hour"));
        int minute = Integer.parseInt(dTimeElement.getChildText("minute"));
        int second = Integer.parseInt(dTimeElement.getChildText("second"));
        Optional<Integer> offset = Optional.ofNullable(dTimeElement.getChildText("offset"))
            .map(Integer::parseInt);

        return new DTime(hour, minute, second, offset);
    }

    /**
     * Convert the XML element representing the J2735 DDateTime into a Java object
     * 
     * @param dDDateTimeElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected DDateTime convertDDateTime(Element dDDateTimeElement) {
        Optional<Integer> year = Optional.ofNullable(dDDateTimeElement.getChildText("year"))
            .map(Integer::parseInt);
        Optional<Integer> month = Optional.ofNullable(dDDateTimeElement.getChildText("month"))
            .map(Integer::parseInt);
        Optional<Integer> day = Optional.ofNullable(dDDateTimeElement.getChildText("day"))
            .map(Integer::parseInt);
        Optional<Integer> hour = Optional.ofNullable(dDDateTimeElement.getChildText("hour"))
            .map(Integer::parseInt);
        Optional<Integer> minute = Optional.ofNullable(dDDateTimeElement.getChildText("minute"))
            .map(Integer::parseInt);
        Optional<Integer> second = Optional.ofNullable(dDDateTimeElement.getChildText("second"))
            .map(Integer::parseInt);
        Optional<Integer> offset = Optional.ofNullable(dDDateTimeElement.getChildText("offset"))
            .map(Integer::parseInt);

        return new DDateTime(year, month, day, hour, minute, second, offset);
    }

    /**
     * Convert the XML element representing the RSM MsgSegmentInfo element into a java object
     * 
     * @param msgSegmentInfoElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected MsgSegmentInfo convertMsgSegmentInfo(Element msgSegmentInfoElement) {
        int totalMsgSegments = Integer.parseInt(msgSegmentInfoElement.getChildText("totalMsgSegments"));
        int thisSegmentNum = Integer.parseInt(msgSegmentInfoElement.getChildText("thisSegmentNum"));
        return new MsgSegmentInfo(totalMsgSegments, thisSegmentNum);
    }

    /**
     * Convert the XML element representing the RSM RegionInfo element into a java object
     * 
     * @param regionInfoElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected RegionInfo convertRegionInfo(Element regionInfoElement) {
        ApplicableHeading applicableHeading = convertApplicableHeading(regionInfoElement.getChild("applicableHeading"));
        Position3D referencePoint = convertPosition3d(regionInfoElement.getChild("referencePoint"));
        Optional<ReferencePointType> referencePointType = Optional.ofNullable(regionInfoElement.getChild("referencePointType"))
            .map((el) -> {
                String referencePointTypeString = el.getChildren().get(0).getName();
                switch (referencePointTypeString) {
                    case "arbitrary":
                        return ReferencePointType.ARBITRARY;
                    case "startOfEvent":
                        return ReferencePointType.START_OF_EVENT;
                    default:
                        return ReferencePointType.ARBITRARY;
                }
            });
        
        Optional<String> descriptiveName = Optional.ofNullable(regionInfoElement.getChildText("descriptiveName"));
        Optional<RsmSpeedLimit> rsmSpeedLimit = Optional.ofNullable(regionInfoElement.getChild("speedLimit"))
            .map(this::convertRsmSpeedLimit);
        Optional<Integer> regionLength = Optional.ofNullable(regionInfoElement.getChildText("regionLength"))
            .map(Integer::parseInt);
        Optional<AreaType> areaType = Optional.ofNullable(regionInfoElement.getChild("approachRegion"))
            .map(this::convertAreaType);

        return new RegionInfo(applicableHeading, referencePoint, referencePointType, descriptiveName, rsmSpeedLimit, regionLength, areaType);
    }

    /**
     * Convert the XML element representing the RSM AreaType element into a java object
     * 
     * @param areaTypeElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected AreaType convertAreaType(Element areaTypeElement) {
        Element broadRegionElement = areaTypeElement.getChild("broadRegion");
        Element rsmGeometryElement = areaTypeElement.getChild("roadwayGeometry");
        Element pathsElement = areaTypeElement.getChild("paths");

        if (broadRegionElement != null) {
            return new AreaType(convertBroadRegion(broadRegionElement));
        } else if (rsmGeometryElement != null) {
            return new AreaType(convertRsmGeometry(rsmGeometryElement));
        } else if (pathsElement != null) {
            return new AreaType(convertPaths(pathsElement));
        } else {
            throw new RuntimeException("Invalid AreaType element");
        }
    }

    /**
     * Convert the XML element representing the RSM Paths element into a java object
     * 
     * @param pathListElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected List<Path> convertPaths(Element pathListElement) {
        return pathListElement.getChildren().stream()
            .map(this::convertPath)
            .collect(Collectors.toList());
    }

    /**
     * Convert the XML element representing the RSM Path element into a java object
     * 
     * @param pathElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected Path convertPath(Element pathElement) {
        int pathWidth = Integer.parseInt(pathElement.getChildText("pathWidth"));
        List<Position3D> pathPoints = pathElement.getChild("pathPoints").getChildren().stream()
            .map(this::convertPosition3d)
            .collect(Collectors.toList());

        return new Path(pathWidth, pathPoints);
    }

    /**
     * Convert the XML element representing the RSM BroadRegion element into a java object
     * 
     * @param broadRegionElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected BroadRegion convertBroadRegion(Element broadRegionElement) {
        Element polygonElement = broadRegionElement.getChild("polygon");
        Element circleElement = broadRegionElement.getChild("circle");

        if (polygonElement != null) {
            List<NodePointLle> polygon = polygonElement.getChildren().stream()
                .map(this::convertNodePointLle)
                .collect(Collectors.toList());
            return new BroadRegion(polygon);
        } else if (circleElement != null) {
            return new BroadRegion(convertCircle(circleElement));
        } else {
            throw new RuntimeException("Invalid BroadRegion element");
        }
    }

    /**
     * Convert the XML element representing the J2735 Circle element into a java object
     * 
     * @param circleElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected Circle convertCircle(Element circleElement) {
        Position3D center = convertPosition3d(circleElement.getChild("center"));
        int radius = Integer.parseInt(circleElement.getChildText("radius"));
        
        String distanceUnitsString = circleElement.getChild("units").getChildren().get(0).getName();
        DistanceUnits distanceUnits;
        switch (distanceUnitsString) {
            case "centimeter":
                distanceUnits = DistanceUnits.CENTIMETER;
                break;
            case "cm2-5":
                distanceUnits = DistanceUnits.CM2_5;
                break;
            case "decimeter":
                distanceUnits = DistanceUnits.DECIMETER;
                break;
            case "meter":
                distanceUnits = DistanceUnits.METER;
                break;
            case "kilometer":
                distanceUnits = DistanceUnits.KILOMETER;
                break;
            case "foot":
                distanceUnits = DistanceUnits.FOOT;
                break;
            case "yard":
                distanceUnits = DistanceUnits.YARD;
                break;
            case "mile":
                distanceUnits = DistanceUnits.MILE;
                break;
            default:
                throw new RuntimeException("Invalid DistanceUnits");
        }

        return new Circle(center, radius, distanceUnits);
    }

    /**
     * Convert the XML element representing the RSM RsmSpeedLimit element into a java object
     * 
     * @param rsmSpeedLimitElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected RsmSpeedLimit convertRsmSpeedLimit(Element rsmSpeedLimitElement) {       
        SpeedLimitType type = SpeedLimitType.UNKNOWN;
        String typeString = rsmSpeedLimitElement.getChild("type").getChildren().get(0).getName();
        switch (typeString) {
            case "vehicleMaxSpeed":
                type = SpeedLimitType.VEHICLE_MAX_SPEED;
                break;
            case "vehicleMinSpeed":
                type = SpeedLimitType.VEHICLE_MIN_SPEED;
                break;
            case "vehicleMaxNightSpeed":
                type = SpeedLimitType.VEHICLE_NIGHT_MAX_SPEED;
                break;
            case "vehiclesWithTrailersMaxNightSpeed":
                type = SpeedLimitType.VEHICLES_WITH_TRAILERS_MAX_NIGHT_SPEED;
                break;
            case "vehiclesWithTrailersMaxSpeed":
                type = SpeedLimitType.VEHICLES_WITH_TRAILERS_MAX_SPEED;
                break;
            case "vehiclesWithTrailersMinSpeed":
                type = SpeedLimitType.VEHICLES_WITH_TRAILERS_MIN_SPEED;
                break;
            case "truckMaxSpeed":
                type = SpeedLimitType.TRUCK_MAX_SPEED;
                break;
            case "truckMinSpeed":
                type = SpeedLimitType.TRUCK_MIN_SPEED;
                break;
            case "truckNightMaxSpeed":
                type = SpeedLimitType.TRUCK_NIGHT_MAX_SPEED;
                break;
            case "maxSpeedInConstructionZone":
                type = SpeedLimitType.MAX_SPEED_IN_CONSTRUCTION_ZONE;
                break;
            case "maxSpeedInSchoolZone":
                type = SpeedLimitType.MAX_SPEED_IN_SCHOOL_ZONE;
                break;
            case "maxSpeedInSchoolZoneWhenChildrenArePresent":
                type = SpeedLimitType.MAX_SPEED_IN_SCHOOL_ZONE_WHEN_CHILDREN_ARE_PRESENT;
                break;
        }
        
        int speed = Integer.parseInt(rsmSpeedLimitElement.getChild("speed").getText());
        SpeedUnits speedUnits = SpeedUnits.MPH;
        String speedUnitsString = rsmSpeedLimitElement.getChild("speedUnits").getChildren().get(0).getName();
        if (speedUnitsString.equals("mph")) {
            speedUnits = SpeedUnits.MPH;
        } else if (speedUnitsString.equals("kph")) {
            speedUnits = SpeedUnits.KPH;
        } else if (speedUnitsString.equals("mpsXPt02")) {
            speedUnits = SpeedUnits.MPS_X_PT02;
        }

        return new RsmSpeedLimit(type, speed, speedUnits);
    }

        /**
     * Convert the XML element representing the J2735 Position3D element into a java object
     * 
     * @param position3dElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected Position3D convertPosition3d(Element position3dElement) {
        return new Position3D(
            Integer.parseInt(position3dElement.getChildText("lat")),
            Integer.parseInt(position3dElement.getChildText("long")),
            Integer.parseInt(position3dElement.getChildText("elevation")));
    }

    /**
     * Convert the XML element representing the RSM ApplicableHeading element into a java object
     * 
     * @param applicableHeadingElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected ApplicableHeading convertApplicableHeading(Element applicableHeadingElement) {
        int headingDeg = Integer.parseInt(applicableHeadingElement.getChildText("heading"));
        int tolerance = Integer.parseInt(applicableHeadingElement.getChildText("tolerance"));

        return new ApplicableHeading(headingDeg, tolerance);
    }

    /**
     * Convert the XML element representing the RSM ReducedSpeedZoneContainer element into a java object
     * 
     * @param reducedSpeedZoneContainerElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected ReducedSpeedZoneContainer convertReducedSpeedZoneContainer(Element reducedSpeedZoneContainerElement) {
        Optional<List<LaneInfo>> laneStatus = Optional.ofNullable(reducedSpeedZoneContainerElement.getChild("laneStatus"))
            .map(el -> el.getChildren())
            .map(children -> children.stream()
                .map(this::convertLaneInfo)
                .collect(Collectors.toList()));

        Optional<Boolean> peoplePresent = Optional.ofNullable(reducedSpeedZoneContainerElement.getChildText("peoplePresent"))
            .map(Boolean::parseBoolean);

        Optional<RsmSpeedLimit> rsmSpeedLimit = Optional.ofNullable(reducedSpeedZoneContainerElement.getChild("speedLimit"))
            .map(this::convertRsmSpeedLimit);

        Optional<Integer> roadClosureDescription = Optional.ofNullable(reducedSpeedZoneContainerElement.getChildText("roadClosureDescription"))
            .map(Integer::parseInt);
        
        Optional<Integer> roadWorkDescription = Optional.ofNullable(reducedSpeedZoneContainerElement.getChildText("roadWorkDescription"))
            .map(Integer::parseInt);

        Optional<String> flagman = Optional.ofNullable(reducedSpeedZoneContainerElement.getChildText("flagman"));

        Optional<Boolean> trucksEnteringLeaving = Optional.ofNullable(reducedSpeedZoneContainerElement.getChildText("trucksEnteringLeaving"))
            .map(Boolean::parseBoolean);

        Optional<AreaType> rszRegion = Optional.ofNullable(reducedSpeedZoneContainerElement.getChild("rszRegion"))
            .map(this::convertAreaType);

        return new ReducedSpeedZoneContainer(laneStatus, peoplePresent, rsmSpeedLimit, roadClosureDescription, roadWorkDescription, flagman, trucksEnteringLeaving, rszRegion);
    }

    /**
     * Convert the XML element representing the RSM LaneInfo element into a java object
     * 
     * @param laneInfoElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected LaneInfo convertLaneInfo(Element laneInfoElement) {
        int lanePosition = Integer.parseInt(laneInfoElement.getChildText("lanePosition"));
        boolean laneClosed = Boolean.parseBoolean(laneInfoElement.getChildText("laneClosed"));
        int laneCloseOffset = Integer.parseInt(laneInfoElement.getChildText("laneCloseOffset"));
        return new LaneInfo(lanePosition, laneClosed, laneCloseOffset);
    }

    /**
     * Parse the RSM geometry element from an XML encoded RSM document
     * 
     * @param rsmGeometryElement The {@code roadwayGeometry} element that starts the desired content
     * @return an {@link RsmGeometry} object containing the parsed data
     */
    protected RsmGeometry convertRsmGeometry(Element rsmGeometryElement) {
        Optional<Integer> rsmScale = Optional.ofNullable(rsmGeometryElement.getChildText("scale"))
            .map(Integer::parseInt);

        List<Element> laneElements = rsmGeometryElement.getChild("rsmLanes").getChildren("RSMLane");

        List<RsmLane> convertedLanes = new ArrayList<>();
        for (Element laneElement : laneElements) {
            convertedLanes.add(convertRsmLane(laneElement));
        }

        return new RsmGeometry(rsmScale, convertedLanes);
    }

    /**
     * Parse the RSM lane element from an XML encoded RSM document
     * <p>
     * Processes both offets and absolute points relative to reference point or previous datum
     * 
     * @param rsmLaneElement The XML document element containing the lane to be converted
     * @return an {@link RsmLane} containing the converted data
     */
    protected RsmLane convertRsmLane(Element rsmLaneElement) {
        int laneId = Integer.parseInt(rsmLaneElement.getChildText("laneID"));
        Optional<Integer> lanePosition = Optional.ofNullable(rsmLaneElement.getChildText("lanePosition"))
            .map(Integer::parseInt);
        Optional<String> laneName = Optional.ofNullable(rsmLaneElement.getChildText("laneName"));
        Optional<Integer> laneWidth = Optional.ofNullable(rsmLaneElement.getChildText("laneWidth"))
            .map(Integer::parseInt);
        Optional<LaneGeometry> laneGeometry = Optional.ofNullable(rsmLaneElement.getChild("laneGeometry"))
            .map((laneGeo) -> convertLaneGeometry(laneGeo));
        Optional<List<Integer>> connectsTo = Optional.ofNullable(rsmLaneElement.getChild("connectsTo"))
            .map(this::convertConnectsTo);

        return new RsmLane(laneId, lanePosition, laneName, laneWidth, laneGeometry, connectsTo);
    }

    /**
     * Convert the XML element representing the RSM NodePointLle element into a java object
     * 
     * @param nodePointLleElement The JDOM XML element
     * @return The parsed, converted object
     */
    protected NodePointLle convertNodePointLle(Element nodePointLleElement) {
        Element node3Dabsolute = nodePointLleElement.getChild("node-3Dabsolute");
        if (node3Dabsolute != null) {
            return new NodePointLle(convertPosition3d(node3Dabsolute));
        } else {
            Element node3Doffset = nodePointLleElement.getChild("node-3Doffset");
            Offset3D nodeContent = new Offset3D(
                Integer.parseInt(node3Doffset.getChildText("lat-offset")),
                Integer.parseInt(node3Doffset.getChildText("long-offset")),
                Integer.parseInt(node3Doffset.getChildText("elevation-offset")));
            return new NodePointLle(nodeContent);
        }
    }

    /**
     * Convert the NodePointLLE {@link Element} into a {@link NodePointLle} instance
     * @param nodeLleElement The nodePointLle XML element convert
     * 
     * @return The converted NodePointLle object
     */
    protected NodeLle convertNodeLle(Element nodeLleElement) {
        Optional<NodePointLle> nodePointLle = Optional.ofNullable(nodeLleElement.getChild("nodePoint"))
            .map(this::convertNodePointLle);

        Optional<NodeAttributeSetLle> nodeAttributesSet = Optional.ofNullable(nodeLleElement.getChild("nodeAttributes"))
            .map((nodeAttributes) -> {
                Optional<RsmSpeedLimit> speedLimit = Optional.ofNullable(nodeAttributes.getChild("speedLimit"))
                    .map(this::convertRsmSpeedLimit);
                Optional<Integer> width = Optional.ofNullable(nodeAttributes.getChildText("width"))
                    .map(Integer::parseInt);
                Optional<Boolean> taperLeft = Optional.ofNullable(nodeAttributes.getChild("taperLeft"))
                    .map(el -> el.getChildren().get(0).getName())
                    .map(Boolean::parseBoolean);
                Optional<Boolean> taperRight = Optional.ofNullable(nodeAttributes.getChild("taperRight"))
                    .map(el -> el.getChildren().get(0).getName())
                    .map(Boolean::parseBoolean);
                Optional<Boolean> laneClosed = Optional.ofNullable(nodeAttributes.getChild("laneClosed"))
                    .map(el -> el.getChildren().get(0).getName())
                    .map(Boolean::parseBoolean);
                Optional<Boolean> peoplePresent = Optional.ofNullable(nodeAttributes.getChild("peoplePresent"))
                    .map((el) -> el.getChildren().get(0).getName())
                    .map(Boolean::parseBoolean);
                return new NodeAttributeSetLle(speedLimit, width, taperLeft, taperRight, laneClosed, peoplePresent);
            });

        return new NodeLle(nodePointLle.get(), nodeAttributesSet);
    }

    /**
     * Convert the lane geometry {@link Element} into a {@link LaneGeometry} instance
     * 
     * @param rsmLaneGeometryElement The lane geometry XML element convert
     * @return The converted LaneGeometry object
     */
    protected LaneGeometry convertLaneGeometry(Element rsmLaneGeometryElement) {
        return new LaneGeometry(
            Optional.ofNullable(rsmLaneGeometryElement.getChildText("referenceLane"))
                .map(Integer::parseInt), 
            Optional.ofNullable(rsmLaneGeometryElement.getChild("nodeSet"))
                .map((nodeSetElement) -> nodeSetElement.getChildren())
                .map((nodeSet) -> 
                    nodeSet.stream()
                        .map(this::convertNodeLle)
                        .collect(Collectors.toList())));
    }

    /**
     * Convert the connectsTo {@link Element} into a {@link List} of contained lane IDs as integers
     * @param connectsToElement The connectsTo XML element convert
     * 
     * @return The converted {@code List<Integer>} containing the laneIDs of the connecting lanes
     */
    protected List<Integer> convertConnectsTo(Element connectsToElement) {
        return connectsToElement.getChildren("laneID").stream()
            .map((child) -> 
                Integer.parseInt(child.getText()))
            .collect(Collectors.toList());
    }
}
