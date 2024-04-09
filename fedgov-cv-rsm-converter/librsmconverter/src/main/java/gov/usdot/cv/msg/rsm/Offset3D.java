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
 * Offset3D data class from RSM ASN.1 Specification
 * <p>
 * Stores the offsets from the previous node in the nodeSet as a group of
 * offsets in latitude, longitude, and elevation
 */
public class Offset3D {
    private int latOffset;
    private int lonOffset;
    private int elevOffset;

    public Offset3D(int latOffset, int lonOffset, int elevOffset) {
        this.latOffset = latOffset;
        this.lonOffset = lonOffset;
        this.elevOffset = elevOffset;
    }

    /**
     * @return The delta from the previous node in latitude
     */
    public int getLatOffset() {
        return latOffset;
    }

    /**
     * @return The delta from the previous node in elevation
     */
    public int getElevOffset() {
        return elevOffset;
    }

    /**
     * @return The delta from the previous node in longitude
     */
    public int getLonOffset() {
        return lonOffset;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Offset3D)) {
            return false;
        }
        Offset3D offset3D = (Offset3D) o;
        return latOffset == offset3D.latOffset && lonOffset == offset3D.lonOffset && elevOffset == offset3D.elevOffset;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latOffset, lonOffset, elevOffset);
    }

    @Override
    public String toString() {
        return "{" +
            " latOffset='" + getLatOffset() + "'" +
            ", lonOffset='" + getLonOffset() + "'" +
            ", elevOffset='" + getElevOffset() + "'" +
            "}";
    }

}