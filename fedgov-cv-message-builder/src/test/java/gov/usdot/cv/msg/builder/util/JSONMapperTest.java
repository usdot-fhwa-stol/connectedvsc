package gov.usdot.cv.msg.builder.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.usdot.cv.msg.builder.input.IntersectionInputData;
import gov.usdot.cv.msg.builder.input.IntersectionInputData.Approach;
import gov.usdot.cv.msg.builder.input.IntersectionInputData.DrivingLane;
import gov.usdot.cv.msg.builder.input.IntersectionInputData.Intersection;
import gov.usdot.cv.msg.builder.input.IntersectionInputData.State;
import gov.usdot.cv.msg.builder.input.IntersectionInputData.IntersectionGeometry;
import gov.usdot.cv.msg.builder.input.IntersectionInputData.LaneList;
import gov.usdot.cv.msg.builder.input.IntersectionInputData.LaneNode;
import gov.usdot.cv.msg.builder.input.IntersectionInputData.MapData;
import gov.usdot.cv.msg.builder.input.IntersectionInputData.ReferencePoint;
import gov.usdot.cv.msg.builder.input.IntersectionInputData.SpatData;
import gov.usdot.cv.msg.builder.input.IntersectionInputData.VerifiedPoint;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;


public class JSONMapperTest {

	@Test (expected = JsonParseException.class)
	public void testInvalidJSON() throws JsonParseException, JsonMappingException, IOException {
		String notJson = "Invalid JSON";
		JSONMapper.jsonStringToPojo(notJson, IntersectionInputData.class);
	}
	
	public void testInvalidMapping() throws JsonParseException, JsonMappingException, IOException {
		// now set to ignore unknown fields so not throwing a parse exception, just check for nulls
		String notJson = "{ \"test\": 1 }";
		IntersectionInputData inputData = JSONMapper.jsonStringToPojo(notJson, IntersectionInputData.class);
		ReferencePoint referencePoint = inputData.mapData.intersectionGeometry.referencePoint;
		VerifiedPoint verifiedPoint = inputData.mapData.intersectionGeometry.verifiedPoint;
		assertNull(referencePoint);
		assertNull(verifiedPoint);
	}
	
