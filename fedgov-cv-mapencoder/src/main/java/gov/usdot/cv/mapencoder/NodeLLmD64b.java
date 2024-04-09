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

public class NodeLLmD64b {
    private int longitude;
    private int latitude;

    public static final int LONGITUDE_UNAVAILABLE = 1800000001;
    public static final int LONGITUDE_MAX = 1800000000;
    public static final int LONGITUDE_MIN = -1799999999;

    public static final int LATITUDE_UNAVAILABLE = 900000001;
    public static final int LATITUDE_MAX = 900000000;
    public static final int LATITUDE_MIN = -900000000;

    // Constructors
    public NodeLLmD64b() {
    }

    public NodeLLmD64b(int longitude, int latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Getters and Setters
    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "NodeLLmD64b{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

}
