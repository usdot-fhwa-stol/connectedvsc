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

public class LaneSharing {
    private short laneSharing;

    // Constants
    public static final short OVERLAPPING_LANE_DESCRIPTION_PROVIDED = 0;
    public static final short MULTIPLE_LANES_TREATED_AS_ONE_LANE = 1;
    public static final short OTHER_NON_MOTORIZED_TRAFFIC_TYPES = 2;
    public static final short INDIVIDUAL_MOTORIZED_VEHICLE_TRAFFIC = 3;
    public static final short BUS_VEHICLE_TRAFFIC = 4;
    public static final short TAXI_VEHICLE_TRAFFIC = 5;
    public static final short PEDESTRIANS_TRAFFIC = 6;
    public static final short CYCLIST_VEHICLE_TRAFFIC = 7;
    public static final short TRACKED_VEHICLE_TRAFFIC = 8;
    public static final short PEDESTRIAN_TRAFFIC = 9;

    // Constructors
    public LaneSharing() {
    }

    public LaneSharing(short laneSharing) {
        this.laneSharing = laneSharing;
    }

    // Getter and Setter
    public short getLaneSharing() {
        return laneSharing;
    }

    public void setLaneSharing(short laneSharing) {
        this.laneSharing = laneSharing;
    }

    @Override
    public String toString() {
        return "LaneSharing{" +
                "laneSharing=" + laneSharing +
                '}';
    }

}
