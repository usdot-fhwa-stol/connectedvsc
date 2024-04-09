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
 * ReducedSpeedZoneContainer data class from the RSM ASN.1 specification
 * <p>
 * Contains the description of the reduced speed zone described by the RSM such
 * as a workzone or traffic incident.
 */
public class ReducedSpeedZoneContainer {
    Optional<List<LaneInfo>> laneStatus;
    Optional<Boolean> peoplePresent;
    Optional<RsmSpeedLimit> speedLimit;
    Optional<Integer> roadClosureDescription;
    Optional<Integer> roadWorkDescription;
    Optional<String> flagman;
    Optional<Boolean> trucksEnteringLeaving;
    Optional<AreaType> rszRegion;

    public ReducedSpeedZoneContainer(Optional<List<LaneInfo>> laneStatus, Optional<Boolean> peoplePresent, Optional<RsmSpeedLimit> speedLimit, Optional<Integer> roadClosureDescription, Optional<Integer> roadWorkDescription, Optional<String> flagman, Optional<Boolean> trucksEnteringLeaving, Optional<AreaType> rszRegion) {
        this.laneStatus = laneStatus;
        this.peoplePresent = peoplePresent;
        this.speedLimit = speedLimit;
        this.roadClosureDescription = roadClosureDescription;
        this.roadWorkDescription = roadWorkDescription;
        this.flagman = flagman;
        this.trucksEnteringLeaving = trucksEnteringLeaving;
        this.rszRegion = rszRegion;
    }

    /**
     * @return Data about the state of the lanes, if specified
     */
    public Optional<List<LaneInfo>> getLaneStatus() {
        return this.laneStatus;
    }

    /**
     * @return True, if people are present in the reduced speed zone, false o.w., if specified
     */
    public Optional<Boolean> getPeoplePresent() {
        return this.peoplePresent;
    }

    /**
     * @return The speed limit data for the reduced speed zone, if specified
     */
    public Optional<RsmSpeedLimit> getSpeedLimit() {
        return this.speedLimit;
    }

    /**
     * @return The ITIS code describing the road closure, if specified
     */
    public Optional<Integer> getRoadClosureDescription() {
        return this.roadClosureDescription;
    }

    /**
     * @return The ITIS code describing the road work, if specified
     */
    public Optional<Integer> getRoadWorkDescription() {
        return this.roadWorkDescription;
    }

    /**
     * @return A string-valued bit string representing the J2375 PublicSafetyDirectingTrafficSubtype,
     * if specified
     */
    public Optional<String> getFlagman() {
        return this.flagman;
    }

    /**
     * @return True if trucks are entering or leaving the reduced speed zone, false o.w., if specified
     */
    public Optional<Boolean> getTrucksEnteringLeaving() {
        return this.trucksEnteringLeaving;
    }

    /**
     * @return The geometric description of the region this reduced speed zone pertains to
     */
    public Optional<AreaType> getRszRegion() {
        return this.rszRegion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ReducedSpeedZoneContainer)) {
            return false;
        }
        ReducedSpeedZoneContainer reducedSpeedZoneContainer = (ReducedSpeedZoneContainer) o;
        return Objects.equals(laneStatus, reducedSpeedZoneContainer.laneStatus) && Objects.equals(peoplePresent, reducedSpeedZoneContainer.peoplePresent) && Objects.equals(speedLimit, reducedSpeedZoneContainer.speedLimit) && Objects.equals(roadClosureDescription, reducedSpeedZoneContainer.roadClosureDescription) && Objects.equals(roadWorkDescription, reducedSpeedZoneContainer.roadWorkDescription) && Objects.equals(flagman, reducedSpeedZoneContainer.flagman) && Objects.equals(trucksEnteringLeaving, reducedSpeedZoneContainer.trucksEnteringLeaving) && Objects.equals(rszRegion, reducedSpeedZoneContainer.rszRegion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(laneStatus, peoplePresent, speedLimit, roadClosureDescription, roadWorkDescription, flagman, trucksEnteringLeaving, rszRegion);
    }

    @Override
    public String toString() {
        return "{" +
            " laneStatus='" + getLaneStatus() + "'" +
            ", peoplePresent='" + getPeoplePresent() + "'" +
            ", speedLimit='" + getSpeedLimit() + "'" +
            ", roadClosureDescription='" + getRoadClosureDescription() + "'" +
            ", roadWorkDescription='" + getRoadWorkDescription() + "'" +
            ", flagman='" + getFlagman() + "'" +
            ", trucksEnteringLeaving='" + getTrucksEnteringLeaving() + "'" +
            ", rszRegion='" + getRszRegion() + "'" +
            "}";
    }
}