/*
* Copyright (C) 2025 LEIDOS.
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

public class WayType {
    private long wayType;

    public enum WayTypeEnum {
        WayType_motorVehicleLane((long) 0),
        WayType_bicycleLane((long) 1),
        WayType_crosswalkLane((long) 2);

        private long value;

        WayTypeEnum(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }

    // Constructors
    public WayType() {
    }

    public WayType(long wayType) {
        this.wayType = wayType;
    }

    // Getters and Setters
    public long getWayType() {
        return wayType;
    }

    public void setWayType(long wayType) {
        this.wayType = wayType;
    }

    @Override
    public String toString() {
        return "WayType{" +
                "wayType=" + wayType +
                '}';
    }
    
}
