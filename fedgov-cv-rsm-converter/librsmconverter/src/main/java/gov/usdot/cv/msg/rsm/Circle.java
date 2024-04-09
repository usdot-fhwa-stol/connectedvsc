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

/**
 * Circle data class from the RSM ASN.1 specification
 */
public class Circle {
    private Position3D center;
    private int radius;
    private DistanceUnits units;

    public Circle(Position3D center, int radius, DistanceUnits units) {
        this.center = center;
        this.radius = radius;
        this.units = units;
    }

    /**
     * @return The center point of the circle
     */
    public Position3D getCenter() {
        return this.center;
    }

    /**
     * @return The radius of the circle in terms of {@link DistanceUnits} returned by {@link Circle#getUnits()}
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * @return The units that describe the radius of the circle
     */
    public DistanceUnits getUnits() {
        return this.units;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Circle)) {
            return false;
        }
        Circle circle = (Circle) o;
        return Objects.equals(center, circle.center) && radius == circle.radius && Objects.equals(units, circle.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius, units);
    }

    @Override
    public String toString() {
        return "{" +
            " center='" + getCenter() + "'" +
            ", radius='" + getRadius() + "'" +
            ", units='" + getUnits() + "'" +
            "}";
    }

}