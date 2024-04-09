/*
 * Copyright (C) 2024 LEIDOS.
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

public class ConnectingLane {
    private int laneId;
    private AllowedManeuvers maneuver;
    private boolean maneuverExists;
   
    // Constructors
    public ConnectingLane() {
    }

    public ConnectingLane(int laneId, AllowedManeuvers maneuver, boolean maneuverExists) {
        this.laneId = laneId;
        this.maneuver = maneuver;
        this.maneuverExists = maneuverExists;
    }

    // Getter and Setter methods for each field...
    public int getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public AllowedManeuvers getManeuver() {
        return maneuver;
    }

    public void setManeuver(AllowedManeuvers maneuver) {
        this.maneuver = maneuver;
    }

    public boolean getManeuverExists() {
        return maneuverExists;
    }

    public void setManeuverExists(boolean maneuverExists) {
        this.maneuverExists = maneuverExists;
    }

    @Override
    public String toString() {
        return "ConnectingLane [laneId=" + laneId + ", maneuver=" + maneuver + ", maneuverExists=" + maneuverExists
                + "]";
    }
}
