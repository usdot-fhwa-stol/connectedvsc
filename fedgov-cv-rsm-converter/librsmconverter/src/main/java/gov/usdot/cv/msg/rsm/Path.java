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

/**
 * Path object from the RSM ASN.1 specifiction
 * <p>
 * Describes a general path as a series of {@link Position3D} points
 */
public class Path {
    private int pathWidth;
    private List<Position3D> pathPoints;

    public Path(int pathWidth, List<Position3D> pathPoints) {
        this.pathWidth = pathWidth;
        this.pathPoints = pathPoints;
    }

    /**
     * @return The width of the path
     */
    public int getPathWidth() {
        return this.pathWidth;
    }

    /**
     * @return The ordered list of {@link Position3D} points describing the path
     */
    public List<Position3D> getPathPoints() {
        return this.pathPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Path)) {
            return false;
        }
        Path path = (Path) o;
        return pathWidth == path.pathWidth && Objects.equals(pathPoints, path.pathPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathWidth, pathPoints);
    }

    @Override
    public String toString() {
        return "{" +
            " pathWidth='" + getPathWidth() + "'" +
            ", pathPoints='" + getPathPoints() + "'" +
            "}";
    }
    
}