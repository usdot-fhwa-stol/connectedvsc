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

import org.junit.*;

import gov.usdot.cv.msg.rsm.AreaType;
import gov.usdot.cv.msg.rsm.CommonContainer;
import gov.usdot.cv.msg.rsm.LaneGeometry;
import gov.usdot.cv.msg.rsm.NodeLle;
import gov.usdot.cv.msg.rsm.Position3D;
import gov.usdot.cv.msg.rsm.ReducedSpeedZoneContainer;
import gov.usdot.cv.msg.rsm.RoadsideSafetyMessage;
import gov.usdot.cv.msg.rsm.RsmGeometry;
import gov.usdot.cv.msg.rsm.RsmLane;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

/**
 * Test harness for testing RSM parser functionality
 */
public class RsmParserTest {

    private JdomRsmParser parser;
    private static final String TEST_FILE_1 = "src/test/resources/RSZW_MAP_xml_File-20181108-114832-1_of_2.xml";
    private static final String TEST_FILE_2 = "src/test/resources/RSZW_MAP_xml_File-20181108-114832-2_of_2.xml";
    private static final String TEST_FILE_3 = "src/test/resources/User_Guide_v1_Sample_20181030.xml";

    @Before
    public void setUp() {
        parser = new JdomRsmParser();
    }

    @Test
    public void testFileLoad1() throws JDOMException, IOException {
        parser.loadFile(TEST_FILE_1);
    }

    @Test
    public void testFileLoad2() throws JDOMException, IOException {
        parser.loadFile(new File(TEST_FILE_2));
    }

    @Test
    public void testFileLoad3() throws JDOMException, IOException {
        parser.loadFile(new File(TEST_FILE_3));
    }

