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
 * CommonContainer data class from the RSM ASN.1 specification
 * <p>
 * Represents the basic data about the event and region of the RSM
 */
public class CommonContainer {
    private EventInfo eventInfo;
    private RegionInfo regionInfo;

    public CommonContainer(EventInfo eventInfo, RegionInfo regionInfo) {
        this.eventInfo = eventInfo;
        this.regionInfo = regionInfo;
    }

    /**
     * @return The details of the event described by the RSM
     */
    public EventInfo getEventInfo() {
        return this.eventInfo;
    }

    /**
     * @return The details of the region the event is relevant to
     */
    public RegionInfo getRegionInfo() {
        return this.regionInfo;
    }

    @Override
    public String toString() {
        return "{" +
            " eventInfo='" + getEventInfo() + "'" +
            ", regionInfo='" + getRegionInfo() + "'" +
            "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CommonContainer)) {
            return false;
        }
        CommonContainer commonContainer = (CommonContainer) o;
        return Objects.equals(eventInfo, commonContainer.eventInfo) && Objects.equals(regionInfo, commonContainer.regionInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventInfo, regionInfo);
    }

}