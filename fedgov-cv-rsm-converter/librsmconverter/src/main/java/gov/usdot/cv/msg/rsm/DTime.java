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
 * DTime data class from J2735 ASN.1 specification
 */
public class DTime {
    private int hour;
    private int minute;
    private int second;
    private Optional<Integer> offset;

    public DTime(int hour, int minute, int second, Optional<Integer> offset) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.offset = offset;
    }

    /**
     * @return The hour contained in this timestamp
     */
    public int getHour() {
        return this.hour;
    }

    /**
     * @return The minute contained in this timestamp
     */
    public int getMinute() {
        return this.minute;
    }

    /**
     * @return The second contained in this timestamp
     */
    public int getSecond() {
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
        if (!(o instanceof DTime)) {
            return false;
        }
        DTime dTime = (DTime) o;
        return hour == dTime.hour && minute == dTime.minute && second == dTime.second && Objects.equals(offset, dTime.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute, second, offset);
    }

    @Override
    public String toString() {
        return "{" +
            " hour='" + getHour() + "'" +
            ", minute='" + getMinute() + "'" +
            ", second='" + getSecond() + "'" +
            ", offset='" + getOffset() + "'" +
            "}";
    }
}