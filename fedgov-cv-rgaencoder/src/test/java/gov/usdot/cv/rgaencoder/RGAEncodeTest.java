/*
 * Copyright (C) 2024 LEIDOS.
 * Licensed under the Apache License, Version 2.0 [...]
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

    // Existing mocks
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
    ReferencePointInfo mockReferencePointInfo1;
    Position3D mockLocation1;
    DDate mockTimeOfCalculation1;

    GeometryContainer mockGeometryContainer6;
    BicycleLaneGeometryLayer mockBicycleLaneGeometryLayer1;
    IndvBikeLaneGeometryInfo mockIndvBikeLaneGeometryInfo1;
    List<IndvBikeLaneGeometryInfo> mockIndvBikeLaneGeometryInfoList1;
    LaneConstructorType mockLaneConstructorType4;
    DuplicateXYZNodeInfo mockDuplicateXYZNodeInfo2;

    GeometryContainer mockGeometryContainer7;
    BicycleLaneGeometryLayer mockBicycleLaneGeometryLayer2;
    IndvBikeLaneGeometryInfo mockIndvBikeLaneGeometryInfo2;
    List<IndvBikeLaneGeometryInfo> mockIndvBikeLaneGeometryInfoList2;
    LaneConstructorType mockLaneConstructorType5;
    ComputedXYZNodeInfo mockComputedXYZNodeInfo2;
    NodeXYZOffsetInfo mockNodeXYZOffsetInfo3;
    NodeXYZOffsetValue mockNodeXYZOffsetValue7;
    NodeXYZOffsetValue mockNodeXYZOffsetValue8;
    NodeXYZOffsetValue mockNodeXYZOffsetValue9;
    WayPlanarGeometryInfo mockWayPlanarGeometryInfo2;

    GeometryContainer mockGeometryContainer8;
    BicycleLaneGeometryLayer mockBicycleLaneGeometryLayer3;
    IndvBikeLaneGeometryInfo mockIndvBikeLaneGeometryInfo3;
    List<IndvBikeLaneGeometryInfo> mockIndvBikeLaneGeometryInfoList3;
    LaneConstructorType mockLaneConstructorType6;
    PhysicalXYZNodeInfo mockPhysicalXYZNodeInfo2;
    IndividualXYZNodeGeometryInfo mockIndividualXYZNodeGeometryInfo2;
    List<IndividualXYZNodeGeometryInfo> mockIndividualXYZNodeGeometryInfoList2;
    NodeXYZOffsetInfo mockNodeXYZOffsetInfo4;
    NodeXYZOffsetValue mockNodeXYZOffsetValue10;
    NodeXYZOffsetValue mockNodeXYZOffsetValue11;
    NodeXYZOffsetValue mockNodeXYZOffsetValue12;
    ReferencePointInfo mockReferencePointInfo2;
    Position3D mockLocation2;
    DDate mockTimeOfCalculation2;

    GeometryContainer mockGeometryContainer9;
    CrosswalkLaneGeometryLayer mockCrosswalkLaneGeometryLayer1;
    IndvCrosswalkLaneGeometryInfo mockIndvCrosswalkLaneGeometryInfo1;
    List<IndvCrosswalkLaneGeometryInfo> mockIndvCrosswalkLaneGeometryInfoList1;
    LaneConstructorType mockLaneConstructorType7;
    DuplicateXYZNodeInfo mockDuplicateXYZNodeInfo3;

    GeometryContainer mockGeometryContainer10;
    CrosswalkLaneGeometryLayer mockCrosswalkLaneGeometryLayer2;
    IndvCrosswalkLaneGeometryInfo mockIndvCrosswalkLaneGeometryInfo2;
    List<IndvCrosswalkLaneGeometryInfo> mockIndvCrosswalkLaneGeometryInfoList2;
    LaneConstructorType mockLaneConstructorType8;
    ComputedXYZNodeInfo mockComputedXYZNodeInfo3;
    NodeXYZOffsetInfo mockNodeXYZOffsetInfo5;
    NodeXYZOffsetValue mockNodeXYZOffsetValue13;
    NodeXYZOffsetValue mockNodeXYZOffsetValue14;
    NodeXYZOffsetValue mockNodeXYZOffsetValue15;
    WayPlanarGeometryInfo mockWayPlanarGeometryInfo3;

    GeometryContainer mockGeometryContainer11;
    CrosswalkLaneGeometryLayer mockCrosswalkLaneGeometryLayer3;
    IndvCrosswalkLaneGeometryInfo mockIndvCrosswalkLaneGeometryInfo3;
    List<IndvCrosswalkLaneGeometryInfo> mockIndvCrosswalkLaneGeometryInfoList3;
    LaneConstructorType mockLaneConstructorType9;
    PhysicalXYZNodeInfo mockPhysicalXYZNodeInfo3;
    IndividualXYZNodeGeometryInfo mockIndividualXYZNodeGeometryInfo3;
    List<IndividualXYZNodeGeometryInfo> mockIndividualXYZNodeGeometryInfoList3;
    NodeXYZOffsetInfo mockNodeXYZOffsetInfo6;
    NodeXYZOffsetValue mockNodeXYZOffsetValue16;
    NodeXYZOffsetValue mockNodeXYZOffsetValue17;
    NodeXYZOffsetValue mockNodeXYZOffsetValue18;
    ReferencePointInfo mockReferencePointInfo3;
    Position3D mockLocation3;
    DDate mockTimeOfCalculation3;

    // New mocks for additional fields
    WayWidth mockWayWidth1;
    WayWidth mockWayWidth2;
    WayPlanarGeometryInfo mockWayPlanarGeometryInfo4; // For NodeXYZOffsetInfo in PhysicalXYZNodeInfo
    RGATimeRestrictions mockTimeRestrictions1;
    TimeWindowItemControlInfo mockTimeWindowItemCtrl1;
    List<TimeWindowInformation> mockTimeWindowSet1;
    TimeWindowInformation mockTimeWindowInfo1;
    DaysOfTheWeek mockDaysOfTheWeek1;
    DDate mockStartPeriod1;
    DDate mockEndPeriod1;
    GeneralPeriod mockGeneralPeriod1;

    // Constants (assuming these are defined in your classes)
    private static final int PHYSICAL_NODE = LaneConstructorType.PHYSICAL_NODE;
    private static final int COMPUTED_NODE = LaneConstructorType.COMPUTED_NODE;
    private static final int DUPLICATE_NODE = LaneConstructorType.DUPLICATE_NODE;
    private static final int OFFSET_B10 = NodeXYZOffsetValue.OFFSET_B10;
    private static final int OFFSET_B11 = NodeXYZOffsetValue.OFFSET_B11;
    private static final int OFFSET_B12 = NodeXYZOffsetValue.OFFSET_B12;
    private static final int OFFSET_B13 = NodeXYZOffsetValue.OFFSET_B13;
    private static final int OFFSET_B14 = NodeXYZOffsetValue.OFFSET_B14;
    private static final int OFFSET_B16 = NodeXYZOffsetValue.OFFSET_B16;
    private static final int APPROACH_GEOMETRY_LAYER_ID = GeometryContainer.APPROACH_GEOMETRY_LAYER_ID;
    private static final int MOTOR_VEHICLE_LANE_GEOMETRY_LAYER_ID = GeometryContainer.MOTOR_VEHICLE_LANE_GEOMETRY_LAYER_ID;
    private static final int BICYCLE_LANE_GEOMETRY_LAYER_ID = GeometryContainer.BICYCLE_LANE_GEOMETRY_LAYER_ID;
    private static final int CROSSWALK_LANE_GEOMETRY_LAYER_ID = GeometryContainer.CROSSWALK_LANE_GEOMETRY_LAYER_ID;

    @Before
    public void setup() {
        // Existing setup
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
        mockReferencePointInfo1 = mock(ReferencePointInfo.class);
        mockLocation1 = mock(Position3D.class);
        mockTimeOfCalculation1 = mock(DDate.class);

        mockGeometryContainer6 = mock(GeometryContainer.class);
        mockBicycleLaneGeometryLayer1 = mock(BicycleLaneGeometryLayer.class);
        mockIndvBikeLaneGeometryInfo1 = mock(IndvBikeLaneGeometryInfo.class);
        mockLaneConstructorType4 = mock(LaneConstructorType.class);
        mockDuplicateXYZNodeInfo2 = mock(DuplicateXYZNodeInfo.class);

        mockGeometryContainer7 = mock(GeometryContainer.class);
        mockBicycleLaneGeometryLayer2 = mock(BicycleLaneGeometryLayer.class);
        mockIndvBikeLaneGeometryInfo2 = mock(IndvBikeLaneGeometryInfo.class);
        mockLaneConstructorType5 = mock(LaneConstructorType.class);
        mockComputedXYZNodeInfo2 = mock(ComputedXYZNodeInfo.class);
        mockNodeXYZOffsetInfo3 = mock(NodeXYZOffsetInfo.class);
        mockNodeXYZOffsetValue7 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue8 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue9 = mock(NodeXYZOffsetValue.class);
        mockWayPlanarGeometryInfo2 = mock(WayPlanarGeometryInfo.class);

        mockGeometryContainer8 = mock(GeometryContainer.class);
        mockBicycleLaneGeometryLayer3 = mock(BicycleLaneGeometryLayer.class);
        mockIndvBikeLaneGeometryInfo3 = mock(IndvBikeLaneGeometryInfo.class);
        mockLaneConstructorType6 = mock(LaneConstructorType.class);
        mockPhysicalXYZNodeInfo2 = mock(PhysicalXYZNodeInfo.class);
        mockIndividualXYZNodeGeometryInfo2 = mock(IndividualXYZNodeGeometryInfo.class);
        mockNodeXYZOffsetInfo4 = mock(NodeXYZOffsetInfo.class);
        mockNodeXYZOffsetValue10 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue11 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue12 = mock(NodeXYZOffsetValue.class);
        mockReferencePointInfo2 = mock(ReferencePointInfo.class);
        mockLocation2 = mock(Position3D.class);
        mockTimeOfCalculation2 = mock(DDate.class);

        mockGeometryContainer9 = mock(GeometryContainer.class);
        mockCrosswalkLaneGeometryLayer1 = mock(CrosswalkLaneGeometryLayer.class);
        mockIndvCrosswalkLaneGeometryInfo1 = mock(IndvCrosswalkLaneGeometryInfo.class);
        mockLaneConstructorType7 = mock(LaneConstructorType.class);
        mockDuplicateXYZNodeInfo3 = mock(DuplicateXYZNodeInfo.class);

        mockGeometryContainer10 = mock(GeometryContainer.class);
        mockCrosswalkLaneGeometryLayer2 = mock(CrosswalkLaneGeometryLayer.class);
        mockIndvCrosswalkLaneGeometryInfo2 = mock(IndvCrosswalkLaneGeometryInfo.class);
        mockLaneConstructorType8 = mock(LaneConstructorType.class);
        mockComputedXYZNodeInfo3 = mock(ComputedXYZNodeInfo.class);
        mockNodeXYZOffsetInfo5 = mock(NodeXYZOffsetInfo.class);
        mockNodeXYZOffsetValue13 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue14 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue15 = mock(NodeXYZOffsetValue.class);
        mockWayPlanarGeometryInfo3 = mock(WayPlanarGeometryInfo.class);

        mockGeometryContainer11 = mock(GeometryContainer.class);
        mockCrosswalkLaneGeometryLayer3 = mock(CrosswalkLaneGeometryLayer.class);
        mockIndvCrosswalkLaneGeometryInfo3 = mock(IndvCrosswalkLaneGeometryInfo.class);
        mockLaneConstructorType9 = mock(LaneConstructorType.class);
        mockPhysicalXYZNodeInfo3 = mock(PhysicalXYZNodeInfo.class);
        mockIndividualXYZNodeGeometryInfo3 = mock(IndividualXYZNodeGeometryInfo.class);
        mockNodeXYZOffsetInfo6 = mock(NodeXYZOffsetInfo.class);
        mockNodeXYZOffsetValue16 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue17 = mock(NodeXYZOffsetValue.class);
        mockNodeXYZOffsetValue18 = mock(NodeXYZOffsetValue.class);
        mockReferencePointInfo3 = mock(ReferencePointInfo.class);
        mockLocation3 = mock(Position3D.class);
        mockTimeOfCalculation3 = mock(DDate.class);

        // New mocks initialization
        mockWayWidth1 = mock(WayWidth.class);
        mockWayWidth2 = mock(WayWidth.class);
        mockWayPlanarGeometryInfo4 = mock(WayPlanarGeometryInfo.class);
        mockTimeRestrictions1 = mock(RGATimeRestrictions.class);
        mockTimeWindowItemCtrl1 = mock(TimeWindowItemControlInfo.class);
        mockTimeWindowInfo1 = mock(TimeWindowInformation.class);
        mockTimeWindowSet1 = Arrays.asList(mockTimeWindowInfo1);
        mockDaysOfTheWeek1 = mock(DaysOfTheWeek.class);
        mockStartPeriod1 = mock(DDate.class);
        mockEndPeriod1 = mock(DDate.class);
        mockGeneralPeriod1 = mock(GeneralPeriod.class);

        encoder = new Encoder();

        // BaseLayer setup
        when(mockBaseLayer.getMajorVer()).thenReturn(22);
        when(mockBaseLayer.getMinorVer()).thenReturn(11);
        when(mockBaseLayer.isFullRdAuthIDExists()).thenReturn(false);
        when(mockBaseLayer.getFullRdAuthID()).thenReturn(new int[]{1, 2, 83493});
        when(mockBaseLayer.isRelRdAuthIDExists()).thenReturn(true);
        when(mockBaseLayer.getRelRdAuthID()).thenReturn(new int[]{8, 4, 8571});
        when(mockBaseLayer.getRelativeToRdAuthID()).thenReturn(new int[]{1, 3, 6, 1, 4, 1, 311, 21, 20});
        when(mockLocation.getLatitude()).thenReturn(7.2);
        when(mockLocation.getLongitude()).thenReturn(11.1);
        when(mockLocation.isElevationExists()).thenReturn(true);
        when(mockLocation.getElevation()).thenReturn(13.12f);
        when(mockTimeOfCalculation.getMonth()).thenReturn(8);
        when(mockTimeOfCalculation.getDay()).thenReturn(21);
        when(mockTimeOfCalculation.getYear()).thenReturn(2024);
        when(mockBaseLayer.getContentVer()).thenReturn(13);
        when(mockContentDateTime.getHour()).thenReturn(13);
        when(mockContentDateTime.getMinute()).thenReturn(51);
        when(mockContentDateTime.getSecond()).thenReturn(20);
        when(mockBaseLayer.getLocation()).thenReturn(mockLocation);
        when(mockBaseLayer.getTimeOfCalculation()).thenReturn(mockTimeOfCalculation);
        when(mockBaseLayer.getContentDateTime()).thenReturn(mockContentDateTime);

        // GeometryContainer setup
        when(mockGeometryContainer1.getGeometryContainerID()).thenReturn(APPROACH_GEOMETRY_LAYER_ID);
        when(mockIndividualApproachGeometryInfo1.getApproachID()).thenReturn(2);
        when(mockIndividualApproachGeometryInfo2.getApproachID()).thenReturn(3);
        mockIndividualApproachGeometryInfoList1 = Arrays.asList(mockIndividualApproachGeometryInfo1, mockIndividualApproachGeometryInfo2);
        when(mockApproachGeometryLayer1.getApproachGeomApproachSet()).thenReturn(mockIndividualApproachGeometryInfoList1);
        when(mockGeometryContainer1.getApproachGeometryLayer()).thenReturn(mockApproachGeometryLayer1);

        when(mockGeometryContainer2.getGeometryContainerID()).thenReturn(APPROACH_GEOMETRY_LAYER_ID);
        when(mockIndividualApproachGeometryInfo3.getApproachID()).thenReturn(4);
        mockIndividualApproachGeometryInfoList2 = Arrays.asList(mockIndividualApproachGeometryInfo3);
        when(mockApproachGeometryLayer2.getApproachGeomApproachSet()).thenReturn(mockIndividualApproachGeometryInfoList2);
        when(mockGeometryContainer2.getApproachGeometryLayer()).thenReturn(mockApproachGeometryLayer2);

        when(mockDuplicateXYZNodeInfo1.getRefLaneID()).thenReturn(23);
        when(mockLaneConstructorType1.getChoice()).thenReturn(DUPLICATE_NODE);
        when(mockLaneConstructorType1.getDuplicateXYZNodeInfo()).thenReturn(mockDuplicateXYZNodeInfo1);
        when(mockIndvMtrVehLaneGeometryInfo1.getLaneID()).thenReturn(11);
        when(mockIndvMtrVehLaneGeometryInfo1.getLaneConstructorType()).thenReturn(mockLaneConstructorType1);
        mockIndvMtrVehLaneGeometryInfoList1 = Arrays.asList(mockIndvMtrVehLaneGeometryInfo1);
        when(mockMotorVehicleLaneGeometryLayer1.getLaneGeomLaneSet()).thenReturn(mockIndvMtrVehLaneGeometryInfoList1);
        when(mockGeometryContainer3.getGeometryContainerID()).thenReturn(MOTOR_VEHICLE_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer3.getMotorVehicleLaneGeometryLayer()).thenReturn(mockMotorVehicleLaneGeometryLayer1);

        when(mockNodeXYZOffsetValue1.getChoice()).thenReturn(OFFSET_B10);
        when(mockNodeXYZOffsetValue1.getOffsetB10()).thenReturn(2L);
        when(mockNodeXYZOffsetValue2.getChoice()).thenReturn(OFFSET_B11);
        when(mockNodeXYZOffsetValue2.getOffsetB11()).thenReturn(3L);
        when(mockNodeXYZOffsetValue3.getChoice()).thenReturn(OFFSET_B12);
        when(mockNodeXYZOffsetValue3.getOffsetB12()).thenReturn(4L);
        when(mockNodeXYZOffsetInfo1.getNodeXOffsetValue()).thenReturn(mockNodeXYZOffsetValue1);
        when(mockNodeXYZOffsetInfo1.getNodeYOffsetValue()).thenReturn(mockNodeXYZOffsetValue2);
        when(mockNodeXYZOffsetInfo1.getNodeZOffsetValue()).thenReturn(mockNodeXYZOffsetValue3);
        when(mockComputedXYZNodeInfo1.getRefLaneID()).thenReturn(24);
        when(mockComputedXYZNodeInfo1.getLaneCenterLineXYZOffset()).thenReturn(mockNodeXYZOffsetInfo1);
        when(mockComputedXYZNodeInfo1.getLanePlanarGeomInfo()).thenReturn(mockWayPlanarGeometryInfo1);
        when(mockLaneConstructorType2.getChoice()).thenReturn(COMPUTED_NODE);
        when(mockLaneConstructorType2.getComputedXYZNodeInfo()).thenReturn(mockComputedXYZNodeInfo1);
        when(mockIndvMtrVehLaneGeometryInfo2.getLaneID()).thenReturn(12);
        when(mockIndvMtrVehLaneGeometryInfo2.getLaneConstructorType()).thenReturn(mockLaneConstructorType2);
        mockIndvMtrVehLaneGeometryInfoList2 = Arrays.asList(mockIndvMtrVehLaneGeometryInfo2);
        when(mockMotorVehicleLaneGeometryLayer2.getLaneGeomLaneSet()).thenReturn(mockIndvMtrVehLaneGeometryInfoList2);
        when(mockGeometryContainer4.getGeometryContainerID()).thenReturn(MOTOR_VEHICLE_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer4.getMotorVehicleLaneGeometryLayer()).thenReturn(mockMotorVehicleLaneGeometryLayer2);

        when(mockNodeXYZOffsetValue4.getChoice()).thenReturn(OFFSET_B13);
        when(mockNodeXYZOffsetValue4.getOffsetB13()).thenReturn(100L);
        when(mockNodeXYZOffsetValue5.getChoice()).thenReturn(OFFSET_B14);
        when(mockNodeXYZOffsetValue5.getOffsetB14()).thenReturn(150L);
        when(mockNodeXYZOffsetValue6.getChoice()).thenReturn(OFFSET_B16);
        when(mockNodeXYZOffsetValue6.getOffsetB16()).thenReturn(200L);
        when(mockNodeXYZOffsetInfo2.getNodeXOffsetValue()).thenReturn(mockNodeXYZOffsetValue4);
        when(mockNodeXYZOffsetInfo2.getNodeYOffsetValue()).thenReturn(mockNodeXYZOffsetValue5);
        when(mockNodeXYZOffsetInfo2.getNodeZOffsetValue()).thenReturn(mockNodeXYZOffsetValue6);
        when(mockIndividualXYZNodeGeometryInfo1.getNodeLocPlanarGeomInfo()).thenReturn(mockWayPlanarGeometryInfo4);
        when(mockIndividualXYZNodeGeometryInfo1.getNodeXYZOffsetInfo()).thenReturn(mockNodeXYZOffsetInfo2);
        mockIndividualXYZNodeGeometryInfoList1 = Arrays.asList(mockIndividualXYZNodeGeometryInfo1);
        when(mockPhysicalXYZNodeInfo1.getNodeXYZGeometryNodeSet()).thenReturn(mockIndividualXYZNodeGeometryInfoList1);
        when(mockPhysicalXYZNodeInfo1.getReferencePointInfo()).thenReturn(mockReferencePointInfo1);
        when(mockReferencePointInfo1.getLocation()).thenReturn(mockLocation1);
        when(mockReferencePointInfo1.getTimeOfCalculation()).thenReturn(mockTimeOfCalculation1);
        when(mockLaneConstructorType3.getChoice()).thenReturn(PHYSICAL_NODE);
        when(mockLaneConstructorType3.getPhysicalXYZNodeInfo()).thenReturn(mockPhysicalXYZNodeInfo1);
        when(mockIndvMtrVehLaneGeometryInfo3.getLaneID()).thenReturn(13);
        when(mockIndvMtrVehLaneGeometryInfo3.getLaneConstructorType()).thenReturn(mockLaneConstructorType3);
        mockIndvMtrVehLaneGeometryInfoList3 = Arrays.asList(mockIndvMtrVehLaneGeometryInfo3);
        when(mockMotorVehicleLaneGeometryLayer3.getLaneGeomLaneSet()).thenReturn(mockIndvMtrVehLaneGeometryInfoList3);
        when(mockGeometryContainer5.getGeometryContainerID()).thenReturn(MOTOR_VEHICLE_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer5.getMotorVehicleLaneGeometryLayer()).thenReturn(mockMotorVehicleLaneGeometryLayer3);

        when(mockDuplicateXYZNodeInfo2.getRefLaneID()).thenReturn(33);
        when(mockLaneConstructorType4.getChoice()).thenReturn(DUPLICATE_NODE);
        when(mockLaneConstructorType4.getDuplicateXYZNodeInfo()).thenReturn(mockDuplicateXYZNodeInfo2);
        when(mockIndvBikeLaneGeometryInfo1.getLaneID()).thenReturn(14);
        when(mockIndvBikeLaneGeometryInfo1.getLaneConstructorType()).thenReturn(mockLaneConstructorType4);
        mockIndvBikeLaneGeometryInfoList1 = Arrays.asList(mockIndvBikeLaneGeometryInfo1);
        when(mockBicycleLaneGeometryLayer1.getLaneGeomLaneSet()).thenReturn(mockIndvBikeLaneGeometryInfoList1);
        when(mockGeometryContainer6.getGeometryContainerID()).thenReturn(BICYCLE_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer6.getBicycleLaneGeometryLayer()).thenReturn(mockBicycleLaneGeometryLayer1);

        when(mockNodeXYZOffsetValue7.getChoice()).thenReturn(OFFSET_B10);
        when(mockNodeXYZOffsetValue7.getOffsetB10()).thenReturn(2L);
        when(mockNodeXYZOffsetValue8.getChoice()).thenReturn(OFFSET_B11);
        when(mockNodeXYZOffsetValue8.getOffsetB11()).thenReturn(3L);
        when(mockNodeXYZOffsetValue9.getChoice()).thenReturn(OFFSET_B12);
        when(mockNodeXYZOffsetValue9.getOffsetB12()).thenReturn(4L);
        when(mockNodeXYZOffsetInfo3.getNodeXOffsetValue()).thenReturn(mockNodeXYZOffsetValue7);
        when(mockNodeXYZOffsetInfo3.getNodeYOffsetValue()).thenReturn(mockNodeXYZOffsetValue8);
        when(mockNodeXYZOffsetInfo3.getNodeZOffsetValue()).thenReturn(mockNodeXYZOffsetValue9);
        when(mockComputedXYZNodeInfo2.getRefLaneID()).thenReturn(34);
        when(mockComputedXYZNodeInfo2.getLaneCenterLineXYZOffset()).thenReturn(mockNodeXYZOffsetInfo3);
        when(mockComputedXYZNodeInfo2.getLanePlanarGeomInfo()).thenReturn(mockWayPlanarGeometryInfo2);
        when(mockLaneConstructorType5.getChoice()).thenReturn(COMPUTED_NODE);
        when(mockLaneConstructorType5.getComputedXYZNodeInfo()).thenReturn(mockComputedXYZNodeInfo2);
        when(mockIndvBikeLaneGeometryInfo2.getLaneID()).thenReturn(15);
        when(mockIndvBikeLaneGeometryInfo2.getLaneConstructorType()).thenReturn(mockLaneConstructorType5);
        mockIndvBikeLaneGeometryInfoList2 = Arrays.asList(mockIndvBikeLaneGeometryInfo2);
        when(mockBicycleLaneGeometryLayer2.getLaneGeomLaneSet()).thenReturn(mockIndvBikeLaneGeometryInfoList2);
        when(mockGeometryContainer7.getGeometryContainerID()).thenReturn(BICYCLE_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer7.getBicycleLaneGeometryLayer()).thenReturn(mockBicycleLaneGeometryLayer2);

        when(mockNodeXYZOffsetValue10.getChoice()).thenReturn(OFFSET_B13);
        when(mockNodeXYZOffsetValue10.getOffsetB13()).thenReturn(10L);
        when(mockNodeXYZOffsetValue11.getChoice()).thenReturn(OFFSET_B14);
        when(mockNodeXYZOffsetValue11.getOffsetB14()).thenReturn(15L);
        when(mockNodeXYZOffsetValue12.getChoice()).thenReturn(OFFSET_B16);
        when(mockNodeXYZOffsetValue12.getOffsetB16()).thenReturn(20L);
        when(mockNodeXYZOffsetInfo4.getNodeXOffsetValue()).thenReturn(mockNodeXYZOffsetValue10);
        when(mockNodeXYZOffsetInfo4.getNodeYOffsetValue()).thenReturn(mockNodeXYZOffsetValue11);
        when(mockNodeXYZOffsetInfo4.getNodeZOffsetValue()).thenReturn(mockNodeXYZOffsetValue12);
        when(mockIndividualXYZNodeGeometryInfo2.getNodeLocPlanarGeomInfo()).thenReturn(mockWayPlanarGeometryInfo4);
        when(mockIndividualXYZNodeGeometryInfo2.getNodeXYZOffsetInfo()).thenReturn(mockNodeXYZOffsetInfo4);
        mockIndividualXYZNodeGeometryInfoList2 = Arrays.asList(mockIndividualXYZNodeGeometryInfo2);
        when(mockPhysicalXYZNodeInfo2.getNodeXYZGeometryNodeSet()).thenReturn(mockIndividualXYZNodeGeometryInfoList2);
        when(mockPhysicalXYZNodeInfo2.getReferencePointInfo()).thenReturn(mockReferencePointInfo2);
        when(mockReferencePointInfo2.getLocation()).thenReturn(mockLocation2);
        when(mockReferencePointInfo2.getTimeOfCalculation()).thenReturn(mockTimeOfCalculation2);
        when(mockLaneConstructorType6.getChoice()).thenReturn(PHYSICAL_NODE);
        when(mockLaneConstructorType6.getPhysicalXYZNodeInfo()).thenReturn(mockPhysicalXYZNodeInfo2);
        when(mockIndvBikeLaneGeometryInfo3.getLaneID()).thenReturn(16);
        when(mockIndvBikeLaneGeometryInfo3.getLaneConstructorType()).thenReturn(mockLaneConstructorType6);
        when(mockIndvBikeLaneGeometryInfo3.getTimeRestrictions()).thenReturn(mockTimeRestrictions1);
        mockIndvBikeLaneGeometryInfoList3 = Arrays.asList(mockIndvBikeLaneGeometryInfo3);
        when(mockBicycleLaneGeometryLayer3.getLaneGeomLaneSet()).thenReturn(mockIndvBikeLaneGeometryInfoList3);
        when(mockGeometryContainer8.getGeometryContainerID()).thenReturn(BICYCLE_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer8.getBicycleLaneGeometryLayer()).thenReturn(mockBicycleLaneGeometryLayer3);

        when(mockDuplicateXYZNodeInfo3.getRefLaneID()).thenReturn(43);
        when(mockLaneConstructorType7.getChoice()).thenReturn(DUPLICATE_NODE);
        when(mockLaneConstructorType7.getDuplicateXYZNodeInfo()).thenReturn(mockDuplicateXYZNodeInfo3);
        when(mockIndvCrosswalkLaneGeometryInfo1.getLaneID()).thenReturn(17);
        when(mockIndvCrosswalkLaneGeometryInfo1.getLaneConstructorType()).thenReturn(mockLaneConstructorType7);
        mockIndvCrosswalkLaneGeometryInfoList1 = Arrays.asList(mockIndvCrosswalkLaneGeometryInfo1);
        when(mockCrosswalkLaneGeometryLayer1.getLaneGeomLaneSet()).thenReturn(mockIndvCrosswalkLaneGeometryInfoList1);
        when(mockGeometryContainer9.getGeometryContainerID()).thenReturn(CROSSWALK_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer9.getCrosswalkLaneGeometryLayer()).thenReturn(mockCrosswalkLaneGeometryLayer1);

        when(mockNodeXYZOffsetValue13.getChoice()).thenReturn(OFFSET_B10);
        when(mockNodeXYZOffsetValue13.getOffsetB10()).thenReturn(2L);
        when(mockNodeXYZOffsetValue14.getChoice()).thenReturn(OFFSET_B11);
        when(mockNodeXYZOffsetValue14.getOffsetB11()).thenReturn(3L);
        when(mockNodeXYZOffsetValue15.getChoice()).thenReturn(OFFSET_B12);
        when(mockNodeXYZOffsetValue15.getOffsetB12()).thenReturn(4L);
        when(mockNodeXYZOffsetInfo5.getNodeXOffsetValue()).thenReturn(mockNodeXYZOffsetValue13);
        when(mockNodeXYZOffsetInfo5.getNodeYOffsetValue()).thenReturn(mockNodeXYZOffsetValue14);
        when(mockNodeXYZOffsetInfo5.getNodeZOffsetValue()).thenReturn(mockNodeXYZOffsetValue15);
        when(mockComputedXYZNodeInfo3.getRefLaneID()).thenReturn(44);
        when(mockComputedXYZNodeInfo3.getLaneCenterLineXYZOffset()).thenReturn(mockNodeXYZOffsetInfo5);
        when(mockComputedXYZNodeInfo3.getLanePlanarGeomInfo()).thenReturn(mockWayPlanarGeometryInfo3);
        when(mockLaneConstructorType8.getChoice()).thenReturn(COMPUTED_NODE);
        when(mockLaneConstructorType8.getComputedXYZNodeInfo()).thenReturn(mockComputedXYZNodeInfo3);
        when(mockIndvCrosswalkLaneGeometryInfo2.getLaneID()).thenReturn(18);
        when(mockIndvCrosswalkLaneGeometryInfo2.getLaneConstructorType()).thenReturn(mockLaneConstructorType8);
        mockIndvCrosswalkLaneGeometryInfoList2 = Arrays.asList(mockIndvCrosswalkLaneGeometryInfo2);
        when(mockCrosswalkLaneGeometryLayer2.getLaneGeomLaneSet()).thenReturn(mockIndvCrosswalkLaneGeometryInfoList2);
        when(mockGeometryContainer10.getGeometryContainerID()).thenReturn(CROSSWALK_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer10.getCrosswalkLaneGeometryLayer()).thenReturn(mockCrosswalkLaneGeometryLayer2);

        when(mockNodeXYZOffsetValue16.getChoice()).thenReturn(OFFSET_B13);
        when(mockNodeXYZOffsetValue16.getOffsetB13()).thenReturn(101L);
        when(mockNodeXYZOffsetValue17.getChoice()).thenReturn(OFFSET_B14);
        when(mockNodeXYZOffsetValue17.getOffsetB14()).thenReturn(151L);
        when(mockNodeXYZOffsetValue18.getChoice()).thenReturn(OFFSET_B16);
        when(mockNodeXYZOffsetValue18.getOffsetB16()).thenReturn(201L);
        when(mockNodeXYZOffsetInfo6.getNodeXOffsetValue()).thenReturn(mockNodeXYZOffsetValue16);
        when(mockNodeXYZOffsetInfo6.getNodeYOffsetValue()).thenReturn(mockNodeXYZOffsetValue17);
        when(mockNodeXYZOffsetInfo6.getNodeZOffsetValue()).thenReturn(mockNodeXYZOffsetValue18);
        when(mockIndividualXYZNodeGeometryInfo3.getNodeLocPlanarGeomInfo()).thenReturn(mockWayPlanarGeometryInfo4);
        when(mockIndividualXYZNodeGeometryInfo3.getNodeXYZOffsetInfo()).thenReturn(mockNodeXYZOffsetInfo6);
        mockIndividualXYZNodeGeometryInfoList3 = Arrays.asList(mockIndividualXYZNodeGeometryInfo3);
        when(mockPhysicalXYZNodeInfo3.getNodeXYZGeometryNodeSet()).thenReturn(mockIndividualXYZNodeGeometryInfoList3);
        when(mockPhysicalXYZNodeInfo3.getReferencePointInfo()).thenReturn(mockReferencePointInfo3);
        when(mockReferencePointInfo3.getLocation()).thenReturn(mockLocation3);
        when(mockReferencePointInfo3.getTimeOfCalculation()).thenReturn(mockTimeOfCalculation3);
        when(mockLaneConstructorType9.getChoice()).thenReturn(PHYSICAL_NODE);
        when(mockLaneConstructorType9.getPhysicalXYZNodeInfo()).thenReturn(mockPhysicalXYZNodeInfo3);
        when(mockIndvCrosswalkLaneGeometryInfo3.getLaneID()).thenReturn(19);
        when(mockIndvCrosswalkLaneGeometryInfo3.getLaneConstructorType()).thenReturn(mockLaneConstructorType9);
        when(mockIndvCrosswalkLaneGeometryInfo3.getTimeRestrictions()).thenReturn(mockTimeRestrictions1);
        mockIndvCrosswalkLaneGeometryInfoList3 = Arrays.asList(mockIndvCrosswalkLaneGeometryInfo3);
        when(mockCrosswalkLaneGeometryLayer3.getLaneGeomLaneSet()).thenReturn(mockIndvCrosswalkLaneGeometryInfoList3);
        when(mockGeometryContainer11.getGeometryContainerID()).thenReturn(CROSSWALK_LANE_GEOMETRY_LAYER_ID);
        when(mockGeometryContainer11.getCrosswalkLaneGeometryLayer()).thenReturn(mockCrosswalkLaneGeometryLayer3);

        // New setup for WayWidth and TimeRestrictions
        when(mockWayWidth1.getChoice()).thenReturn((byte)0); // FULL_WIDTH
        when(mockWayWidth1.getFullWidth()).thenReturn(500);
        when(mockWayWidth2.getChoice()).thenReturn((byte)1); // DELTA_WIDTH
        when(mockWayWidth2.getDeltaWidth()).thenReturn(300);
        when(mockWayPlanarGeometryInfo1.getWayWidth()).thenReturn(mockWayWidth1);
        when(mockWayPlanarGeometryInfo4.getWayWidth()).thenReturn(mockWayWidth2);
        when(mockIndividualXYZNodeGeometryInfo3.getNodeLocPlanarGeomInfo()).thenReturn(mockWayPlanarGeometryInfo4);
        when(mockIndividualXYZNodeGeometryInfo3.getNodeLocPlanarGeomInfo()).thenReturn(mockWayPlanarGeometryInfo4);
        when(mockIndividualXYZNodeGeometryInfo3.getNodeLocPlanarGeomInfo()).thenReturn(mockWayPlanarGeometryInfo4);
        when(mockLocation1.getLatitude()).thenReturn(40.7128);
        when(mockLocation1.getLongitude()).thenReturn(-74.0060);
        when(mockLocation1.isElevationExists()).thenReturn(false);
        when(mockTimeOfCalculation1.getYear()).thenReturn(2025);
        when(mockTimeOfCalculation1.getMonth()).thenReturn(1);
        when(mockTimeOfCalculation1.getDay()).thenReturn(15);
        when(mockTimeRestrictions1.getFixedTimeWindowCtrl()).thenReturn(mockTimeWindowItemCtrl1);
        when(mockTimeWindowItemCtrl1.getTimeWindowSet()).thenReturn(mockTimeWindowSet1);

        when(mockRGA.getBaseLayer()).thenReturn(mockBaseLayer);
        mockGeometryContainerList = Arrays.asList(mockGeometryContainer1, mockGeometryContainer2,
                mockGeometryContainer3, mockGeometryContainer4, mockGeometryContainer5, mockGeometryContainer6,
                mockGeometryContainer7, mockGeometryContainer8, mockGeometryContainer9, mockGeometryContainer10,
                mockGeometryContainer11);
        when(mockRGA.getGeometryContainers()).thenReturn(mockGeometryContainerList);
    }

    @Test
    public void rgaEncodeTester() {
        ByteArrayObject res = encoder.encode(mockRGA);
        byte[] expected = { 0, 43, -128, -111, 2, 4, 4, 88, 44, -102, -46, 116, -125, -75, -92, -23, 5, 8, 6, -65, 68,
                85, 65, 2, 1, 48, -98, -63, 64, 32, 96, -64, 32, -128, 48, 70, -30, -94, -127, -65, -33, -94, 42, -36,
                -64, 5, 26, 66, -128, 12, 16, -125, 0, 0, -128, 16, 4, 16, 0, 11, 65, 112, 66, -64, 0, -62, 24, 18, 2,
                44, 3, 52, 2, 0, 4, 48, 0, 13, 0, 0, 120, 50, 34, 9, 101, 64, 100, 0, -127, 0, 1, -56, 66, 8, 40, 0, 30,
                68, 66, 64, 69, -128, 102, -128, 64, -125, 0, 2, 0, 0, 15, 0, -92, 64, 30, -88, 1, 64, 12, 16, 0, 69,
                10, -64, -62, -128, 4, -120, -80, 72, 8, -80, 12, -48, 8, 12, 48, 0, 76, 0, 1, -32, -54, -120, 37, -43,
                1, -110, 0 };
        Assert.assertArrayEquals(expected, res.getMessage());
    }

    @Test
    public void testWithFullRAID() {
        when(mockBaseLayer.isFullRdAuthIDExists()).thenReturn(true);
        when(mockBaseLayer.isRelRdAuthIDExists()).thenReturn(false);
        when(mockBaseLayer.getFullRdAuthID()).thenReturn(new int[]{1, 2, 83493});

        ByteArrayObject res = encoder.encode(mockRGA);
        Assert.assertNotNull(res);
        logger.info("Encoded RGA with full RAID: " + Arrays.toString(res.getMessage()));
    }

    @Test
    public void testWithWayWidthAndReferencePoint() {
        when(mockWayPlanarGeometryInfo1.getWayWidth()).thenReturn(mockWayWidth1);
        when(mockWayPlanarGeometryInfo4.getWayWidth()).thenReturn(mockWayWidth2);
        when(mockLocation1.getLatitude()).thenReturn(40.7128);
        when(mockLocation1.getLongitude()).thenReturn(-74.0060);
        when(mockLocation1.isElevationExists()).thenReturn(false);
        when(mockReferencePointInfo1.getLocation()).thenReturn(mockLocation1);
        when(mockTimeOfCalculation1.getYear()).thenReturn(2025);
        when(mockTimeOfCalculation1.getMonth()).thenReturn(1);
        when(mockTimeOfCalculation1.getDay()).thenReturn(15);

        ByteArrayObject res = encoder.encode(mockRGA);
        Assert.assertNotNull(res);
        logger.info("Encoded RGA with WayWidth and ReferencePoint: " + Arrays.toString(res.getMessage()));
    }

    @Test
    public void testWithTimeRestrictions() {
        when(mockIndvBikeLaneGeometryInfo3.getTimeRestrictions()).thenReturn(mockTimeRestrictions1);
        when(mockIndvCrosswalkLaneGeometryInfo3.getTimeRestrictions()).thenReturn(mockTimeRestrictions1);

        ByteArrayObject res = encoder.encode(mockRGA);
        Assert.assertNotNull(res);
        logger.info("Encoded RGA with TimeRestrictions: " + Arrays.toString(res.getMessage()));
    }

    @Test
    public void testWithEmptyGeometryContainers() {
        when(mockRGA.getGeometryContainers()).thenReturn(Collections.emptyList());

        ByteArrayObject res = encoder.encode(mockRGA);
        Assert.assertNotNull(res);
        logger.info("Encoded RGA with empty GeometryContainers: " + Arrays.toString(res.getMessage()));
    }

    @Test
    public void testWithNullGeometryContainers() {
        when(mockRGA.getGeometryContainers()).thenReturn(null);

        ByteArrayObject res = encoder.encode(mockRGA);
        Assert.assertNotNull(res);
        logger.info("Encoded RGA with null GeometryContainers: " + Arrays.toString(res.getMessage()));
    }
}