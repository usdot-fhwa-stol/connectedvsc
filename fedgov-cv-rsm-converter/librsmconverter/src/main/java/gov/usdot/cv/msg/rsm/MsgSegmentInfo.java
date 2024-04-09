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
 * MsgSegmentInfo data class from the RSM ASN.1 specification
 * <p>
 * Describes how an RSM message is segmented and which segment the current
 * message describes
 */
public class MsgSegmentInfo {
    private int totalMsgSegments;
    private int thisSegmentNum;

    public MsgSegmentInfo(int totalMsgSegments, int thisSegmentNum) {
        this.totalMsgSegments = totalMsgSegments;
        this.thisSegmentNum = thisSegmentNum;
    }

    /**
     * @return The total number of message segments used to transmit this RSM
     */
    public int getTotalMsgSegments() {
        return this.totalMsgSegments;
    }

    /**
     * @return Which RSM segment out of the total this object describes
     */
    public int getThisSegmentNum() {
        return this.thisSegmentNum;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MsgSegmentInfo)) {
            return false;
        }
        MsgSegmentInfo msgSegmentInfo = (MsgSegmentInfo) o;
        return totalMsgSegments == msgSegmentInfo.totalMsgSegments && thisSegmentNum == msgSegmentInfo.thisSegmentNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalMsgSegments, thisSegmentNum);
    }

    @Override
    public String toString() {
        return "{" +
            " totalMsgSegments='" + getTotalMsgSegments() + "'" +
            ", thisSegmentNum='" + getThisSegmentNum() + "'" +
            "}";
    }
}