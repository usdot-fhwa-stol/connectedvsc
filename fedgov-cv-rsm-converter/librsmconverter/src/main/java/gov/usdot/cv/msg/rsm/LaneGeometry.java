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
 * LaneGeometry data class from RSM ASN.1 Specification
 * <p>
 * Either references another lane by LaneID stored in referenceLane or describes
 * the geometry of the lane by a series of absolute/offset points stored in 
 * nodeSet.
 */
public class LaneGeometry {
    private Optional<Integer> referenceLane;
    private Optional<List<NodeLle>> nodeSet; // Must be at least 2 elements in size

    // TODO: Re-work into proper CHOICE style sum-type constructor
    public LaneGeometry(Optional<Integer> referenceLane, Optional<List<NodeLle>> nodeSet) {
        this.referenceLane = referenceLane;
        this.nodeSet = nodeSet;
    }

    /**
     * Get the OPTIONALLY specified reference lane the geoemtry is to be transformed from.
     * @return An optional containing the Lane ID of the reference lane if it was specified
     */
    public Optional<Integer> getReferenceLane() {
        return referenceLane;
    }

    /**
     * Get the optiona list of points describing this lanes geometry
     * @return An optional containing the list of points if they were specified
     */
    public Optional<List<NodeLle>> getNodeSet() {
        return nodeSet;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LaneGeometry)) {
            return false;
        }
        LaneGeometry laneGeometry = (LaneGeometry) o;
        return Objects.equals(referenceLane, laneGeometry.referenceLane) && Objects.equals(nodeSet, laneGeometry.nodeSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(referenceLane, nodeSet);
    }

    @Override
    public String toString() {
        return "{" +
            " referenceLane='" + getReferenceLane() + "'" +
            ", nodeSet='" + getNodeSet() + "'" +
            "}";
    }

}