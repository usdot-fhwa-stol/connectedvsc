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

package gov.usdot.cv.msg.kml;

import java.util.Optional;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

/**
 * Builder pattern class for KML Documents created with JDOM
 */
public class KmlDocumentBuilder {

    public static final Namespace KML_NS = Namespace.getNamespace("http://www.opengis.net/kml/2.2");

    /**
     * Locate the first element of the specified name inside the XML document
     * 
     * @param kmlDoc The document to search
     * @param elementName The string name of the element to look for
     * 
     * @return An {@link Optional} instance containing the desired {@link Element} if it was found
     */
    protected static Optional<Element> locateElementByName(Document kmlDoc, String elementName) {
        XPathFactory xPathFactory = XPathFactory.instance();
        XPathExpression<Element> rsmGeometryXpe = xPathFactory.compile("//*[local-name() = '" + elementName + "']", Filters.element());
        return Optional.ofNullable(rsmGeometryXpe.evaluateFirst(kmlDoc));
    }

    /**
     * Build an empty KML document ready for use
     * 
     * @return An intialized KML document with namespace configuration
     */
    public static Document buildKmlDocumentRoot() {
        Element root = new Element("kml", KML_NS);
        Document kmlDoc = new Document(root);
        Element docElement = new Element("Document", KML_NS);
        root.addContent(docElement);

        return kmlDoc;
    }

    /**
     * Add a folder or group of placemarks to the KML document
     * 
     * @param kmlDoc The document to construct a folder in
     * @return The created folder element
     */
    public static Element createFolder(Document kmlDoc) {
        Element docEle = locateElementByName(kmlDoc, "Document").get();
        Element folderEle = new Element("Folder", KML_NS);
        docEle.addContent(folderEle);

        return folderEle;
    }

    /**
     * Create a new stylesheet element in the KML document
     * 
     * @param kmlDoc The Document object to modify
     * @param styleName The name of the new style to create
     * @return The empty new style element
     */
    public static Element createStyle(Document kmlDoc, String styleName) {
        Element docEle = locateElementByName(kmlDoc, "Document").get();
        Element styleEle = new Element("Style", KML_NS);
        styleEle.setAttribute("id", styleName);

        docEle.addContent(styleEle);

        return styleEle;
    }

    /**
     * Add a definition for the color of line elements to the stylesheet
     * 
     * @param styleEle The style definition to modify
     * @param hexColorString The desired color of the lines in AABBGGRR hex format
     * @return The modified style element
     */
    public static Element addLineColorToStyle(Element styleEle, String hexColorString) {
        Element lineStyleEle = new Element("LineStyle", KML_NS);
        Element colorEle = new Element("color", KML_NS).setText(hexColorString);
        lineStyleEle.addContent(colorEle);
        styleEle.addContent(lineStyleEle);

        return styleEle;
    }

    /**
     * Assign the specified stylesheet ID to the placemark. Note that assigning more than
     * one stylesheet to a single placemark is not well defined.
     * 
     * @param placemarkEle The placemark to assign the stylesheet to
     * @param styleName  The string ID of the style
     * @return The modified placemark element
     */
    public static Element assignStyleToPlacemark(Element placemarkEle, String styleName) {
        Element styleUrlElement = new Element("styleUrl", KML_NS).setText("#" + styleName);
        placemarkEle.addContent(styleUrlElement);

        return placemarkEle;
    }

    /**
     * Add a folder or group of placemarks to the KML document
     * 
     * @param kmlDoc The document to construct a folder in
     * @param name The name of the folder to add
     * @return The created folder element
     */
    public static Element createFolder(Document kmlDoc, String name) {
        Element docEle = locateElementByName(kmlDoc, "Document").get();
        Element folderEle = new Element("Folder", KML_NS);
        Element nameEle = new Element("name", KML_NS).setText(name);

        folderEle.addContent(nameEle);
        docEle.addContent(folderEle);

        return folderEle;
    }

    /**
     * Build a new placemark instance and add it to the KML document under the Document node
     * 
     * @param kmlDoc The KML document to add the placemark to
     * @param name The name of the placemark to create
     * @return The created placemark element requiring further configuration
     */
    public static Element createPlacemark(Document kmlDoc, String name) {
        Element docEle = locateElementByName(kmlDoc, "Document").get();
        Element placemarkEle = new Element("Placemark", KML_NS);
        Element nameEle = new Element("name", KML_NS);
        nameEle.setText(name);
        placemarkEle.addContent(nameEle);
        docEle.addContent(placemarkEle);

        return placemarkEle;
    }

    /**
     * Build a new placemark instance and add it to the KML document under the specifiedf folder element
     * 
     * @param folderEle The folder to add the placemark to
     * @param name The name of the placemark to create
     * @return The created placemark element requiring further configuration
     */
    public static Element createPlacemark(Element folderEle, String name) {
        Element placemarkEle = new Element("Placemark", KML_NS);
        Element nameEle = new Element("name", KML_NS);
        nameEle.setText(name);
        placemarkEle.addContent(nameEle);
        folderEle.addContent(placemarkEle);

        return placemarkEle;
    }

    /**
     * Build and add a new point object to a placemark instance
     * 
     * @param placemarkEle The placemark to add the object to
     * @param lat The latitude of the point
     * @param lon The longitude of the point
     * @param elev The elevation of the point
     * @return The placemark element with the new point added
     */
    public static Element addPointToPlacemark(Element placemarkEle, double lat, double lon, double elev) {
        Element pointEle = new Element("Point", KML_NS);
        pointEle.addContent(new Element("coordinates", KML_NS).setText(lon + "," + lat + "," + elev));
        placemarkEle.addContent(pointEle);

        return pointEle;
    }

    /**
     * Add a polyline or linestring to a placemark instance
     * 
     * @param placemarkEle The placemark to add the object to
     * @return The created empty LineString element
     */
    public static Element addPolylinetoPlacemark(Element placemarkEle) {
        Element lineStringEle = new Element("LineString", KML_NS);
        placemarkEle.addContent(lineStringEle);

        return lineStringEle;
    }

    /**
     * Add a point to the polyline element
     * 
     * @param polyLineEle The polyline or linestring to add a point to
     * @param lat the latitude of the point to add
     * @param lon the longitude of the point to add
     * @param elev the elevation of the point to add
     * @return The polyline element with the new point added
     */
    public static Element addPointToPolyline(Element polyLineEle, double lat, double lon, double elev) {
        Element coordsEle = polyLineEle.getChild("coordinates", KML_NS);
        if (coordsEle == null) {
            coordsEle = new Element("coordinates", KML_NS);
            polyLineEle.addContent(coordsEle);
        }

        coordsEle.setText(coordsEle.getText() + "\n" + lon + "," + lat + "," + elev);

        return polyLineEle;
    }
}
