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

public class LaneAttributes {
    private LaneDirection directionalUse;
    private LaneSharing sharedWith;
    private LaneTypeAttributes laneType;

    // Constructors
    public LaneAttributes() {
    }

    public LaneAttributes(
        LaneDirection directionalUse,
        LaneSharing sharedWith,
        LaneTypeAttributes laneType) {
        this.directionalUse = directionalUse;
        this.sharedWith = sharedWith;
        this.laneType = laneType;
    }

    // Getter and Setter methods for each field...
    public LaneDirection getLaneDirectionAttribute() {
        return directionalUse;
    }

    public void setLaneDirectionAttribute(LaneDirection direction) {
        this.directionalUse = direction;
    }

    public LaneSharing getLaneSharingAttribute() {
        return sharedWith;
    }

    public void setLaneSharingAttribute(LaneSharing sharedWith) {
        this.sharedWith = sharedWith;
    }

    public LaneTypeAttributes getLaneTypeAttribute() {
        return laneType;
    }

    public void setLaneTypeAttribute(LaneTypeAttributes laneType) {
        this.laneType = laneType;
    }

    @Override
    public String toString() {
        return "LaneAttributes{" +
                "directionalUse=" + directionalUse +
                ", sharedWith=" + sharedWith +
                ", laneType=" + laneType +
                '}';
    }
}