	@Test
	public void testValidJSON() throws JsonParseException, JsonMappingException, IOException {
		IntersectionInputData inputData = JSONMapper.jsonFileToPojo("src/test/resources/intersection2.json", IntersectionInputData.class);
		assertNotNull(inputData);
		
		MapData mapData = inputData.mapData;
		assertNotNull(mapData);
		assertEquals(352090, mapData.minuteOfTheYear);
		assertEquals("intersectionData", mapData.layerType);
		IntersectionGeometry intersectionGeometry = mapData.intersectionGeometry;
		assertNotNull(intersectionGeometry);

		ReferencePoint referencePoint = intersectionGeometry.referencePoint;
		assertNotNull(referencePoint);
		assertEquals("StreetNames", referencePoint.descriptiveIntersctionName);
		assertEquals(3, referencePoint.layerID);
		assertEquals(11, referencePoint.msgCount);
		assertEquals(366, referencePoint.masterLaneWidth);
		assertEquals(42.337649011479286, referencePoint.referenceLat, .0001);
		assertEquals(-83.0513240932538, referencePoint.referenceLon, .0001);
		assertEquals(100., referencePoint.referenceElevation, .0001);
		
		VerifiedPoint verifiedPoint = intersectionGeometry.verifiedPoint;
		assertNotNull(verifiedPoint);
		assertEquals(42.33799399312783, verifiedPoint.verifiedMapLat, .0001);
		assertEquals(-83.05076619377935, verifiedPoint.verifiedMapLon, .0001);
		assertEquals(102., verifiedPoint.verifiedMapElevation, .0001);
		assertEquals(42.33799399312783, verifiedPoint.verifiedSurveyedLat, .0001);
		assertEquals(-83.05076619377935, verifiedPoint.verifiedSurveyedLon, .0001);
		assertEquals(102., verifiedPoint.verifiedSurveyedElevation, .0001);
		
		LaneList laneList = intersectionGeometry.laneList;
		assertNotNull(laneList);

		Approach[] approaches = laneList.approach;
		assertNotNull(approaches);
		assertEquals(1, approaches.length);

		Approach approach = approaches[0];
		assertEquals(1, approach.approachID);	
		assertEquals("Ingress", approach.approachType);	
		assertEquals("(2) Northwestbound", approach.laneDirection);

		DrivingLane[] drivingLanes = approach.drivingLanes;
		assertNotNull(drivingLanes);
		assertEquals(2, drivingLanes.length);

		DrivingLane lane = approach.drivingLanes[0];
		assertNotNull(lane);
		assertEquals("01", lane.laneID);
		assertEquals("Vehicle", lane.laneType);

		int[] laneManeuvers = lane.laneManeuvers;
		assertNotNull(laneManeuvers);
		assertEquals(1, lane.laneManeuvers.length);
		assertEquals(2, lane.laneManeuvers[0]);
		
		LaneNode[] laneNodes = lane.laneNodes;
		assertNotNull(laneNodes);
		assertEquals(3, laneNodes.length);

		LaneNode node = laneNodes[0];
		assertEquals(0, node.nodeNumber);
		assertEquals(42.33758913516711, node.nodeLat, .0001);
		assertEquals(-83.05116798869764, node.nodeLong, .0001);
		
		node = laneNodes[1];
		assertEquals(1, node.nodeNumber);
		assertEquals(42.33740078213973, node.nodeLat, .0001);
		assertEquals(-83.05106606475493, node.nodeLong, .0001);
		
		node = laneNodes[2];
		assertEquals(2, node.nodeNumber);
		assertEquals(42.33732940610814, node.nodeLat, .0001);
		assertEquals(-83.05103387824677, node.nodeLong, .0001);
		
		lane = approach.drivingLanes[1];
		assertNotNull(lane);
		assertEquals("03", lane.laneID);
		assertEquals("Bike", lane.laneType);

		laneManeuvers = lane.laneManeuvers;
		assertNotNull(laneManeuvers);
		assertEquals(1, lane.laneManeuvers.length);
		assertEquals(2, lane.laneManeuvers[0]);
		
		laneNodes = lane.laneNodes;
		assertNotNull(laneNodes);
		assertEquals(3, laneNodes.length);

		node = laneNodes[0];
		assertEquals(0, node.nodeNumber);
		assertEquals(42.33758715250687, node.nodeLat, .0001);
		assertEquals(-83.05112775556174, node.nodeLong, .0001);

		node = laneNodes[1];
		assertEquals(1, node.nodeNumber);
		assertEquals(42.33744440078575, node.nodeLat, .0001);
		assertEquals(-83.05103119603723, node.nodeLong, .0001);
		
		node = laneNodes[2];
		assertEquals(2, node.nodeNumber);
		assertEquals(42.337343284787366, node.nodeLat, .0001);
		assertEquals(-83.05097755185723, node.nodeLong, .0001);
		
		SpatData spatData = mapData.spatData;
		assertNotNull(spatData);
		
		Intersection intersection = spatData.intersections;
		assertNotNull(intersection);
		
		assertEquals(12345, intersection.id);
		assertEquals(0, intersection.status);
		State[] states = intersection.states;
		assertNotNull(states);
		assertEquals(2, intersection.states.length);
		
		State state = states[0];
		assertNotNull(state);
		assertEquals("01", state.laneSet);
//		assertEquals(1, state.phase);
//		assertEquals(10, state.time);
		assertEquals(0, state.stateConfidence);
//		assertEquals(1, state.yellStateConfidence);
		
		state = states[1];
		assertNotNull(state);
		assertEquals("03", state.laneSet);
//		assertEquals(1, state.phase);
//		assertEquals(1, state.time);
		assertEquals(0, state.stateConfidence);
//		assertEquals(3, state.yellStateConfidence);			
	}

	
}
