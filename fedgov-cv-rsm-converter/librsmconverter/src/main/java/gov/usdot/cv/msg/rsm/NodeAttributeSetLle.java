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

import java.util.Objects;
import java.util.Optional;

/**
 * NodeAttributeSetLle data object from the RSM ASN.1 specification
 * <p>
 * Describes the properties of a {@link NodePointLle} in an {@link RsmGeometry} instance
 */
public class NodeAttributeSetLle {
    private Optional<RsmSpeedLimit> speedLimit;
    private Optional<Integer> width;
    private Optional<Boolean> taperLeft;
    private Optional<Boolean> taperRight;
    private Optional<Boolean> laneClosed;
    private Optional<Boolean> peoplePresent;

    public NodeAttributeSetLle(Optional<RsmSpeedLimit> speedLimit, Optional<Integer> width, Optional<Boolean> taperLeft, Optional<Boolean> taperRight, Optional<Boolean> laneClosed, Optional<Boolean> peoplePresent) {
        this.speedLimit = speedLimit;
        this.width = width;
        this.taperLeft = taperLeft;
        this.taperRight = taperRight;
        this.laneClosed = laneClosed;
        this.peoplePresent = peoplePresent;
    }

    /**
     * @return Object containing information on the speed limit at this node point, if it exists.
     */
    public Optional<RsmSpeedLimit> getSpeedLimit() {
        return this.speedLimit;
    }

    /**
     * @return The width of the node point, if it is specified.
     */
    public Optional<Integer> getWidth() {
        return this.width;
    }

    /**
     * @return True, if the lane tapers to the left at this node point, if it is specified
     */
    public Optional<Boolean> getTaperLeft() {
        return this.taperLeft;
    }

    /**
     * @return True, if the lane tapers to the left at this node point, if it is specified
     */
    public Optional<Boolean> getTaperRight() {
        return this.taperRight;
    }

    /**
     * @return True, if the lane closes at this node point, if it is specified
     */
    public Optional<Boolean> getLaneClosed() {
        return this.laneClosed;
    }

    /**
     * @return True, if people are present at this node point, if it is specified
     */
    public Optional<Boolean> getPeoplePresent() {
        return this.peoplePresent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NodeAttributeSetLle)) {
            return false;
        }
        NodeAttributeSetLle nodeAttributeSetLle = (NodeAttributeSetLle) o;
        return Objects.equals(speedLimit, nodeAttributeSetLle.speedLimit) && Objects.equals(width, nodeAttributeSetLle.width) && Objects.equals(taperLeft, nodeAttributeSetLle.taperLeft) && Objects.equals(taperRight, nodeAttributeSetLle.taperRight) && Objects.equals(laneClosed, nodeAttributeSetLle.laneClosed) && Objects.equals(peoplePresent, nodeAttributeSetLle.peoplePresent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speedLimit, width, taperLeft, taperRight, laneClosed, peoplePresent);
    }

    @Override
    public String toString() {
        return "{" +
            " speedLimit='" + getSpeedLimit() + "'" +
            ", width='" + getWidth() + "'" +
            ", taperLeft='" + getTaperLeft() + "'" +
            ", taperRight='" + getTaperRight() + "'" +
            ", laneClosed='" + getLaneClosed() + "'" +
            ", peoplePresent='" + getPeoplePresent() + "'" +
            "}";
    }
}