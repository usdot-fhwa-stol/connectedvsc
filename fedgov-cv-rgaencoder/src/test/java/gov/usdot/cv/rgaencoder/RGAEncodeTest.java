/*
 * Copyright (C) 2024 LEIDOS.
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
package gov.usdot.cv.rgaencoder;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gov.usdot.cv.mapencoder.ByteArrayObject;
import gov.usdot.cv.mapencoder.Position3D;

public class RGAEncodeTest {
    private static final Logger logger = LogManager.getLogger(RGAEncodeTest.class);
    Encoder encoder;
    RGAData mockRGA;
    BaseLayer mockBaseLayer;
    Position3D mockLocation;
    DDate mockTimeOfCalculation;
    DDateTime mockContentDateTime;

    List<GeometryContainer> mockGeometryContainerList;

    GeometryContainer mockGeometryContainer1;
    ApproachGeometryLayer mockApproachGeometryLayer1;
    IndividualApproachGeometryInfo mockIndividualApproachGeometryInfo1;
    IndividualApproachGeometryInfo mockIndividualApproachGeometryInfo2;
    List<IndividualApproachGeometryInfo> mockIndividualApproachGeometryInfoList1;

    GeometryContainer mockGeometryContainer2;
    ApproachGeometryLayer mockApproachGeometryLayer2;
    IndividualApproachGeometryInfo mockIndividualApproachGeometryInfo3;
    List<IndividualApproachGeometryInfo> mockIndividualApproachGeometryInfoList2;

    GeometryContainer mockGeometryContainer3;
    MotorVehicleLaneGeometryLayer mockMotorVehicleLaneGeometryLayer1;
    IndvMtrVehLaneGeometryInfo mockIndvMtrVehLaneGeometryInfo1;
    List<IndvMtrVehLaneGeometryInfo> mockIndvMtrVehLaneGeometryInfoList1;
    DuplicateXYZNodeInfo mockDuplicateXYZNodeInfo1;
    LaneConstructorType mockLaneConstructorType1;

    GeometryContainer mockGeometryContainer4;
    MotorVehicleLaneGeometryLayer mockMotorVehicleLaneGeometryLayer2;
    IndvMtrVehLaneGeometryInfo mockIndvMtrVehLaneGeometryInfo2;
    List<IndvMtrVehLaneGeometryInfo> mockIndvMtrVehLaneGeometryInfoList2;
    LaneConstructorType mockLaneConstructorType2;
    ComputedXYZNodeInfo mockComputedXYZNodeInfo1;
    NodeXYZOffsetInfo mockNodeXYZOffsetInfo1;
    NodeXYZOffsetValue mockNodeXYZOffsetValue1;
    NodeXYZOffsetValue mockNodeXYZOffsetValue2;
    NodeXYZOffsetValue mockNodeXYZOffsetValue3;
    // WayWidth mockWayWidth1;
    WayPlanarGeometryInfo mockWayPlanarGeometryInfo1;

    GeometryContainer mockGeometryContainer5;
    MotorVehicleLaneGeometryLayer mockMotorVehicleLaneGeometryLayer3;
    IndvMtrVehLaneGeometryInfo mockIndvMtrVehLaneGeometryInfo3;
    List<IndvMtrVehLaneGeometryInfo> mockIndvMtrVehLaneGeometryInfoList3;
    LaneConstructorType mockLaneConstructorType3;
    PhysicalXYZNodeInfo mockPhysicalXYZNodeInfo1;
    IndividualXYZNodeGeometryInfo mockIndividualXYZNodeGeometryInfo1;
    List<IndividualXYZNodeGeometryInfo> mockIndividualXYZNodeGeometryInfoList1;
    NodeXYZOffsetInfo mockNodeXYZOffsetInfo2;
    NodeXYZOffsetValue mockNodeXYZOffsetValue4;
    NodeXYZOffsetValue mockNodeXYZOffsetValue5;
    NodeXYZOffsetValue mockNodeXYZOffsetValue6;

    
    @Before
    public void setup() {
        mockRGA = mock(RGAData.class); 
        mockBaseLayer = mock(BaseLayer.class);
        mockLocation = mock(Position3D.class);
        mockTimeOfCalculation = mock(DDate.class);
        mockContentDateTime = mock(DDateTime.class);

        mockGeometryContainer1 = mock(GeometryContainer.class);
        mockApproachGeometryLayer1 = mock(ApproachGeometryLayer.class);
        mockIndividualApproachGeometryInfo1 = mock(IndividualApproachGeometryInfo.class);
        mockIndividualApproachGeometryInfo2 = mock(IndividualApproachGeometryInfo.class);

        mockGeometryContainer2 = mock(GeometryContainer.class);
        mockApproachGeometryLayer2 = mock(ApproachGeometryLayer.class);
        mockIndividualApproachGeometryInfo3 = mock(IndividualApproachGeometryInfo.class);

        mockGeometryContainer3 = mock(GeometryContainer.class);
        mockMotorVehicleLaneGeometryLayer1 = mock(MotorVehicleLaneGeometryLayer.class);
        mockIndvMtrVehLaneGeometryInfo1 = mock(IndvMtrVehLaneGeometryInfo.class);
        mockDuplicateXYZNodeInfo1 = mock(DuplicateXYZNodeInfo.class);
        mockLaneConstructorType1 = mock(LaneConstructorType.class);

        mockGeometryContainer4 = mock(GeometryContainer.class);
        mockMotorVehicleLaneGeometryLayer2 = mock(MotorVehicleLaneGeometryLayer.class);
        mockIndvMtrVehLaneGeometryInfo2 = mock(IndvMtrVehLaneGeometryInfo.class);
        mockLaneConstructorType2 = mock(LaneConstructorType.class);
        mockComputedXYZNodeInfo1 = mock(ComputedXYZNodeInfo.class);
        mockNodeXYZOffsetInfo1 = mock(NodeXYZOffsetInfo.class);
        mockNodeXYZOffsetValue1 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue2 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue3 = mock(NodeXYZOffsetValue.class);
        mockWayPlanarGeometryInfo1 = mock(WayPlanarGeometryInfo.class);
        // mockWayWidth1 = mock(WayWidth.class);

        mockGeometryContainer5 = mock(GeometryContainer.class);
        mockMotorVehicleLaneGeometryLayer3 = mock(MotorVehicleLaneGeometryLayer.class);
        mockIndvMtrVehLaneGeometryInfo3 = mock(IndvMtrVehLaneGeometryInfo.class);
        mockLaneConstructorType3 = mock(LaneConstructorType.class);
        mockPhysicalXYZNodeInfo1 = mock(PhysicalXYZNodeInfo.class);
        mockIndividualXYZNodeGeometryInfo1 = mock(IndividualXYZNodeGeometryInfo.class);
        mockNodeXYZOffsetInfo2 = mock(NodeXYZOffsetInfo.class);
        mockNodeXYZOffsetValue4 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue5 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue6 = mock(NodeXYZOffsetValue.class);

        encoder = new Encoder();

        when(mockBaseLayer.getMajorVer()).thenReturn(22);
        when(mockBaseLayer.getMinorVer()).thenReturn(11);

        // RAID
        when(mockBaseLayer.isFullRdAuthIDExists()).thenReturn(false);
        when(mockBaseLayer.getFullRdAuthID()).thenReturn(new int[]{1, 2, 83493});
        when(mockBaseLayer.isRelRdAuthIDExists()).thenReturn(true);
        when(mockBaseLayer.getRelRdAuthID()).thenReturn(new int[]{8, 4, 8571});

        when(mockBaseLayer.getRelativeToRdAuthID()).thenReturn(new int[]{1, 3, 6, 1, 4, 1, 311, 21, 20});
    
        //location
        when(mockLocation.getLatitude()).thenReturn((double)7.2);
        when(mockLocation.getLongitude()).thenReturn((double)11.1);
        when(mockLocation.isElevationExists()).thenReturn(true);
        when(mockLocation.getElevation()).thenReturn((float)13.12);

        //TimeOfCalculation
        when(mockTimeOfCalculation.getMonth()).thenReturn(8);
        when(mockTimeOfCalculation.getDay()).thenReturn(21);
        when(mockTimeOfCalculation.getYear()).thenReturn(2024);

        when(mockBaseLayer.getContentVer()).thenReturn(13);

        //ContentDateTime
        when(mockContentDateTime.getHour()).thenReturn(13);
        when(mockContentDateTime.getMinute()).thenReturn(51);
        when(mockContentDateTime.getSecond()).thenReturn(20);
        
        when(mockBaseLayer.getLocation()).thenReturn(mockLocation);
        when(mockBaseLayer.getTimeOfCalculation()).thenReturn(mockTimeOfCalculation);
        when(mockBaseLayer.getContentDateTime()).thenReturn(mockContentDateTime);

        when(mockGeometryContainer1.getGeometryContainerID()).thenReturn(GeometryContainer.APPROACH_GEOMETRY_LAYER_ID);
        when(mockIndividualApproachGeometryInfo1.getApproachID()).thenReturn(2);
        when(mockIndividualApproachGeometryInfo2.getApproachID()).thenReturn(3);
        mockIndividualApproachGeometryInfoList1 = Arrays.asList(mockIndividualApproachGeometryInfo1, mockIndividualApproachGeometryInfo2);
        when(mockApproachGeometryLayer1.getApproachGeomApproachSet()).thenReturn(mockIndividualApproachGeometryInfoList1);
        when(mockGeometryContainer1.getApproachGeometryLayer()).thenReturn(mockApproachGeometryLayer1);

        when(mockGeometryContainer2.getGeometryContainerID()).thenReturn(GeometryContainer.APPROACH_GEOMETRY_LAYER_ID);
        when(mockIndividualApproachGeometryInfo3.getApproachID()).thenReturn(4);
        mockIndividualApproachGeometryInfoList2 = Arrays.asList(mockIndividualApproachGeometryInfo3);
        when(mockApproachGeometryLayer2.getApproachGeomApproachSet()).thenReturn(mockIndividualApproachGeometryInfoList2);
        when(mockGeometryContainer2.getApproachGeometryLayer()).thenReturn(mockApproachGeometryLayer2);

        when(mockDuplicateXYZNodeInfo1.getRefLaneID()).thenReturn(23);
        when(mockLaneConstructorType1.getChoice()).thenReturn(LaneConstructorType.DUPLICATE_NODE);
        when(mockLaneConstructorType1.getDuplicateXYZNodeInfo()).thenReturn(mockDuplicateXYZNodeInfo1);
        when(mockIndvMtrVehLaneGeometryInfo1.getLaneID()).thenReturn(11);
        when(mockIndvMtrVehLaneGeometryInfo1.getLaneConstructorType()).thenReturn(mockLaneConstructorType1);
        mockIndvMtrVehLaneGeometryInfoList1 = Arrays.asList(mockIndvMtrVehLaneGeometryInfo1);
        when(mockMotorVehicleLaneGeometryLayer1.getLaneGeomLaneSet()).thenReturn(mockIndvMtrVehLaneGeometryInfoList1);
        when(mockGeometryContainer3.getGeometryContainerID()).thenReturn(GeometryContainer.MOTOR_VEHICLE_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer3.getMotorVehicleLaneGeometryLayer()).thenReturn(mockMotorVehicleLaneGeometryLayer1);

        when(mockNodeXYZOffsetValue1.getChoice()).thenReturn(NodeXYZOffsetValue.OFFSET_B10);
        when(mockNodeXYZOffsetValue1.getOffsetB10()).thenReturn((long)2);
        when(mockNodeXYZOffsetValue2.getChoice()).thenReturn(NodeXYZOffsetValue.OFFSET_B11);
        when(mockNodeXYZOffsetValue2.getOffsetB11()).thenReturn((long)3);
        when(mockNodeXYZOffsetValue3.getChoice()).thenReturn(NodeXYZOffsetValue.OFFSET_B12);
        when(mockNodeXYZOffsetValue3.getOffsetB12()).thenReturn((long)4);
        when(mockNodeXYZOffsetInfo1.getNodeXOffsetValue()).thenReturn(mockNodeXYZOffsetValue1);
        when(mockNodeXYZOffsetInfo1.getNodeYOffsetValue()).thenReturn(mockNodeXYZOffsetValue2);
        when(mockNodeXYZOffsetInfo1.getNodeZOffsetValue()).thenReturn(mockNodeXYZOffsetValue3);
        when(mockComputedXYZNodeInfo1.getRefLaneID()).thenReturn(24);
        when(mockComputedXYZNodeInfo1.getLaneCenterLineXYZOffset()).thenReturn(mockNodeXYZOffsetInfo1);
        when(mockComputedXYZNodeInfo1.getLanePlanarGeomInfo()).thenReturn(mockWayPlanarGeometryInfo1);
        when(mockLaneConstructorType2.getChoice()).thenReturn(LaneConstructorType.COMPUTED_NODE);
        when(mockLaneConstructorType2.getComputedXYZNodeInfo()).thenReturn(mockComputedXYZNodeInfo1);
        when(mockIndvMtrVehLaneGeometryInfo2.getLaneID()).thenReturn(12);
        when(mockIndvMtrVehLaneGeometryInfo2.getLaneConstructorType()).thenReturn(mockLaneConstructorType2);
        mockIndvMtrVehLaneGeometryInfoList2 = Arrays.asList(mockIndvMtrVehLaneGeometryInfo2);
        when(mockMotorVehicleLaneGeometryLayer2.getLaneGeomLaneSet()).thenReturn(mockIndvMtrVehLaneGeometryInfoList2);
        when(mockGeometryContainer4.getGeometryContainerID()).thenReturn(GeometryContainer.MOTOR_VEHICLE_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer4.getMotorVehicleLaneGeometryLayer()).thenReturn(mockMotorVehicleLaneGeometryLayer2);

        when(mockNodeXYZOffsetValue4.getChoice()).thenReturn(NodeXYZOffsetValue.OFFSET_B13);
        when(mockNodeXYZOffsetValue4.getOffsetB13()).thenReturn((long)100);
        when(mockNodeXYZOffsetValue5.getChoice()).thenReturn(NodeXYZOffsetValue.OFFSET_B14);
        when(mockNodeXYZOffsetValue5.getOffsetB14()).thenReturn((long)150);
        when(mockNodeXYZOffsetValue6.getChoice()).thenReturn(NodeXYZOffsetValue.OFFSET_B16);
        when(mockNodeXYZOffsetValue6.getOffsetB16()).thenReturn((long)200);
        when(mockNodeXYZOffsetInfo2.getNodeXOffsetValue()).thenReturn(mockNodeXYZOffsetValue4);
        when(mockNodeXYZOffsetInfo2.getNodeYOffsetValue()).thenReturn(mockNodeXYZOffsetValue5);
        when(mockNodeXYZOffsetInfo2.getNodeZOffsetValue()).thenReturn(mockNodeXYZOffsetValue6);
        when(mockIndividualXYZNodeGeometryInfo1.getNodeXYZOffsetInfo()).thenReturn(mockNodeXYZOffsetInfo2);
        mockIndividualXYZNodeGeometryInfoList1 = Arrays.asList(mockIndividualXYZNodeGeometryInfo1);
        when(mockPhysicalXYZNodeInfo1.getNodeXYZGeometryNodeSet()).thenReturn(mockIndividualXYZNodeGeometryInfoList1);
        when(mockLaneConstructorType3.getChoice()).thenReturn(LaneConstructorType.PHYSICAL_NODE);
        when(mockLaneConstructorType3.getPhysicalXYZNodeInfo()).thenReturn(mockPhysicalXYZNodeInfo1);
        when(mockIndvMtrVehLaneGeometryInfo3.getLaneID()).thenReturn(13);
        when(mockIndvMtrVehLaneGeometryInfo3.getLaneConstructorType()).thenReturn(mockLaneConstructorType3);
        mockIndvMtrVehLaneGeometryInfoList3 = Arrays.asList(mockIndvMtrVehLaneGeometryInfo3);
        when(mockMotorVehicleLaneGeometryLayer3.getLaneGeomLaneSet()).thenReturn(mockIndvMtrVehLaneGeometryInfoList3);
        when(mockGeometryContainer5.getGeometryContainerID()).thenReturn(GeometryContainer.MOTOR_VEHICLE_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer5.getMotorVehicleLaneGeometryLayer()).thenReturn(mockMotorVehicleLaneGeometryLayer3);

        when(mockRGA.getBaseLayer()).thenReturn(mockBaseLayer);
        mockGeometryContainerList = Arrays.asList(mockGeometryContainer1, mockGeometryContainer2, mockGeometryContainer3, mockGeometryContainer4, mockGeometryContainer5);
        when(mockRGA.getGeometryContainers()).thenReturn(mockGeometryContainerList);
    }

    @Test
    public void rgaEncodeTester() {
        ByteArrayObject res = encoder.encode(mockRGA);
        byte[] expected = { 0, 43, 44, 0, 4, 4, 88, 44, -102, -46, 116, -125, -75, -92, -23, 5, 8, 6, -65, 68, 85, 65, 2, 1, 48, -98, -63, 64, 32, 96, -64, 32, -128, 48, 70, -30, -94, -127, -65, -33, -94, 42, -36, -64, 5, 26, 64 };
        // Assert.assertArrayEquals(expected, res.getMessage());
    }
}