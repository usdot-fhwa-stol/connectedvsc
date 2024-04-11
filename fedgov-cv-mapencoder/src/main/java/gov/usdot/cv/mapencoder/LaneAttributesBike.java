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

public class LaneAttributesBike {
    private short laneAttributesBike;

    // Constants
    public static final short BIKE_REVOCABLE_LANE = 0;
    public static final short PEDESTRIAN_USE_ALLOWED = 1;
    public static final short IS_BIKE_FLYOVER_LANE = 2;
    public static final short FIXED_CYCLE_TIME = 3;

    // Constructors
    public LaneAttributesBike() {
    }

    public LaneAttributesBike(short laneAttributesBike) {
        this.laneAttributesBike = laneAttributesBike;
    }

    // Getter and Setter
    public short getLaneAttributesBike() {
        return laneAttributesBike;
    }

    public void setLaneAttributesBike(short laneAttributesBike) {
        this.laneAttributesBike = laneAttributesBike;
    }

    @Override
    public String toString() {
        return "LaneAttributesBike{" +
                "laneAttributesBike=" + laneAttributesBike +
                '}';
    }

}
