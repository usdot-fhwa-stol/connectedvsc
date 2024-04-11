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

public class LaneAttributesBarrier {
    private short laneAttributesBarrier;

    // Constants
    public static final short MEDIAN_REVOCABLE_LANE = 0;
    public static final short MEDIAN = 1;
    public static final short WHITE_LINE_HASHING = 2;
    public static final short STRIPED_LINES = 3;
    public static final short DOUBLE_STRIPED_LINES = 4;
    public static final short TRAFFIC_CONES = 5;
    public static final short CONSTRUCTION_BARRIER = 6;
    public static final short TRAFFIC_CHANNELS = 7;
    public static final short LOW_CURBS = 8;
    public static final short HIGH_CURBS = 9;

    // Constructors
    public LaneAttributesBarrier() {
    }

    public LaneAttributesBarrier(short laneAttributesBarrier) {
        this.laneAttributesBarrier = laneAttributesBarrier;
    }

    // Getter and Setter
    public short getLaneAttributesBarrier() {
        return laneAttributesBarrier;
    }

    public void setLaneAttributesBarrier(short laneAttributesBarrier) {
        this.laneAttributesBarrier = laneAttributesBarrier;
    }

    @Override
    public String toString() {
        return "LaneAttributesBarrier{" +
                "laneAttributesBarrier=" + laneAttributesBarrier +
                '}';
    }

}
