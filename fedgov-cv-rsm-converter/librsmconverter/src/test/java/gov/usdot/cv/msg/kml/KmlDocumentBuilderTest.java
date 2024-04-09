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

import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.xml.sax.SAXException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileNotFoundException;
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
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * {@link KmlDocumentBuilder} test harness
 */
public class KmlDocumentBuilderTest {
    private static final String KML_SCHEMA = "src/test/resources/ogckml22.xsd";

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testDocumentCreation() throws FileNotFoundException, IOException, SAXException, JDOMException {
        Document doc1 = KmlDocumentBuilder.buildKmlDocumentRoot();

        File tmpOutFile = tempFolder.newFile("testDocumentCreation.xml");
        new XMLOutputter(Format.getPrettyFormat()).output(doc1, new FileOutputStream(tmpOutFile));

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(KML_SCHEMA));
        XMLReaderJDOMFactory factory = new XMLReaderSchemaFactory(schema);
        SAXBuilder builder = new SAXBuilder(factory);
        Document kmlDoc = builder.build(tmpOutFile);
        assertNotNull(kmlDoc);
    }

    @Test
    public void testPlacemarkCreation() throws IOException, SAXException, JDOMException {
        Document doc1 = KmlDocumentBuilder.buildKmlDocumentRoot();
        KmlDocumentBuilder.createPlacemark(doc1, "Test Placemark 1");

        File tmpOutFile = tempFolder.newFile("testPlacemarkCreation.xml");
        new XMLOutputter(Format.getPrettyFormat()).output(doc1, new FileOutputStream(tmpOutFile));

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(KML_SCHEMA));
        XMLReaderJDOMFactory factory = new XMLReaderSchemaFactory(schema);
        SAXBuilder builder = new SAXBuilder(factory);
        Document kmlDoc = builder.build(tmpOutFile);
        assertNotNull(kmlDoc);
    }

    @Test
    public void testPlacemarkCreationWPoint() throws IOException, SAXException, JDOMException {
        Document doc1 = KmlDocumentBuilder.buildKmlDocumentRoot();
        Element placemark = KmlDocumentBuilder.createPlacemark(doc1, "Test Placemark 2");
        KmlDocumentBuilder.addPointToPlacemark(placemark, -37.80000, 77.3545, 0.0);

        System.out.println(new XMLOutputter(Format.getPrettyFormat()).outputString(doc1));

        File tmpOutFile = tempFolder.newFile("testPlacemarkCreationWPoint.xml");
        new XMLOutputter(Format.getPrettyFormat()).output(doc1, new FileOutputStream(tmpOutFile));

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(KML_SCHEMA));
        XMLReaderJDOMFactory factory = new XMLReaderSchemaFactory(schema);
        SAXBuilder builder = new SAXBuilder(factory);
        Document kmlDoc = builder.build(tmpOutFile);
        assertNotNull(kmlDoc);
    }
}
