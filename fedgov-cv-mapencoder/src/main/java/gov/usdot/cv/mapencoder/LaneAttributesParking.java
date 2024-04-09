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

public class LaneAttributesParking {
    private short laneAttributesParking;

    // Constants
    public static final short PARKING_REVOCABLE_LANE = 0;
    public static final short PARALLEL_PARKING_IN_USE = 1;
    public static final short HEAD_IN_PARKING_IN_USE = 2;
    public static final short DO_NOT_PARK_ZONE = 3;
    public static final short PARKING_FOR_BUS_USE = 4;
    public static final short PARKING_FOR_TAXI_USE = 5;
    public static final short NO_PUBLIC_PARKING_USE = 6;

    // Constructors
    public LaneAttributesParking() {
    }

    public LaneAttributesParking(short laneAttributesParking) {
        this.laneAttributesParking = laneAttributesParking;
    }

    // Getter and Setter
    public short getLaneAttributesParking() {
        return laneAttributesParking;
    }

    public void setLaneAttributesParking(short laneAttributesParking) {
        this.laneAttributesParking = laneAttributesParking;
    }

    @Override
    public String toString() {
        return "LaneAttributesParking{" +
                "laneAttributesParking=" + laneAttributesParking +
                '}';
    }

}
