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
import java.util.zip.DataFormatException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import gov.usdot.cv.msg.rsm.RoadsideSafetyMessage;
import gov.usdot.cv.msg.rsm.RsmGeometry;

/**
 * Parses the RSMGeometry Element out of an ASN.1 XML encoded RSM message
 * <p>
 * Extracts the first found RsmGeometry element out of the XML encoding
 * and parses it down to the geometric structure of the lanes and nodes.
 */
public class RsmParser {

    /**
     * Convert an input XML formatted RSM file into a parsed RSM geometry
     * representation
     * 
     * @param rsmXmlFile The XML file to be parsed
     * @return An {@link RoadsideSafetyMessage} instance containing the data of the XML file
     *         if the parse was successful
     * @throws IOException           If the file is unable to be opened
     * @throws DataFormatException Thrown if the XML is not properly formatted for processing 
     */
    public RoadsideSafetyMessage parseRsmGeometry(File rsmXmlFile) throws IOException, DataFormatException {
            try {
                JdomRsmParser parser = new JdomRsmParser();
                return parser.parse(rsmXmlFile);
            } catch (JDOMException jde) {
                // Rethrow as more generic exception
                throw new DataFormatException("XML data did not contain expected representation and was unable to be parsed.");
            }
    }

    /**
     * Convert an input XML formatted RSM file into a parsed RSM geometry
     * representation
     * 
     * @param rsmXmlFile The XML file to be parsed
     * @return An {@link RoadsideSafetyMessage} instance containing the data of the XML file
     *         if the parse was successful
     * @throws DataFormatException Thrown if the XML is not properly formatted for processing 
     */
    public RoadsideSafetyMessage parseRsmGeometry(String rsmXmlString) throws DataFormatException {
            try {
                JdomRsmParser parser = new JdomRsmParser();
                return parser.parse(rsmXmlString);
            } catch (JDOMException jde) {
                // Rethrow as more generic exception
                throw new DataFormatException("XML data did not contain expected representation and was unable to be parsed.");
            }
    }
}
