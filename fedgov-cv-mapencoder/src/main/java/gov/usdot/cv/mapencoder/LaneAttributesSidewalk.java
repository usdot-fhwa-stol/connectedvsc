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

public class LaneAttributesSidewalk {
    private short laneAttributesSidewalk;

    // Constants
    public static final short SIDEWALK_REVOCABLE_LANE = 0;
    public static final short BICYCLE_USE_ALLOWED = 1;
    public static final short IS_SIDEWALK_FLYOVER_LANE = 2;
    public static final short WALK_BIKES = 3;

    // Constructors
    public LaneAttributesSidewalk() {
    }

    public LaneAttributesSidewalk(short laneAttributesSidewalk) {
        this.laneAttributesSidewalk = laneAttributesSidewalk;
    }

    // Getter and Setter
    public short getLaneAttributesSidewalk() {
        return laneAttributesSidewalk;
    }

    public void setLaneAttributesSidewalk(short laneAttributesSidewalk) {
        this.laneAttributesSidewalk = laneAttributesSidewalk;
    }

    @Override
    public String toString() {
        return "LaneAttributesSidewalk{" +
                "laneAttributesSidewalk=" + laneAttributesSidewalk +
                '}';
    }

}
