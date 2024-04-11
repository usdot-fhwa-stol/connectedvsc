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

public class AllowedManeuvers {
    private int allowedManeuvers;

    // Bit flags
    public static final int STRAIGHT = 1;
    public static final int LEFT_TURN = 2;
    public static final int RIGHT_TURN = 4;
    public static final int U_TURN = 8;
    public static final int LEFT_TURN_ON_RED = 16;
    public static final int RIGHT_TURN_ON_RED = 32;
    public static final int LANE_CHANGE = 64;
    public static final int NO_STOPPING_ALLOWED = 128;
    public static final int ALWAYS_YIELD = 256;
    public static final int GO_WITH_HALT = 512;
    public static final int CAUTION = 1024;

    // Constructors
    public AllowedManeuvers() {
    }

    public AllowedManeuvers(int allowedManeuvers) {
        this.allowedManeuvers = allowedManeuvers;
    }

    // Getter and Setter
    public int getAllowedManeuvers() {
        return allowedManeuvers;
    }

    public void setAllowedManeuvers(int allowedManeuvers) {
        this.allowedManeuvers = allowedManeuvers;
    }

    @Override
    public String toString() {
        return "AllowedManeuvers{" +
                "allowedManeuvers=" + allowedManeuvers +
                '}';
    }

}
