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

public class LaneAttributesVehicle {
    private byte laneAttributesVehicle;

    // Constants
    public static final byte IS_VEHICLE_REVOCABLE_LANE = 0;
    public static final byte IS_VEHICLE_FLYOVER_LANE = 1;
    public static final byte HOV_LANE_USE_ONLY = 2;
    public static final byte RESTRICTED_TO_BUS_USE = 3;
    public static final byte RESTRICTED_TO_TAXI_USE = 4;
    public static final byte RESTRICTED_FROM_PUBLIC_USE = 5;
    public static final byte HAS_IR_BEACON_COVERAGE = 6;
    public static final byte PERMISSION_ON_REQUEST = 7;

    // Constructors
    public LaneAttributesVehicle() {
    }

    public LaneAttributesVehicle(byte laneAttributesVehicle) {
        this.laneAttributesVehicle = laneAttributesVehicle;
    }

    // Getter and Setter
    public byte getLaneAttributesVehicle() {
        return laneAttributesVehicle;
    }

    public void setLaneAttributesVehicle(byte laneAttributesVehicle) {
        this.laneAttributesVehicle = laneAttributesVehicle;
    }

    @Override
    public String toString() {
        return "LaneAttributesVehicle{" +
                "laneAttributesVehicle=" + laneAttributesVehicle +
                '}';
    }

}
