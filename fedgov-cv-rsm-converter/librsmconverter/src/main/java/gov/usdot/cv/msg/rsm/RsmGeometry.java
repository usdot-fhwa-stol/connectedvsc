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
 * RsmGeometry data class from the RSM ASN.1 Specification
 * <p>
 * Stores the geometric scaling of the lane geometries and the list of lanes
 * described by this message
 */
public class RsmGeometry {
    private Optional<Integer> scale;
    private List<RsmLane> rsmLanes;

    public RsmGeometry(Optional<Integer> scale, List<RsmLane> rsmLanes) {
        this.scale = scale;
        this.rsmLanes = rsmLanes;
    }

    /**
     * @return The lanes described by this RsmGeometry
     */
    public List<RsmLane> getRsmLanes() {
        return rsmLanes;
    }

    /**
     * @return An optional containing the multiplicitave scaling factor to be 
     * applied to the lane geometry if specified
     */
    public Optional<Integer> getScale() {
        return scale;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RsmGeometry)) {
            return false;
        }
        RsmGeometry rsmGeometry = (RsmGeometry) o;
        return Objects.equals(scale, rsmGeometry.scale) && Objects.equals(rsmLanes, rsmGeometry.rsmLanes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scale, rsmLanes);
    }

    @Override
    public String toString() {
        return "{" +
            " scale='" + getScale() + "'" +
            ", rsmLanes='" + getRsmLanes() + "'" +
            "}";
    }

}