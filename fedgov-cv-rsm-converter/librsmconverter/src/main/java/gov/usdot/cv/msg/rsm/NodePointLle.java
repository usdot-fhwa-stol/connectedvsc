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
 * NodePointLLE data class from RSM ASN.1 specification
 * <p>
 * Stores either the offset based position representation of this node (to be
 * calculated from the previous point's location) or the absolute position of this 
 * node as an exclusive choice. One of the values must be present.
 */
public class NodePointLle {
    private Offset3D offset;
    private Position3D position;

    public NodePointLle(Position3D position) {
        this.position = position;
    }

    public NodePointLle(Offset3D offset) {
        this.offset = offset;
    }

    /**
     * Get the absolute position value of this node if it was specified
     * @return An optional containing the absolute position value of this node if it was specified
     */
    public Optional<Position3D> getPosition() {
        return Optional.ofNullable(position);
    }

    /**
     * Get the offset position value of this node if it was specified
     * @return An optional containing the offset position value of this node if it was specified
     */
    public Optional<Offset3D> getOffset() {
        return Optional.ofNullable(offset);
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NodePointLle)) {
            return false;
        }
        NodePointLle nodePointLle = (NodePointLle) o;
        return Objects.equals(offset, nodePointLle.offset) && Objects.equals(position, nodePointLle.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, position);
    }

    @Override
    public String toString() {
        return "{" +
            " offset='" + getOffset() + "'" +
            ", position='" + getPosition() + "'" +
            "}";
    }

}