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
 * BroadRegion data class from the RSM ASN.1 specification
 * <p>
 * Contains either a polygonal bounding box for the region or a circular
 * description of the region. One of the fields must be present.
 */
public class BroadRegion {
    private Optional<List<NodePointLle>> polygon;
    private Optional<Circle> circle;

    public BroadRegion(List<NodePointLle> polygon) {
        this.polygon = Optional.of(polygon);
        this.circle = Optional.empty();
    }

    public BroadRegion(Circle circle) {
        this.polygon = Optional.empty();
        this.circle = Optional.of(circle);
    }

    /**
     * @return The polygon representation of the relevant region if that's how
     *         it was described in the RSM. The polygon is represented as a
     *         series of {@link NodePointLle} vertices.
     */
    public Optional<List<NodePointLle>> getPolygon() {
        return this.polygon;
    }

    /**
     * @return The {@link Circle} representation of the relevant region if
     *         thats how it was described in the RSM
     */
    public Optional<Circle> getCircle() {
        return this.circle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BroadRegion)) {
            return false;
        }
        BroadRegion broadRegion = (BroadRegion) o;
        return Objects.equals(polygon, broadRegion.polygon) && Objects.equals(circle, broadRegion.circle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(polygon, circle);
    }

    @Override
    public String toString() {
        return "{" +
            " polygon='" + getPolygon() + "'" +
            ", circle='" + getCircle() + "'" +
            "}";
    }
}