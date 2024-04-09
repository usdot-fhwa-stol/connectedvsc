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

public class LaneAttributesCrosswalk {
    private short laneAttributesCrosswalk;

    // Constants
    public static final short CROSSWALK_REVOCABLE_LANE = 0;
    public static final short BICYCLE_USE_ALLOWED = 1;
    public static final short IS_XWALK_FLYOVER_LANE = 2;
    public static final short FIXED_CYCLE_TIME = 3;
    public static final short BI_DIRECTIONAL_CYCLE_TIMES = 4;
    public static final short HAS_PUSH_TO_WALK_BUTTON = 5;
    public static final short AUDIO_SUPPORT = 6;
    public static final short RF_SIGNAL_REQUEST_PRESENT = 7;
    public static final short UNSIGNALIZED_SEGMENTS_PRESENT = 8;

    // Constructors
    public LaneAttributesCrosswalk() {
    }

    public LaneAttributesCrosswalk(short laneAttributesCrosswalk) {
        this.laneAttributesCrosswalk = laneAttributesCrosswalk;
    }

    // Getter and Setter
    public short getLaneAttributesCrosswalk() {
        return laneAttributesCrosswalk;
    }

    public void setLaneAttributesCrosswalk(short laneAttributesCrosswalk) {
        this.laneAttributesCrosswalk = laneAttributesCrosswalk;
    }

    @Override
    public String toString() {
        return "LaneAttributesCrosswalk{" +
                "laneAttributesCrosswalk=" + laneAttributesCrosswalk +
                '}';
    }

}
