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
 * NodeLle data class from the RSM ASN.1 specification
 * <p>
 * Contains the data about a given point in an {@link LaneGeometry} described in an
 * {@link RsmGeometry} instance.
 */
public class NodeLle {
    private NodePointLle nodePoint;
    private Optional<NodeAttributeSetLle> nodeAttributes;

    public NodeLle(NodePointLle nodePoint, Optional<NodeAttributeSetLle> nodeAttributes) {
        this.nodePoint = nodePoint;
        this.nodeAttributes = nodeAttributes;
    }

    /**
     * @return The geometric data pertaining to this node
     */
    public NodePointLle getNodePoint() {
        return this.nodePoint;
    }

    /**
     * @return The properties of the lane at this node, if it was specified in the message
     */
    public Optional<NodeAttributeSetLle> getNodeAttributes() {
        return this.nodeAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NodeLle)) {
            return false;
        }
        NodeLle nodeLle = (NodeLle) o;
        return Objects.equals(nodePoint, nodeLle.nodePoint) && Objects.equals(nodeAttributes, nodeLle.nodeAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodePoint, nodeAttributes);
    }

    @Override
    public String toString() {
        return "{" +
            " nodePoint='" + getNodePoint() + "'" +
            ", nodeAttributes='" + getNodeAttributes() + "'" +
            "}";
    }
}