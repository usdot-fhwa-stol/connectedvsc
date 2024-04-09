package gov.usdot.cv.msg.builder.util;

public class Vincenty {
	
	// WGS-84 ellipsoid params
	private static final double a = 6378137;			// Semi-Major Axis
	private static final double b = 6356752.314245;		// Semi-Minor Axis
	private static final double f = 1 / 298.257223563;	// Flattening

	/**
	 * Calculates geodetic distance between two points specified by latitude/longitude using Vincenty inverse formula
	 * for ellipsoids
	 * 
	 * @param lat1
	 *            first point latitude in decimal degrees
	 * @param lon1
	 *            first point longitude in decimal degrees
	 * @param lat2
	 *            second point latitude in decimal degrees
	 * @param lon2
	 *            second point longitude in decimal degrees
	 * @returns distance in meters between points with 5.10<sup>-4</sup> precision
	 * @see <a href="http://www.movable-type.co.uk/scripts/latlong-vincenty.html">Originally posted here</a>
	 */
	// http://stackoverflow.com/questions/120283/working-with-latitude-longitude-values-in-java#9822531
	public static DistanceAndBearing getDistanceAndBearing(double lat1, double lon1, double lat2, double lon2) {
		// Convert Lat/Lon to radians
		lat1 = Math.toRadians(lat1);
		lon1 = Math.toRadians(lon1);
		lat2 = Math.toRadians(lat2);
		lon2 = Math.toRadians(lon2);
		
	    double L = lon2 - lon1;
	    double U1 = Math.atan((1 - f) * Math.tan(lat1));
	    double U2 = Math.atan((1 - f) * Math.tan(lat2));
	    double sinU1 = Math.sin(U1);
	    double cosU1 = Math.cos(U1);
	    double sinU2 = Math.sin(U2);
	    double cosU2 = Math.cos(U2);

	    double sinLambda, cosLambda, sinSigma, cosSigma, sigma, sinAlpha, cosSqAlpha, cos2SigmaM;
	    double lambda = L; 
	    double lambdaP; 
	    double iterLimit = 100;
	    do {
	        sinLambda = Math.sin(lambda);
	        cosLambda = Math.cos(lambda);
	        sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda)
	                + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
	        
	        if(sinSigma == 0) {
	        	return new DistanceAndBearing(Double.NaN, Double.NaN, Double.NaN); // co-incident points
	        }
	        
	        cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
	        sigma = Math.atan2(sinSigma, cosSigma);
	        sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
	        cosSqAlpha = 1 - sinAlpha * sinAlpha;
	        cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
	        
	        if(Double.isNaN(cos2SigmaM)) {
	            cos2SigmaM = 0; // equatorial line: cosSqAlpha=0 (§6)
	        }
	        
	        double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
	        lambdaP = lambda;
	        lambda = L + (1 - C) * f * sinAlpha
	                * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
	    } while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);

	    if(iterLimit == 0) {
	        return new DistanceAndBearing(Double.NaN, Double.NaN, Double.NaN); // formula failed to converge
	    }

	    double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
	    double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
	    double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
	    double deltaSigma = B
	            * sinSigma
	            * (cos2SigmaM + B
	                    / 4
	                    * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM
	                            * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
	    
	    double distance = b * A * (sigma - deltaSigma);
	    double alpha1 = Math.atan2(cosU2 * sinLambda, cosU1 * sinU2 - sinU1 * cosU2 * cosLambda);
	    double alpha2 = Math.atan2(cosU1 * sinLambda, -sinU1 * cosU2 + cosU1 * sinU2 * cosLambda);
		
	    // Normalize to 0..360
	    alpha1 = (alpha1 + 2*Math.PI) % (2*Math.PI); 
		alpha2 = (alpha2 + 2*Math.PI) % (2*Math.PI);
		
		// Convert to degrees
		alpha1 = Math.toDegrees(alpha1);
		alpha2 = Math.toDegrees(alpha2);
		
	    DistanceAndBearing result = new DistanceAndBearing(distance, alpha1, alpha2);
	    
	    return result;
	}
	
	/* Work to provide command line utility to demonstrate Lat/Lon -> X/Y & back from X/Y -> Lat/Lon
	public static void main(String[] args) 
	{		
		// Concatenate all args to allow the user to enter Lat/Lons on command line with white space
		String allArgs = "";
		for(String arg : args) {
			allArgs += arg;
		}
allArgs = "(33.8429408, -112.1352055),(33.843169, -112.13532),(33.843473, -112.135353),(33.84383, -112.135409),(33.844667, -112.135718)";
//allArgs = "33.8429408, -112.1352055,33.843169, -112.13532,33.843473, -112.135353,33.84383, -112.135409,33.844667, -112.135718";
		allArgs = allArgs.replaceAll("\\s","");
		
		// Print the header
		System.out.println("From Latitude,From Longitude," +
						   "To Latitude,To Longitude," +
						   "Latitude Offset in CM,Longitude Offset in CM");
		// Regex to extract between parenthesis
		Pattern parenPattern = Pattern.compile("\\((.*?)\\)");
		
		// Split on ; to get all Lat/Lon chains
		String[] latLonValuesChains = allArgs.split(";");
		
		// Work through the chains to perform the calculations
		for(String latLonValuesChain : latLonValuesChains) {
			ArrayList<GeoPoint> gpChain = new ArrayList<GeoPoint>();
			// Assume input used parenthesis if we find an open paren
			if(latLonValuesChain.contains("(")){
				Matcher parenMatcher = parenPattern.matcher(latLonValuesChain);
				
				while(parenMatcher.find()) {
					String[] latLon = parenMatcher.group(1).split(",");
					gpChain.add(new GeoPoint(
										Double.parseDouble(latLon[0]),
										Double.parseDouble(latLon[1])));
				}
				
			}
			else {
				// Input of Lat/Lons not separated by parenthesis
				String[] values = latLonValuesChain.split(",");
				double lat = -1, lon = -1;
				for(int i = 0; i < values.length; i++) {
					if(i%2 == 0) {
						// Lats are on even indices
						lat = Double.parseDouble(values[i]);
					}
					else {
						// Lons are on odd indices
						lon = Double.parseDouble(values[i]);
						
						// If we got a lon, we must have gotten a lat so we have a complete GeoPoint
						gpChain.add(new GeoPoint(lat, lon));
					}
				}
			}
			
for(GeoPoint gp : gpChain) {
	System.out.println(gp);
}
			// Work through all the GeoPoints to calculate the expected values
			for(int i = 0; i < gpChain.size(); i++) {
				if(i+1 != gpChain.size()) {
					GeoPoint gpFrom = gpChain.get(i);
					GeoPoint gpTo = gpChain.get(i+1);
					
					DistanceAndBearing result = calculateVincenty(gpFrom.getLat(), gpFrom.getLon(), gpTo.getLat(), gpTo.getLon());
					
					System.out.println("\nRESULT:\n" + result + "\n\n");
					
					short latOffset = gpTo.getLatOffsetInCentimeters(gpFrom);
					short lonOffset = gpTo.getLonOffsetInCentimeters(gpFrom);
					
					StringBuilder outLine = new StringBuilder();
					outLine.append(gpFrom.getLat()).append(",");	// From Latitude
					outLine.append(gpFrom.getLon()).append(",");	// From Longitude
					outLine.append(gpTo.getLat()).append(",");		// To Latitude
					outLine.append(gpTo.getLon()).append(",");		// To Longitude
					outLine.append(latOffset).append(",");			// Latitude Offset in CM
					outLine.append(lonOffset).append(",");			// Longitude Offset in CM
					
					// Wolfgang implementation
					GeoPoint backWolfgang = new GeoPoint(
													convertXYToLat_J2945_1_Wolfgang(gpTo.getLat(), lonOffset),
													convertXYToLon_J2945_1_Wolfgang(gpTo.getLat(), gpTo.getLon(), latOffset));

					DistanceAndBearing wolfgangResult = calculateVincenty(gpFrom.getLat(), gpFrom.getLon(), backWolfgang.getLat(), backWolfgang.getLon());
							
					outLine.append("\n\tWG");
					outLine.append(" ; ").append(backWolfgang.getLat()); // Vincenty Distance Between Points
					outLine.append(" , ").append(backWolfgang.getLon());// 
					outLine.append(" , ").append(wolfgangResult.getDistance());
					
					// By Initial Bearing implementation
					GeoPoint backIB = new GeoPoint(
											convertXYToLat_J2945_1_By_Heading(gpTo.getLat(), result.getInitialBearing(), latOffset, lonOffset),
											convertXYToLon_J2945_1_By_Heading(gpTo.getLat(), gpTo.getLon(), result.getInitialBearing(), latOffset, lonOffset)
										);
					
					DistanceAndBearing ibResult = calculateVincenty(gpFrom.getLat(), gpFrom.getLon(), backIB.getLat(), backIB.getLon());
					
					outLine.append("\n\tIB");
					outLine.append(" ; ").append(backIB.getLat()); // Vincenty Distance Between Points
					outLine.append(" , ").append(backIB.getLon());// 
					outLine.append(" , ").append(ibResult.getDistance());
					
					// By Final Bearing implementation
					GeoPoint backFB = new GeoPoint(
											convertXYToLat_J2945_1_By_Heading(gpTo.getLat(), result.getFinalBearing(), latOffset, lonOffset),
											convertXYToLon_J2945_1_By_Heading(gpTo.getLat(), gpTo.getLon(), result.getFinalBearing(), latOffset, lonOffset)
										);
					
					DistanceAndBearing fbResult = calculateVincenty(gpFrom.getLat(), gpFrom.getLon(), backFB.getLat(), backFB.getLon());
					
					outLine.append("\n\tFB");
					outLine.append(" ; ").append(backFB.getLat()); // Vincenty Distance Between Points
					outLine.append(" , ").append(backFB.getLon());// 
					outLine.append(" , ").append(fbResult.getDistance());
					
					
					System.out.print(outLine);
					System.out.println();
				}
			}
			
			System.out.println();
		}
		
	}*/
	
	/* Attempts to convert from X/Y -> Lat/Lon
	private static double convertXYToLat_J2945_1_Wolfgang(double refLat, double deltaYinCM) {
		// Use Radians and Meters
		refLat = Math.toRadians(refLat);
		double deltaYinM = deltaYinCM / 100;
		
		double a = 6378137;
		double f = 0.003353;
		double f1 = Math.pow(f*(2-f), 0.5);
		double f2 = a * (1 - Math.pow(f1, 2)) / Math.pow((1 - Math.pow(f1, 2) * Math.pow(Math.sin(refLat), 2)), 3/2);
		
		double N = deltaYinM;
		
		double newLat = (1 / f2) * N + refLat;
		
		return Math.toDegrees(newLat);
	}
	
	private static double convertXYToLon_J2945_1_Wolfgang(double refLat, double refLon, double deltaXinCM) {
		// Use Radians and Meters
		refLat = Math.toRadians(refLat);
		refLon = Math.toRadians(refLon);
		double deltaXinM = deltaXinCM / 100;
		
		double a = 6378137;
		double f = 0.003353;
		double f1 = Math.pow(f*(2-f), 0.5);
		double f3 = a / Math.pow((1 - Math.pow(f1, 2) * Math.pow(Math.sin(refLat), 2)), 1/2);
		
		double E = deltaXinM;
		
		double newLon = (1 / (f3 * Math.cos(refLat))) * E + refLon;
		
		return Math.toDegrees(newLon);
	}
	
	private static double convertXYToLat_J2945_1_By_Heading(double refLat, double refHeading, double deltaXinCM, double deltaYinCM) {
		// Use Radians and Meters
		refLat = Math.toRadians(refLat);
		refHeading = Math.toRadians(refHeading);
		double deltaXinM = deltaXinCM / 100;
		double deltaYinM = deltaYinCM / 100;
				
		double a = 6378137;
		double f = 0.003353;
		double f1 = Math.pow(f*(2-f), 0.5);
		double f2 = a * (1 - Math.pow(f1, 2)) / Math.pow((1 - Math.pow(f1, 2) * Math.pow(Math.sin(refLat), 2)), 3/2);
		
		double N = Math.cos(refHeading) * deltaXinM - Math.sin(refHeading) * deltaYinM;
		
		double newLat = (1 / f2) * N + refLat;
		
		return Math.toDegrees(newLat);
	}
	
	private static double convertXYToLon_J2945_1_By_Heading(double refLat, double refLon, double refHeading,
																double deltaXinCM, double deltaYinCM) {
		// Use Radians and Meters
		refLat = Math.toRadians(refLat);
		refLon = Math.toRadians(refLon);
		refHeading = Math.toRadians(refHeading);
		double deltaXinM = deltaXinCM / 100;
		double deltaYinM = deltaYinCM / 100;
		
		double a = 6378137;
		double f = 0.003353;
		double f1 = Math.pow(f*(2-f), 0.5);
		double f3 = a / Math.pow((1 - Math.pow(f1, 2) * Math.pow(Math.sin(refLat), 2)), 1/2);
		
		double E = Math.cos(refHeading) * deltaYinM + Math.sin(refHeading) * deltaXinM;
		
		double newLon = (1 / (f3 * Math.cos(refLat))) * E + refLon;
		
		return Math.toDegrees(newLon);
	}
	*/
	
	private static double dms2Decimal(double degrees, double minutes, double seconds) {
		boolean isNeg = degrees < 0;
		
		if(isNeg) 
			degrees = -degrees;
		
		double decimal = degrees + (minutes / 60) + (seconds / 3600);
		
		if(isNeg)
			decimal = -decimal;
		
		return decimal;
	}
	
	private static String decimal2Dms(double decimal)
    {
        double degrees = Math.floor(decimal);
        double minutes = Math.floor((decimal - degrees) * 60);
        double seconds = (((decimal - degrees) * 60) - minutes) * 60;
        
        if(seconds == 60) {
        	minutes++;
        	seconds = 0;
        }
        if(minutes == 60) {
        	degrees++;
        	minutes = 0;
        }
        
        return degrees + "° " + minutes + "' " + seconds + "\"";
    }
	
	public static class DistanceAndBearing {
		private double distance, initialBearing, finalBearing;
		
		public DistanceAndBearing(double distance, double initialBearing, double finalBearing) {
			this.distance = distance;
			this.initialBearing = initialBearing;
			this.finalBearing = finalBearing;
		}
		
		public double getDistance() {
			return distance;
		}
		
		public double getInitialBearing() {
			return initialBearing;
		}
		
		public double getFinalBearing() {
			return finalBearing;
		}
		
		@Override
		public String toString() {
			return "Distance: " + distance + "\n" +
				   "Initial Bearing: " + initialBearing + "\n" +
				   "Final Bearing: " + finalBearing;
		}
	}
	
	private static void controlTest() {
		// Control: http://www.ga.gov.au/bin/geodesy/run/gda_vincenty?inverse=1&lat_degrees1=-37&lat_minutes1=57&lat_seconds1=03.72030&NamePoint1=FlindersPeak&lon_degrees1=144&lon_minutes1=25&lon_seconds1=29.52440&lat_degrees2=-37&lat_minutes2=39&lat_seconds2=10.15610&NamePoint2=Buninyong&lon_degrees2=143&lon_minutes2=55&lon_seconds2=35.38390&lat_deg1=-37+deg&lat_min1=57+min&lat_sec1=3.7203+sec&lon_deg1=144+deg&lon_min1=25+min&lon_sec1=29.5244+sec&lat_deg2=-37+deg&lat_min2=39+min&lat_sec2=10.1561+sec&lon_deg2=143+deg&lon_min2=55+min&lon_sec2=35.3839+sec&Submit=Submit+Data
		
		/* Test Data
			Point 1 - Flinders Peak 	
			37°57′03.72030″S, 144°25′29.52440″E
			-37.95103341666667, 144.42486788888888
		
			Point 2 - Buninyong
			37°39′10.15610″S, 143°55′35.38390″E
			-37.65282113888889, 143.92649552777777
		
		
			Results
			s 	54,972.271 m
			α1 	306°52′05.37″
				306.8681583333333
			α2 	127°10′25.07″ (≡ 307°10′25.07″ p1→p2)
				127.17363056  (≡ 307.17363056 p1→p2)
		*/
		System.out.println("Control Test:");
		System.out.println();
		System.out.println("Expected Results:");
		System.out.println(
				"Location: 	POINT I	POINT II \n"  +
				"Name:	FlindersPeak	Buninyong \n" +
				"Latitude:	-37 ° 57 ' 3.72030 ''	-37 ° 39 ' 10.15610 '' \n" +
				"Longitude:	144 ° 25 ' 29.52440 ''	143 ° 55 ' 35.38390 '' \n" +
				"Forward Azimuth:		306 ° 52 ' 5.37 '' \n" +
				"Reverse Azimuth:		127 ° 10 ' 25.07 '' \n" +
				"Ellipsoidal Distance:		54972.271 meters");

		System.out.println();
		System.out.println("Calculated Results:");
		DistanceAndBearing control = getDistanceAndBearing(
											-37.95103341666667, 144.42486788888888,
											-37.65282113888889, 143.92649552777777);
		System.out.println(control);
		System.out.println(decimal2Dms(control.getInitialBearing()));
		System.out.println(decimal2Dms(control.getFinalBearing()));
	}
}
