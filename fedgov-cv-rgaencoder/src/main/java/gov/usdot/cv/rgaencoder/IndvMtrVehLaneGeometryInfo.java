/*
* Copyright (C) 2025 LEIDOS.
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

package gov.usdot.cv.rgaencoder;

public class IndvMtrVehLaneGeometryInfo {
    private int laneID; // Represents LaneID
    private LaneConstructorType laneConstructorType;

    public IndvMtrVehLaneGeometryInfo() {
        this.laneID = 0;
        this.laneConstructorType = null;
    }

    public IndvMtrVehLaneGeometryInfo(int laneID, LaneConstructorType laneConstructorType) {
        this.laneID = laneID;
        this.laneConstructorType = laneConstructorType;
    }

    public int getLaneID() {
        return laneID;
    }

    public void setLaneID(int laneID) {
        this.laneID = laneID;
    }

    public LaneConstructorType getLaneConstructorType() {
        return laneConstructorType;
    }

    public void setLaneConstructorType(LaneConstructorType laneConstructorType) {
        this.laneConstructorType = laneConstructorType;
    }

    @Override
    public String toString() {
        return "IndvMtrVehLaneGeometryInfo [laneID=" + laneID + ", laneConstructorType=" + laneConstructorType + "]";
    }
}
