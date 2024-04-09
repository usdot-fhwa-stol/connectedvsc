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
 * EventInfo data class from the RSM ASN.1 specification
 * <p>
 * Contains the data pertaining to the event the RSM describes including start/end
 * dates plus times, cause, and recurrence information.
 */
public class EventInfo {
    private String eventId;
    private Optional<MsgSegmentInfo> msgSegmentInfo;
    private DDateTime startDateTime;
    private Optional<DDateTime> endDateTime;
    private Optional<List<EventRecurrence>> eventRecurrence;
    private Integer causeCode;
    private Optional<Integer> subCauseCode;

    public EventInfo(String eventId, Optional<MsgSegmentInfo> msgSegmentInfo, DDateTime startDateTime, Optional<DDateTime> endDateTime, Optional<List<EventRecurrence>> eventRecurrence, Integer causeCode, Optional<Integer> subCauseCode) {
        this.eventId = eventId;
        this.msgSegmentInfo = msgSegmentInfo;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.eventRecurrence = eventRecurrence;
        this.causeCode = causeCode;
        this.subCauseCode = subCauseCode;
    }

    /**
     * @return The identifier for the event as a unique string
     */
    public String getEventId() {
        return this.eventId;
    }

    /**
     * @return The message segmentation information for this RSM, if present
     */
    public Optional<MsgSegmentInfo> getMsgSegmentInfo() {
        return this.msgSegmentInfo;
    }

    /**
     * @return The start date and time of the event for this RSM
     */
    public DDateTime getStartDateTime() {
        return this.startDateTime;
    }

    /**
     * @return The end date and time of the event for this RSM, if present
     */
    public Optional<DDateTime> getEndDateTime() {
        return this.endDateTime;
    }

    /**
     * @return The event recurrence information of the event for this RSM, 
     *         if present. More than one recurrence pattern may be present.
     */
    public Optional<List<EventRecurrence>> getEventRecurrence() {
        return this.eventRecurrence;
    }

    /**
     * @return The ITIS code associated with the cause of this event
     */
    public Integer getCauseCode() {
        return this.causeCode;
    }

    /**
     * @return The ITIS code associated with the sub-cause of this event, 
     *         if present
     */
    public Optional<Integer> getSubCauseCode() {
        return this.subCauseCode;
    }

    @Override
    public String toString() {
        return "{" +
            " eventId='" + getEventId() + "'" +
            ", msgSegmentInfo='" + getMsgSegmentInfo() + "'" +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", eventRecurrence='" + getEventRecurrence() + "'" +
            ", causeCode='" + getCauseCode() + "'" +
            ", subCauseCode='" + getSubCauseCode() + "'" +
            "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EventInfo)) {
            return false;
        }
        EventInfo eventInfo = (EventInfo) o;
        return Objects.equals(eventId, eventInfo.eventId) && Objects.equals(msgSegmentInfo, eventInfo.msgSegmentInfo) && Objects.equals(startDateTime, eventInfo.startDateTime) && Objects.equals(endDateTime, eventInfo.endDateTime) && Objects.equals(eventRecurrence, eventInfo.eventRecurrence) && Objects.equals(causeCode, eventInfo.causeCode) && Objects.equals(subCauseCode, eventInfo.subCauseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, msgSegmentInfo, startDateTime, endDateTime, eventRecurrence, causeCode, subCauseCode);
    }
}