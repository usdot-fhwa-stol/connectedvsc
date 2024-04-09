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
 * RegionInfo data object from the RSM ASN.1 specification
 * <p>
 * Describes the general region info for the {@link CommonContainer}
 */
public class RegionInfo {
    private ApplicableHeading applicableHeading;
    private Position3D referencePoint;
    private Optional<ReferencePointType> referencePointType;
    private Optional<String> descriptiveName;
    private Optional<RsmSpeedLimit> speedLimit;
    private Optional<Integer> eventLength;
    private Optional<AreaType> areaType;

    public RegionInfo(ApplicableHeading applicableHeading, Position3D referencePoint, Optional<ReferencePointType> referencePointType, Optional<String> descriptiveName, Optional<RsmSpeedLimit> speedLimit, Optional<Integer> eventLength, Optional<AreaType> areaType) {
        this.applicableHeading = applicableHeading;
        this.referencePoint = referencePoint;
        this.referencePointType = referencePointType;
        this.descriptiveName = descriptiveName;
        this.speedLimit = speedLimit;
        this.eventLength = eventLength;
        this.areaType = areaType;
    }

    /**
     * @return The heading of a traveling vehicle for which this RSM is relevant
     */
    public ApplicableHeading getApplicableHeading() {
        return this.applicableHeading;
    }

    /**
     * @return The reference point for this RSM
     */
    public Position3D getReferencePoint() {
        return this.referencePoint;
    }

    /**
     * @return The type of the reference point, if specified
     */
    public Optional<ReferencePointType> getReferencePointType() {
        return this.referencePointType;
    }

    /**
     * @return A human readable string describing the region this RSM pertains to, if specified
     */
    public Optional<String> getDescriptiveName() {
        return this.descriptiveName;
    }

    /**
     * @return The speed limit data for this RSM region, if specified
     */
    public Optional<RsmSpeedLimit> getSpeedLimit() {
        return this.speedLimit;
    }

    /**
     * @return The length of the event in meters, if specified
     */
    public Optional<Integer> getEventLength() {
        return this.eventLength;
    }

    /**
     * @return The geometric description of the RSM region, if specified
     */
    public Optional<AreaType> getAreaType() {
        return this.areaType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RegionInfo)) {
            return false;
        }
        RegionInfo regionInfo = (RegionInfo) o;
        return Objects.equals(applicableHeading, regionInfo.applicableHeading) && Objects.equals(referencePoint, regionInfo.referencePoint) && Objects.equals(referencePointType, regionInfo.referencePointType) && Objects.equals(descriptiveName, regionInfo.descriptiveName) && Objects.equals(speedLimit, regionInfo.speedLimit) && Objects.equals(eventLength, regionInfo.eventLength) && Objects.equals(areaType, regionInfo.areaType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicableHeading, referencePoint, referencePointType, descriptiveName, speedLimit, eventLength, areaType);
    }

    @Override
    public String toString() {
        return "{" +
            " applicableHeading='" + getApplicableHeading() + "'" +
            ", referencePoint='" + getReferencePoint() + "'" +
            ", referencePointType='" + getReferencePointType() + "'" +
            ", descriptiveName='" + getDescriptiveName() + "'" +
            ", speedLimit='" + getSpeedLimit() + "'" +
            ", eventLength='" + getEventLength() + "'" +
            ", areaType='" + getAreaType() + "'" +
            "}";
    }
}