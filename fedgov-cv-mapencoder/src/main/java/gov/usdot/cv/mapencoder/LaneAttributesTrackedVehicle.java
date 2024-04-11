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

public class LaneAttributesTrackedVehicle {
    private short laneAttributesTrackedVehicle;

    // Constants
    public static final short SPEC_REVOCABLE_LANE = 0;
    public static final short SPEC_COMMUTER_RAILROAD_TRACK = 1;
    public static final short SPEC_LIGHT_RAILROAD_TRACK = 2;
    public static final short SPEC_HEAVY_RAILROAD_TRACK = 3;
    public static final short SPEC_OTHER_RAIL_TYPE = 4;

    // Constructors
    public LaneAttributesTrackedVehicle() {
    }

    public LaneAttributesTrackedVehicle(short laneAttributesTrackedVehicle) {
        this.laneAttributesTrackedVehicle = laneAttributesTrackedVehicle;
    }

    // Getter and Setter
    public short getLaneAttributesTrackedVehicle() {
        return laneAttributesTrackedVehicle;
    }

    public void setLaneAttributesTrackedVehicle(short laneAttributesTrackedVehicle) {
        this.laneAttributesTrackedVehicle = laneAttributesTrackedVehicle;
    }

    @Override
    public String toString() {
        return "LaneAttributesTrackedVehicle{" +
                "laneAttributesTrackedVehicle=" + laneAttributesTrackedVehicle +
                '}';
    }

}
