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
 * DSRC.Position3D data class from RSM ASN.1 Specification
 * <p>
 * Stores the position of the node as a set of absolute latitude, longitude, and
 * elevation coordinates.
 */
public class Position3D {
    private int lat;
    private int lon;
    private int elev;

    public Position3D(int lat, int lon, int elev) {
        this.lat = lat;
        this.lon = lon;
        this.elev = elev;
    }

    /**
     * @return The absolute latitude of the node
     */
    public int getLat() {
        return lat;
    }

    /**
     * @return The absolute elevation of the node
     */
    public int getElev() {
        return elev;
    }

    /**
     * @return The absolute longitude of the node
     */
    public int getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Position3D)) {
            return false;
        }
        Position3D position3D = (Position3D) o;
        return lat == position3D.lat && lon == position3D.lon && elev == position3D.elev;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon, elev);
    }

    @Override
    public String toString() {
        return "{" +
            " lat='" + getLat() + "'" +
            ", lon='" + getLon() + "'" +
            ", elev='" + getElev() + "'" +
            "}";
    }
}