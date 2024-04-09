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
 * EventRecurrence data class from the RSM ASN.1 specification
 * <p>
 * Specifies when and how an event is scheduled to recur across time
 */
public class EventRecurrence {
    private Optional<DTime> startTime;
    private Optional<DTime> endTime;
    private Optional<DDate> startDate;
    private Optional<DDate> endDate;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private boolean exclusion;

    public EventRecurrence(Optional<DTime> startTime, Optional<DTime> endTime, Optional<DDate> startDate, Optional<DDate> endDate, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean exclusion) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.exclusion = exclusion;
    }

    /**
     * @return The start time of the event recurrence, if present
     */
    public Optional<DTime> getStartTime() {
        return this.startTime;
    }

    /**
     * @return The end time of the event recurrence, if present
     */
    public Optional<DTime> getEndTime() {
        return this.endTime;
    }

    /**
     * @return The start date of the event recurrence, if present
     */
    public Optional<DDate> getStartDate() {
        return this.startDate;
    }

    /**
     * @return The end date of the event recurrence, if present
     */
    public Optional<DDate> getEndDate() {
        return this.endDate;
    }

    /**
     * @return True if the event recurs weekly on Monday, False o.w.
     */
    public boolean getMonday() {
        return this.monday;
    }

    /**
     * @return True if the event recurs weekly on Monday, False o.w.
     */
    public boolean getTuesday() {
        return this.tuesday;
    }

    /**
     * @return True if the event recurs weekly on Monday, False o.w.
     */    
    public boolean getWednesday() {
        return this.wednesday;
    }

    /**
     * @return True if the event recurs weekly on Monday, False o.w.
     */    
    public boolean getThursday() {
        return this.thursday;
    }

    /**
     * @return True if the event recurs weekly on Monday, False o.w.
     */    
    public boolean getFriday() {
        return this.friday;
    }

    /**
     * @return True if the event recurs weekly on Monday, False o.w.
     */    
    public boolean getSaturday() {
        return this.saturday;
    }

    /**
     * @return True if the event recurs weekly on Monday, False o.w.
     */    
    public boolean getSunday() {
        return this.sunday;
    }

    /**
     * @return True if this recursion patterned should be negated, False o.w.
     */    
    public boolean getExclusion() {
        return this.exclusion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof EventRecurrence)) {
            return false;
        }
        EventRecurrence eventRecurrence = (EventRecurrence) o;
        return Objects.equals(startTime, eventRecurrence.startTime) && Objects.equals(endTime, eventRecurrence.endTime) && Objects.equals(startDate, eventRecurrence.startDate) && Objects.equals(endDate, eventRecurrence.endDate) && monday == eventRecurrence.monday && tuesday == eventRecurrence.tuesday && wednesday == eventRecurrence.wednesday && thursday == eventRecurrence.thursday && friday == eventRecurrence.friday && saturday == eventRecurrence.saturday && sunday == eventRecurrence.sunday && exclusion == eventRecurrence.exclusion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, startDate, endDate, monday, tuesday, wednesday, thursday, friday, saturday, sunday, exclusion);
    }

    @Override
    public String toString() {
        return "{" +
            " startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", monday='" + getMonday() + "'" +
            ", tuesday='" + getTuesday() + "'" +
            ", wednesday='" + getWednesday() + "'" +
            ", thursday='" + getThursday() + "'" +
            ", friday='" + getFriday() + "'" +
            ", saturday='" + getSaturday() + "'" +
            ", sunday='" + getSunday() + "'" +
            ", exclusion='" + getExclusion() + "'" +
            "}";
    }
}