    @Test
    public void smokeTest1() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_1));
        assertNotNull(rsm);
    }

    @Test
    public void smokeTest2() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_2));
        assertNotNull(rsm);
    }

    @Test
    public void smokeTest3() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_3));
        assertNotNull(rsm);
    }

    @Test
    public void testParseVersion1() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_1));

        assertNotNull(rsm.getVersion());
        assertEquals(1, rsm.getVersion());
    }

    @Test
    public void testParseVersion2() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_1));

        assertNotNull(rsm.getVersion());
        assertEquals(1, rsm.getVersion());
    }

    @Test
    public void testParseVersion3() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_1));

        assertNotNull(rsm.getVersion());
        assertEquals(1, rsm.getVersion());
    }

    @Test
    public void testParseCommonContainer1() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_1));
        CommonContainer commonContainer = rsm.getCommonContainer();

        assertNotNull(commonContainer);
        assertNotNull(commonContainer.getEventInfo());
        assertNotNull(commonContainer.getRegionInfo());
    }

    @Test
    public void testParseCommonContainer2() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_2));
        CommonContainer commonContainer = rsm.getCommonContainer();

        assertNotNull(commonContainer);
        assertNotNull(commonContainer.getEventInfo());
        assertNotNull(commonContainer.getRegionInfo());
    }

    @Test
    public void testParseCommonContainer3() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_3));
        CommonContainer commonContainer = rsm.getCommonContainer();

        assertNotNull(commonContainer);
        assertNotNull(commonContainer.getEventInfo());
        assertNotNull(commonContainer.getRegionInfo());
    }

    @Test
    public void testParseRszContainer1() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_1));
        Optional<ReducedSpeedZoneContainer> rszContainer = rsm.getReducedSpeedZoneContainer();

        assertNotNull(rszContainer);
        assertTrue(rszContainer.isPresent());

        ReducedSpeedZoneContainer rsz = rszContainer.get();

        assertNotNull(rsz.getLaneStatus());
        assertNotNull(rsz.getPeoplePresent());
        assertNotNull(rsz.getSpeedLimit());
        assertNotNull(rsz.getRoadClosureDescription());
        assertNotNull(rsz.getRoadWorkDescription());
        assertNotNull(rsz.getFlagman());
        assertNotNull(rsz.getTrucksEnteringLeaving());
        assertNotNull(rsz.getRszRegion());
    }

    @Test
    public void testParseRszContainer2() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_2));
        Optional<ReducedSpeedZoneContainer> rszContainer = rsm.getReducedSpeedZoneContainer();

        
        assertNotNull(rszContainer);
        assertTrue(rszContainer.isPresent());

        ReducedSpeedZoneContainer rsz = rszContainer.get();

        assertNotNull(rsz.getLaneStatus());
        assertNotNull(rsz.getPeoplePresent());
        assertNotNull(rsz.getSpeedLimit());
        assertNotNull(rsz.getRoadClosureDescription());
        assertNotNull(rsz.getRoadWorkDescription());
        assertNotNull(rsz.getFlagman());
        assertNotNull(rsz.getTrucksEnteringLeaving());
        assertNotNull(rsz.getRszRegion());
    }

    @Test
    public void testParseRszContainer3() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_2));
        Optional<ReducedSpeedZoneContainer> rszContainer = rsm.getReducedSpeedZoneContainer();

        
        assertNotNull(rszContainer);
        assertTrue(rszContainer.isPresent());

        ReducedSpeedZoneContainer rsz = rszContainer.get();

        assertNotNull(rsz.getLaneStatus());
        assertNotNull(rsz.getPeoplePresent());
        assertNotNull(rsz.getSpeedLimit());
        assertNotNull(rsz.getRoadClosureDescription());
        assertNotNull(rsz.getRoadWorkDescription());
        assertNotNull(rsz.getFlagman());
        assertNotNull(rsz.getTrucksEnteringLeaving());
        assertNotNull(rsz.getRszRegion());
    }

    @Test
    public void testLocateReferencePoint1() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_1));

        Position3D refEle1 = rsm.getCommonContainer().getRegionInfo().getReferencePoint();
        assertNotNull(refEle1);

        assertEquals(425730230, refEle1.getLat());
        assertEquals(-832353725, refEle1.getLon());
        assertEquals(254, refEle1.getElev());
    }

    @Test
    public void testLocateReferencePoint2() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_2));

        Position3D refEle2 = rsm.getCommonContainer().getRegionInfo().getReferencePoint();
        assertNotNull(refEle2);

        assertEquals(425550778, refEle2.getLat());
        assertEquals(-832204100, refEle2.getLon());
        assertEquals(232, refEle2.getElev());
    }

    @Test
    public void testLocateReferencePoint3() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File(TEST_FILE_3));

        Position3D refEle3 = rsm.getCommonContainer().getRegionInfo().getReferencePoint();
        assertNotNull(refEle3);

        assertEquals(-900000000, refEle3.getLat());
        assertEquals(-1799999999, refEle3.getLon());
        assertEquals(-4096, refEle3.getElev());
    }

    @Test
    public void testGeometryParse1() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File (TEST_FILE_1));

        RsmGeometry approachGeo = rsm.getCommonContainer().getRegionInfo().getAreaType()
            .get().getRoadwayGeometry()
            .get();

        assertNotNull(approachGeo);

        RsmGeometry workzoneGeo = rsm.getReducedSpeedZoneContainer()
            .get().getRszRegion()
            .get().getRoadwayGeometry()
            .get();

        assertNotNull(workzoneGeo);

        assertEquals(1, approachGeo.getScale().get().intValue());
        assertEquals(4, approachGeo.getRsmLanes().size());

        // Validate Lane 1
        RsmLane lane1 = approachGeo.getRsmLanes().get(0);
        assertNotNull(lane1);

        assertEquals(1, lane1.getLaneId());
        assertEquals(1, lane1.getLanePosition().get().intValue());
        assertEquals("Lane #1", lane1.getLaneName().get());
        LaneGeometry lane1Geo = lane1.getLaneGeometry().get();
        assertNotNull(lane1Geo);

        List<NodeLle> lane1Nodes = lane1Geo.getNodeSet().get(); 
        assertNotNull(lane1Nodes);
        assertEquals(4, lane1Nodes.size());
        assertEquals(425730573, lane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832353512, lane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(254, lane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getElev());
        
        assertEquals(425768395, lane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832389221, lane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(263, lane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getElev());

        assertEquals(425775783, lane1Nodes
            .get(2).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832396167, lane1Nodes
            .get(2).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(262, lane1Nodes
            .get(2).getNodePoint().getPosition()
            .get().getElev());

        assertEquals(425776429, lane1Nodes
            .get(3).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832396720, lane1Nodes
            .get(3).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(262, lane1Nodes
            .get(3).getNodePoint().getPosition()
            .get().getElev());
        
       // Validate WZ Lane 1 (first 4 points)
        RsmLane wzLane1 = workzoneGeo.getRsmLanes().get(0);
        assertNotNull(wzLane1);

        assertEquals(1, wzLane1.getLaneId());
        assertEquals(1, wzLane1.getLanePosition().get().intValue());
        assertEquals("Left Lane: Lane #1", wzLane1.getLaneName().get());
        assertEquals(360, wzLane1.getLaneWidth().get().intValue());
        LaneGeometry wzLane1Geo = wzLane1.getLaneGeometry().get();
        assertNotNull(wzLane1Geo);

        List<NodeLle> wzLane1Nodes = wzLane1Geo.getNodeSet().get(); 
        assertNotNull(wzLane1Nodes);
        assertEquals(21, wzLane1Nodes.size());
        assertEquals(425730414, wzLane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832353363, wzLane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(254, wzLane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getElev());
        
        assertEquals(425708985, wzLane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832333136, wzLane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(251, wzLane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getElev());

        assertEquals(425696240, wzLane1Nodes
            .get(2).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832321113, wzLane1Nodes
            .get(2).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(247, wzLane1Nodes
            .get(2).getNodePoint().getPosition()
            .get().getElev());

        assertEquals(425665603, wzLane1Nodes
            .get(3).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832292121, wzLane1Nodes
            .get(3).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(243, wzLane1Nodes
            .get(3).getNodePoint().getPosition()
            .get().getElev());
    }

    @Test
    public void testGeometryParse2() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File (TEST_FILE_2));

        Optional<AreaType> approachGeo = rsm.getCommonContainer().getRegionInfo().getAreaType();

        assertFalse(approachGeo.isPresent());

        RsmGeometry workzoneGeo = rsm.getReducedSpeedZoneContainer()
            .get().getRszRegion()
            .get().getRoadwayGeometry()
            .get();

        assertNotNull(workzoneGeo);

        // Validate WZ Lane 1 (first 4 points)
        assertEquals(4, workzoneGeo.getRsmLanes().size());
        RsmLane wzLane1 = workzoneGeo.getRsmLanes().get(0);
        assertNotNull(wzLane1);

        assertEquals(1, wzLane1.getLaneId());
        assertEquals(1, wzLane1.getLanePosition().get().intValue());
        assertEquals("Left Lane: Lane #1", wzLane1.getLaneName().get());
        assertEquals(360, wzLane1.getLaneWidth().get().intValue());
        LaneGeometry wzLane1Geo = wzLane1.getLaneGeometry().get();
        assertNotNull(wzLane1Geo);

        List<NodeLle> wzLane1Nodes = wzLane1Geo.getNodeSet().get(); 
        assertNotNull(wzLane1Nodes);
        assertEquals(20, wzLane1Nodes.size());
        assertEquals(425550992, wzLane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832203769, wzLane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(232, wzLane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getElev());
        
        assertEquals(425548287, wzLane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832200390, wzLane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(232, wzLane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getElev());

        assertEquals(425545761, wzLane1Nodes
            .get(2).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832196846, wzLane1Nodes
            .get(2).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(232, wzLane1Nodes
            .get(2).getNodePoint().getPosition()
            .get().getElev());

        assertEquals(425543181, wzLane1Nodes
            .get(3).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-832192843, wzLane1Nodes
            .get(3).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(232, wzLane1Nodes
            .get(3).getNodePoint().getPosition()
            .get().getElev());
    }

    @Test
    public void testGeometryParse3() throws JDOMException, IOException {
        RoadsideSafetyMessage rsm = parser.parse(new File (TEST_FILE_3));

        RsmGeometry approachGeo = rsm.getCommonContainer().getRegionInfo().getAreaType()
            .get().getRoadwayGeometry()
            .get();

        assertNotNull(approachGeo);

        RsmGeometry workzoneGeo = rsm.getReducedSpeedZoneContainer()
            .get().getRszRegion()
            .get().getRoadwayGeometry()
            .get();

        assertNotNull(workzoneGeo);

        assertEquals(10, approachGeo.getScale().get().intValue());
        assertEquals(2, approachGeo.getRsmLanes().size());

        // Validate Lane 1
        RsmLane lane1 = approachGeo.getRsmLanes().get(0);
        assertNotNull(lane1);

        assertEquals(123, lane1.getLaneId());
        assertEquals(1, lane1.getLanePosition().get().intValue());
        assertEquals("Lane 1", lane1.getLaneName().get());
        LaneGeometry lane1Geo = lane1.getLaneGeometry().get();
        assertNotNull(lane1Geo);

        List<NodeLle> lane1Nodes = lane1Geo.getNodeSet().get(); 
        assertNotNull(lane1Nodes);
        assertEquals(2, lane1Nodes.size());
        assertEquals(-900000000, lane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-1799999999, lane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(-4096, lane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getElev());
        
        assertEquals(-900000000, lane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-1799999999, lane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(-4096, lane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getElev());
        
        // Validate WZ Lane 1
        RsmLane wzLane1 = workzoneGeo.getRsmLanes().get(0);
        assertNotNull(wzLane1);

        assertEquals(0, wzLane1.getLaneId());
        assertEquals(1, wzLane1.getLanePosition().get().intValue());
        assertEquals("0", wzLane1.getLaneName().get());
        assertEquals(0, wzLane1.getLaneWidth().get().intValue());
        LaneGeometry wzLane1Geo = wzLane1.getLaneGeometry().get();
        assertNotNull(wzLane1Geo);

        List<NodeLle> wzLane1Nodes = wzLane1Geo.getNodeSet().get(); 
        assertNotNull(wzLane1Nodes);
        assertEquals(2, wzLane1Nodes.size());
        assertEquals(-900000000, wzLane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-1799999999, wzLane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(-4096, wzLane1Nodes
            .get(0).getNodePoint().getPosition()
            .get().getElev());

        assertEquals(-900000000, wzLane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getLat());
        assertEquals(-1799999999, wzLane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getLon());
        assertEquals(-4096, wzLane1Nodes
            .get(1).getNodePoint().getPosition()
            .get().getElev());
    }
}