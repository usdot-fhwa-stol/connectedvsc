package gov.usdot.cv.msg.builder.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GeoPointTest {
	
	@Test
	public void testOffsetCalculationAt42Lat() {
		GeoPoint point1 = new GeoPoint(42.0, -83.0);
		GeoPoint point2 = new GeoPoint(42.00123, -83.00145);
		
		// Comparing our calculation against results from https://www.movable-type.co.uk/scripts/latlong-vincenty.html
		// Note, to get the X-Axis(Longitude), only change the value Y-Axis(Latitude)
		// and to get the Y-Axis(Latitude), only change the value of the X-Axis(Longitude)
		final double CONTROL_LONGITUDE_OFFSET = 120.13 * 100;
		final double CONTROL_LATITUDE_OFFSET = -136.62 * 100;

		short ourXOffset = point1.getLonOffsetInCentimeters(point2);
		assertEquals(CONTROL_LONGITUDE_OFFSET, ourXOffset, 100.);
		
		short ourYOffset = point1.getLatOffsetInCentimeters(point2);
		assertEquals(CONTROL_LATITUDE_OFFSET, ourYOffset, 100.);
	}
	
	@Test
	public void testOffsetCalculationAt28Lat() {
		GeoPoint point1 = new GeoPoint(28.0, -83.0);
		GeoPoint point2 = new GeoPoint(28.00216, -83.00192);
		
		// Comparing our calculation against results from https://www.movable-type.co.uk/scripts/latlong-vincenty.html
		// Note, to get the X-Axis(Longitude), only change the value Y-Axis(Latitude)
		// and to get the Y-Axis(Latitude), only change the value of the X-Axis(Longitude)
		final double CONTROL_LONGITUDE_OFFSET = 188.86 * 100;
		final double CONTROL_LATITUDE_OFFSET = -239.37 * 100;

		short ourXOffset = point1.getLonOffsetInCentimeters(point2);
		assertEquals(CONTROL_LONGITUDE_OFFSET, ourXOffset, 100.);
		
		short ourYOffset = point1.getLatOffsetInCentimeters(point2);
		assertEquals(CONTROL_LATITUDE_OFFSET, ourYOffset, 100.);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testLatOffsetTooSmall() {
		GeoPoint point1 = new GeoPoint(42.0, -83.0);
		GeoPoint point2 = new GeoPoint(42.1, -83.00145);
		point1.getLatOffsetInCentimeters(point2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testLatOffsetTooLarge() {
		GeoPoint point1 = new GeoPoint(42.0, -83.0);
		GeoPoint point2 = new GeoPoint(42.1, -83.00145);
		point2.getLatOffsetInCentimeters(point1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testLonOffsetTooSmall() {
		GeoPoint point1 = new GeoPoint(42.0, -83.0);
		GeoPoint point2 = new GeoPoint(42.00123, -83.1);
		point2.getLonOffsetInCentimeters(point1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testLonOffsetTooLarge() {
		GeoPoint point1 = new GeoPoint(42.0, -83.0);
		GeoPoint point2 = new GeoPoint(42.00123, -83.1);
		point1.getLonOffsetInCentimeters(point2);
	}
}
