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

public class IntersectionReferenceID {
    private int region;
    private static final int REGION_UNAVAILABLE = 0;
    private boolean regionExists;
    private int id;

    // Constructors
    public IntersectionReferenceID() {
    }

    public IntersectionReferenceID(int region, boolean regionExists, int id) {
        this.region = region;
        this.regionExists = regionExists;
        this.id = id;
    }

    // Getters and Setters
    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public boolean isRegionExists() {
        return regionExists;
    }

    public void setRegionExists(boolean regionExists) {
        this.regionExists = regionExists;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Constants
    public static int getRegionUnavailable() {
        return REGION_UNAVAILABLE;
    }

    @Override
    public String toString() {
        return "IntersectionReferenceID{" +
                "region=" + region +
                ", regionExists=" + regionExists +
                ", id=" + id +
                '}';
    }
}
