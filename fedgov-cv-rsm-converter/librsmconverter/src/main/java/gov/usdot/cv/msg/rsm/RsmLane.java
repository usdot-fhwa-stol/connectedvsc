/*
 * Copyright (C) 2018-2019 LEIDOS.
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

package gov.usdot.cv.msg.rsm;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * RSMLane data class from the RSM ASN.1 Specification
 * <p>
 * Stores the data representation of a physical and logical lane the
 * RSM message pertains to.
 */
public class RsmLane {
    private int laneId;
    private Optional<Integer> lanePosition;
    private Optional<String> laneName;
    private Optional<Integer> laneWidth;
    private Optional<LaneGeometry> laneGeometry;
    private Optional<List<Integer>> connectsTo;

    public RsmLane(int laneId, Optional<Integer> lanePosition, Optional<String> laneName, 
    Optional<Integer> laneWidth, Optional<LaneGeometry> laneGeometry, Optional<List<Integer>> connectsTo) {
        this.laneId = laneId;
        this.lanePosition = lanePosition;
        this.laneName = laneName;
        this.laneWidth = laneWidth;
        this.laneGeometry = laneGeometry;
        this.connectsTo = connectsTo;
    }

    /**
     * Gets the unique lane ID for this lane.
     * @return The ID for the lane
     */
    public int getLaneId() {
        return laneId;
    }

    /**
     * Get the lane position on the roadway where 1 is the leftmost lane
     * @return An optional containing the lane position if it was specified
     */
    public Optional<Integer> getLanePosition() {
        return lanePosition;
    }

    /**
     * Get the human readable lane description
     * @return An optional containing the lane name if it was specified
     */
    public Optional<String> getLaneName() {
        return laneName;
    }

    /**
     * Get the width of the lane in centimeters
     * @return An optional containing the lane width if it was specified
     */
    public Optional<Integer> getLaneWidth() {
        return laneWidth;
    }

    /**
     * Get the geometric description of the lane
     * @return An optional containing the lane geometry if it was specified
     */
    public Optional<LaneGeometry> getLaneGeometry() {
        return laneGeometry;
    }

    /**
     * Get the list of lanes (as LaneID elements) that this lane connects to
     * @return An optional containing the connects to data if it was specified
     */
    public Optional<List<Integer>> getConnectsTo() {
        return connectsTo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RsmLane)) {
            return false;
        }
        RsmLane rsmLane = (RsmLane) o;
        return laneId == rsmLane.laneId && Objects.equals(lanePosition, rsmLane.lanePosition) && Objects.equals(laneName, rsmLane.laneName) && Objects.equals(laneWidth, rsmLane.laneWidth) && Objects.equals(laneGeometry, rsmLane.laneGeometry) && Objects.equals(connectsTo, rsmLane.connectsTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(laneId, lanePosition, laneName, laneWidth, laneGeometry, connectsTo);
    }

    @Override
    public String toString() {
        return "{" +
            " laneId='" + getLaneId() + "'" +
            ", lanePosition='" + getLanePosition() + "'" +
            ", laneName='" + getLaneName() + "'" +
            ", laneWidth='" + getLaneWidth() + "'" +
            ", laneGeometry='" + getLaneGeometry() + "'" +
            ", connectsTo='" + getConnectsTo() + "'" +
            "}";
    }

}