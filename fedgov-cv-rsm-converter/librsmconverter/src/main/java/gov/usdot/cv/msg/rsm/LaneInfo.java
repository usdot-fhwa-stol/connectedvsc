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
 * LaneInfo data class from the RSM ASN.1 specification
 * <p>
 * Describes the properties of the lane described in the RSM
 */
public class LaneInfo {
    private int lanePosition;
    private boolean laneClosed;
    private int laneCloseOffset;

    public LaneInfo(int lanePosition, boolean laneClosed, int laneCloseOffset) {
        this.lanePosition = lanePosition;
        this.laneClosed = laneClosed;
        this.laneCloseOffset = laneCloseOffset;
    }

    /**
     * @return The position of the lane on the roadway
     */
    public int getLanePosition() {
        return this.lanePosition;
    }

    /**
     * @return True if the lane is close, False o.w.
     */
    public boolean getlaneClosed() {
        return this.laneClosed;
    }

    /**
     * @return How far into the lane it closes
     */
    public int getlaneCloseOffset() {
        return this.laneCloseOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LaneInfo)) {
            return false;
        }
        LaneInfo laneInfo = (LaneInfo) o;
        return lanePosition == laneInfo.lanePosition && laneClosed == laneInfo.laneClosed && laneCloseOffset == laneInfo.laneCloseOffset;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lanePosition, laneClosed, laneCloseOffset);
    }

    @Override
    public String toString() {
        return "{" +
            " lanePosition='" + getLanePosition() + "'" +
            ", laneClosed='" + getlaneClosed() + "'" +
            ", laneCloseOffset='" + getlaneCloseOffset() + "'" +
            "}";
    }
}