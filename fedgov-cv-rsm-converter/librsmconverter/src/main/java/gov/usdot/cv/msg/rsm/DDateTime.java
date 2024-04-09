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
 * DDateTime data class from J2735 ASN.1 Specification
 */
public class DDateTime {
    private Optional<Integer> year;
    private Optional<Integer> month;
    private Optional<Integer> day;
    private Optional<Integer> hour;
    private Optional<Integer> minute;
    private Optional<Integer> second;
    private Optional<Integer> offset;

    public DDateTime(Optional<Integer> year, Optional<Integer> month, Optional<Integer> day, Optional<Integer> hour, Optional<Integer> minute, Optional<Integer> second, Optional<Integer> offset) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.offset = offset;
    }

    /**
     * @return The year contained in this datetimestamp, if it exists
     */
    public Optional<Integer> getYear() {
        return this.year;
    }

    /**
     * @return The month contained in this datetimestamp, if it exists
     */
    public Optional<Integer> getMonth() {
        return this.month;
    }

    /**
     * @return The day contained in this datetimestamp, if it exists
     */
    public Optional<Integer> getDay() {
        return this.day;
    }

    /**
     * @return The hour contained in this datetimestamp, if it exists
     */
    public Optional<Integer> getHour() {
        return this.hour;
    }

    /**
     * @return The minute contained in this datetimestamp, if it exists
     */
    public Optional<Integer> getMinute() {
        return this.minute;
    }

    /**
     * @return The second contained in this datetimestamp, if it exists
     */    
    public Optional<Integer> getSecond() {
        return this.second;
    }

    /**
     * @return The timezone contained in this datetimestamp in terms of hours
     *         offset from GMT, if it exists
     */
    public Optional<Integer> getOffset() {
        return this.offset;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DDateTime)) {
            return false;
        }
        DDateTime dDateTime = (DDateTime) o;
        return Objects.equals(year, dDateTime.year) && Objects.equals(month, dDateTime.month) && Objects.equals(day, dDateTime.day) && Objects.equals(hour, dDateTime.hour) && Objects.equals(minute, dDateTime.minute) && Objects.equals(second, dDateTime.second) && Objects.equals(offset, dDateTime.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day, hour, minute, second, offset);
    }

    @Override
    public String toString() {
        return "{" +
            " year='" + getYear() + "'" +
            ", month='" + getMonth() + "'" +
            ", day='" + getDay() + "'" +
            ", hour='" + getHour() + "'" +
            ", minute='" + getMinute() + "'" +
            ", second='" + getSecond() + "'" +
            ", offset='" + getOffset() + "'" +
            "}";
    }
}
