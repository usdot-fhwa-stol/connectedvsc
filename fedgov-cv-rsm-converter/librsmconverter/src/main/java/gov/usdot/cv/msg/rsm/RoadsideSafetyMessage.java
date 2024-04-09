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
 * RoadsideSafetyMessage data object from the RSM ASN.1 specification
 * <p>
 * Currently only the {@link CommonContainer} and {@link ReducedSpeedZoneContainer}
 * containers are implemented. 
 */
public class RoadsideSafetyMessage {
    private int version;
    private CommonContainer commonContainer;
    private Optional<ReducedSpeedZoneContainer> reducedSpeedZoneContainer;

    public RoadsideSafetyMessage(int version, CommonContainer commonContainer, Optional<ReducedSpeedZoneContainer> reducedSpeedZoneContainer) {
        this.version = version;
        this.commonContainer = commonContainer;
        this.reducedSpeedZoneContainer = reducedSpeedZoneContainer;
    }

    /**
     * @return The version of the RSM specification used for this RSM
     */
    public int getVersion() {
        return this.version;
    }

    /**
     * @return General data about the event described in this RSM
     */
    public CommonContainer getCommonContainer() {
        return this.commonContainer;
    }

    /**
     * @return Data about the region of this RSM in which reduced speed is advised, if specified
     */
    public Optional<ReducedSpeedZoneContainer> getReducedSpeedZoneContainer() {
        return this.reducedSpeedZoneContainer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RoadsideSafetyMessage)) {
            return false;
        }
        RoadsideSafetyMessage roadsideSafetyMessage = (RoadsideSafetyMessage) o;
        return version == roadsideSafetyMessage.version && Objects.equals(commonContainer, roadsideSafetyMessage.commonContainer) && Objects.equals(reducedSpeedZoneContainer, roadsideSafetyMessage.reducedSpeedZoneContainer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, commonContainer, reducedSpeedZoneContainer);
    }

    @Override
    public String toString() {
        return "{" +
            " version='" + getVersion() + "'" +
            ", commonContainer='" + getCommonContainer() + "'" +
            ", reducedSpeedZoneContainer='" + getReducedSpeedZoneContainer() + "'" +
            "}";
    }


    // UNIMPLEMENTED
    // private Optional<CurveContainer> curveContainer;
    // private Optional<StaticSignageContainer> staticSignageContainer;
    // private Optional<SituationalContainer> situationalContainer;
    // private Optional<DynamicInfoContainer> dynamicInfoContainer;
    // private Optional<IncidentsContainer> incidentsContainer;
}