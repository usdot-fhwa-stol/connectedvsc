/*
 * Copyright (C) 2023 LEIDOS.
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
 
package gov.usdot.cv.mapencoder;

public class Position3D {
    private double latitude;
    private static final double LATITUDE_UNAVAILABLE = 90.0000001;
    private static final double LATITUDE_MAX = 90.0;
    private static final double LATITUDE_MIN = -90.0;

    private double longitude;
    private static final double LONGITUDE_UNAVAILABLE = 180.0000001;
    private static final double LONGITUDE_MAX = 180.0;
    private static final double LONGITUDE_MIN = -179.9999999;

    private float elevation;
    private boolean elevationExists;
    private static final float ELEVATION_UNAVAILABLE = -409.6f;
    private static final float ELEVATION_MAX = 6143.9f;
    private static final float ELEVATION_MIN = -409.5f;

    // Constructors
    public Position3D() {
    }

    public Position3D(double latitude, double longitude, float elevation, boolean elevationExists) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.elevationExists = elevationExists;
    }

    // Getters and Setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getElevation() {
        return elevation;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    public boolean isElevationExists() {
        return elevationExists;
    }

    public void setElevationExists(boolean elevationExists) {
        this.elevationExists = elevationExists;
    }

    // Constants
    public static double getLatitudeUnavailable() {
        return LATITUDE_UNAVAILABLE;
    }

    public static double getLatitudeMax() {
        return LATITUDE_MAX;
    }

    public static double getLatitudeMin() {
        return LATITUDE_MIN;
    }

    public static double getLongitudeUnavailable() {
        return LONGITUDE_UNAVAILABLE;
    }

    public static double getLongitudeMax() {
        return LONGITUDE_MAX;
    }

    public static double getLongitudeMin() {
        return LONGITUDE_MIN;
    }

    public static float getElevationUnavailable() {
        return ELEVATION_UNAVAILABLE;
    }

    public static float getElevationMax() {
        return ELEVATION_MAX;
    }

    public static float getElevationMin() {
        return ELEVATION_MIN;
    }

    @Override
    public String toString() {
        return "Position3D{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                ", elevationExists=" + elevationExists +
                '}';
    }

}
