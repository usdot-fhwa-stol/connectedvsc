package gov.usdot.cv.msg.builder.util;

import gov.usdot.cv.msg.builder.util.Vincenty.DistanceAndBearing;

public class GeoPoint {
	
	private double lat;
	private double lon;
	
	public GeoPoint(double lat, double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
	
	public short getLatOffsetInCentimeters(GeoPoint fromPoint) {
		// Calculate the distance on the Y-Axis(Latitude) by only changing the X-Axis(Longitude)
		DistanceAndBearing dab = Vincenty.getDistanceAndBearing(fromPoint.lat, fromPoint.lon,
																this.lat, fromPoint.lon);
		
		double latOffset = dab.getDistance();
		
		// Distance calculated by Vincenty provides no insight in directionality.
		// Use bearing to determine correct sign for offset.
		if(dab.getInitialBearing() > 90 && dab.getInitialBearing() < 270) {
			latOffset = -latOffset;
		}
		
		// Convert to CM
		latOffset *= 100;

		validateShortRange(fromPoint, latOffset, "latitude offset", "centimeters");
		
		return (short)Math.round(latOffset);
	}
	
	public short getLonOffsetInCentimeters(GeoPoint fromPoint) {
		// Calculate the distance on the X-Axis(Longitude) by only changing the Y-Axis(Latitude)
		DistanceAndBearing dab = Vincenty.getDistanceAndBearing(fromPoint.lat, fromPoint.lon,
																fromPoint.lat, this.lon);
		
		double lonOffset = dab.getDistance();
		
		// Distance calculated by Vincenty provides no insight in directionality.
		// Use bearing to determine correct sign for offset.
		if(dab.getInitialBearing() > 180 && dab.getInitialBearing() < 360) {
			lonOffset = -lonOffset;
		}
											
		// Convert to CM
		lonOffset *= 100;

		validateShortRange(fromPoint, lonOffset, "longitude offset", "centimeters");
		
		return (short)Math.round(lonOffset);
	}

	public short getDistanceInCentimeters(GeoPoint fromPoint) {
		DistanceAndBearing dab = Vincenty.getDistanceAndBearing(fromPoint.lat, fromPoint.lon,
																this.lat, this.lon);
		
		double dist = dab.getDistance();
		
		// Convert to CM
		dist *= 100;
		
		validateShortRange(fromPoint, dist, "distance", "centimeters");
		
		return (short)Math.round(dist);
	}
	
	public short getLatOffsetInMeters(GeoPoint fromPoint) {
		// Calculate the distance on the Y-Axis(Latitude) by only changing the X-Axis(Longitude)
		DistanceAndBearing dab = Vincenty.getDistanceAndBearing(fromPoint.lat, fromPoint.lon,
																this.lat, fromPoint.lon);
		
		double latOffset = dab.getDistance();
		
		// Distance calculated by Vincenty provides no insight in directionality.
		// Use bearing to determine correct sign for offset.
		if(dab.getInitialBearing() > 90 && dab.getInitialBearing() < 270) {
			latOffset = -latOffset;
		}

		validateShortRange(fromPoint, latOffset, "latitude offset", "meters");
		
		return (short)Math.round(latOffset);
	}
	
	public short getLonOffsetInMeters(GeoPoint fromPoint) {
		// Calculate the distance on the X-Axis(Longitude) by only changing the Y-Axis(Latitude)
		DistanceAndBearing dab = Vincenty.getDistanceAndBearing(fromPoint.lat, fromPoint.lon,
																fromPoint.lat, this.lon);
		
		double lonOffset = dab.getDistance();
		
		// Distance calculated by Vincenty provides no insight in directionality.
		// Use bearing to determine correct sign for offset.
		if(dab.getInitialBearing() > 180 && dab.getInitialBearing() < 360) {
			lonOffset = -lonOffset;
		}

		validateShortRange(fromPoint, lonOffset, "longitude offset", "meters");
		
		return (short)Math.round(lonOffset);
	}
	
	public short getDistanceInMeters(GeoPoint fromPoint) {
		DistanceAndBearing dab = Vincenty.getDistanceAndBearing(fromPoint.lat, fromPoint.lon,
																this.lat, this.lon);
		
		double dist = dab.getDistance();
		
		validateShortRange(fromPoint, dist, "distance", "meters");
		
		return (short)Math.round(dist);
	}
	
	private void validateShortRange(GeoPoint fromPoint, double value, String measurementType, String units) {
		if (value > Short.MAX_VALUE) {
			throw new IllegalArgumentException(
					String.format("The %s of %f %s between %s and %s cannot be greater than %d", 
										measurementType,
										value,
										units,
										fromPoint.toString(),
										this.toString(),
										Short.MAX_VALUE));
		}
		if (value < Short.MIN_VALUE) {
			throw new IllegalArgumentException(
					String.format("The %s of %f %s between %s and %s cannot be less than %d", 
										measurementType,
										value,
										units,
										fromPoint.toString(),
										this.toString(),
										Short.MIN_VALUE));
		}
	}

	@Override
	public String toString() {
		return "GeoPoint [lat=" + lat + ", lon=" + lon + "]";
	}
}
