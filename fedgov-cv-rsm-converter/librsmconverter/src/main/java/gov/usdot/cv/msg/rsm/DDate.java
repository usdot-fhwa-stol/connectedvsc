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
 * DDate data class from J2735 ASN.1 Specification
 */
public class DDate {
    private int year;
    private int month;
    private int day;

    public DDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * @return The year contained in this datestamp
     */
    public int getYear() {
        return this.year;
    }

    /**
     * @return The month contained in this datestamp
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * @return The day contained in this datestamp
     */
    public int getDay() {
        return this.day;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DDate)) {
            return false;
        }
        DDate dDate = (DDate) o;
        return year == dDate.year && month == dDate.month && day == dDate.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @Override
    public String toString() {
        return "{" +
            " year='" + getYear() + "'" +
            ", month='" + getMonth() + "'" +
            ", day='" + getDay() + "'" +
            "}";
    }
}
