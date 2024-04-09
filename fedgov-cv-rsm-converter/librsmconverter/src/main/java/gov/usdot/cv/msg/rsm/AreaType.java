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
 * AreaType data class from the RSM ASN.1 specification
 * <p>
 * Stores one of three different types of geometric representation which can be
 * used to describe the area of the event captured in the RSM. One of the three
 * fields must be present.
 */
public class AreaType {
    private Optional<BroadRegion> broadRegion;
    private Optional<RsmGeometry> roadwayGeometry;
    private Optional<List<Path>> paths;

    public AreaType(BroadRegion broadRegion) {
        this.broadRegion = Optional.of(broadRegion);
        this.roadwayGeometry = Optional.empty();
        this.paths = Optional.empty();
    }

    public AreaType(RsmGeometry roadwayGeometry) {
        this.broadRegion = Optional.empty();
        this.roadwayGeometry = Optional.of(roadwayGeometry);
        this.paths = Optional.empty();
    }

    public AreaType(List<Path> paths) {
        this.broadRegion = Optional.empty();
        this.roadwayGeometry = Optional.empty();
        this.paths = Optional.of(paths);
    }

    /** 
     * @return The {@link BroadRegion} representation of the region if that's 
     *         how it was described in the RSM 
     */
    public Optional<BroadRegion> getBroadRegion() {
        return this.broadRegion;
    }

    /**
     * @return The {@link RsmGeometry} representation of the region if that's
     *         how it was described in the RSM
     */
    public Optional<RsmGeometry> getRoadwayGeometry() {
        return this.roadwayGeometry;
    }

    /**
     * @return The {@link Path} representation of the region if that's how it
     *         was described in the RSM
     */
    public Optional<List<Path>> getPaths() {
        return this.paths;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AreaType)) {
            return false;
        }
        AreaType areaType = (AreaType) o;
        return Objects.equals(broadRegion, areaType.broadRegion) && Objects.equals(roadwayGeometry, areaType.roadwayGeometry) && Objects.equals(paths, areaType.paths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(broadRegion, roadwayGeometry, paths);
    }

    @Override
    public String toString() {
        return "{" +
            " broadRegion='" + getBroadRegion() + "'" +
            ", roadwayGeometry='" + getRoadwayGeometry() + "'" +
            ", paths='" + getPaths() + "'" +
            "}";
    }


}