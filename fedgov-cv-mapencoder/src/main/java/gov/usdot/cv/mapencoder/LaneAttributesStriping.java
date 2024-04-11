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

public class LaneAttributesStriping {
    private short laneAttributesStriping;

    // Constants
    public static final short STRIPE_TO_CONNECTING_LANES_REVOCABLE_LANE = 0;
    public static final short STRIPE_DRAW_ON_LEFT = 1;
    public static final short STRIPE_DRAW_ON_RIGHT = 2;
    public static final short STRIPE_TO_CONNECTING_LANES_LEFT = 3;
    public static final short STRIPE_TO_CONNECTING_LANES_RIGHT = 4;
    public static final short STRIPE_TO_CONNECTING_LANES_AHEAD = 5;

    // Constructors
    public LaneAttributesStriping() {
    }

    public LaneAttributesStriping(short laneAttributesStriping) {
        this.laneAttributesStriping = laneAttributesStriping;
    }

    // Getter and Setter
    public short getLaneAttributesStriping() {
        return laneAttributesStriping;
    }

    public void setLaneAttributesStriping(short laneAttributesStriping) {
        this.laneAttributesStriping = laneAttributesStriping;
    }

    @Override
    public String toString() {
        return "LaneAttributesStriping{" +
                "laneAttributesStriping=" + laneAttributesStriping +
                '}';
    }

}
