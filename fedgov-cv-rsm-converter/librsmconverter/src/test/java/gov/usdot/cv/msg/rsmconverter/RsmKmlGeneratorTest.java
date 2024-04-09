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

import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.xml.sax.SAXException;

import gov.usdot.cv.msg.rsm.RoadsideSafetyMessage;
import gov.usdot.cv.msg.rsmparser.JdomRsmParser;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderSchemaFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * {@link KmlDocumentBuilder} test harness
 */
public class RsmKmlGeneratorTest {
    private static final String TEST_FILE_1 = "src/test/resources/RSZW_MAP_xml_File-20181108-114832-1_of_2.xml";
    private static final String TEST_FILE_2 = "src/test/resources/RSZW_MAP_xml_File-20181108-114832-2_of_2.xml";
    private static final String TEST_FILE_3 = "src/test/resources/User_Guide_v1_Sample_20181030.xml";
    private static final String KML_SCHEMA = "src/test/resources/ogckml22.xsd";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testDocumentCreation1() throws JDOMException, IOException, SAXException {
        RoadsideSafetyMessage rsm = new JdomRsmParser().parse(new File(TEST_FILE_1));

        Document kml = RsmKmlGenerator.convertRsmToKml(rsm);

        File tmpOutFile = tempFolder.newFile("testDocumentCreation1.xml");
        new XMLOutputter(Format.getPrettyFormat()).output(kml, new FileOutputStream(tmpOutFile));

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(KML_SCHEMA));
        XMLReaderJDOMFactory factory = new XMLReaderSchemaFactory(schema);
        SAXBuilder builder = new SAXBuilder(factory);
        Document rsmDoc = builder.build(tmpOutFile);
        assertNotNull(rsmDoc);
    }

    @Test
    public void testDocumentCreation2() throws JDOMException, IOException, SAXException {
        RoadsideSafetyMessage rsm = new JdomRsmParser().parse(new File(TEST_FILE_2));

        Document kml = RsmKmlGenerator.convertRsmToKml(rsm);

        File tmpOutFile = tempFolder.newFile("testDocumentCreation2.xml");
        new XMLOutputter(Format.getPrettyFormat()).output(kml, new FileOutputStream(tmpOutFile));

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(KML_SCHEMA));
        XMLReaderJDOMFactory factory = new XMLReaderSchemaFactory(schema);
        SAXBuilder builder = new SAXBuilder(factory);
        Document rsmDoc = builder.build(tmpOutFile);
        assertNotNull(rsmDoc);
    }

    @Test
    public void testDocumentCreation3() throws JDOMException, IOException, SAXException {
        RoadsideSafetyMessage rsm = new JdomRsmParser().parse(new File(TEST_FILE_3));

        Document kml = RsmKmlGenerator.convertRsmToKml(rsm);

        File tmpOutFile = tempFolder.newFile("testDocumentCreation3.xml");
        new XMLOutputter(Format.getPrettyFormat()).output(kml, new FileOutputStream(tmpOutFile));

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(KML_SCHEMA));
        XMLReaderJDOMFactory factory = new XMLReaderSchemaFactory(schema);
        SAXBuilder builder = new SAXBuilder(factory);
        Document rsmDoc = builder.build(tmpOutFile);
        assertNotNull(rsmDoc);
    }
}
