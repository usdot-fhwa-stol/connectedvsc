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
 * ApplicableHeading data class from RSM ASN.1 specification
 * <p>
 * Stores the description of the heading for which the data in the RSM is relevant
 */
public class ApplicableHeading {
    private int headingDeg;
    private int tolerance;

    public ApplicableHeading(int headingDeg, int tolerance) {
        this.headingDeg = headingDeg;
        this.tolerance = tolerance;
    }

    /**
     * @return The heading for which the event is relevant
     */
    public int getHeadingDeg() {
        return this.headingDeg;
    }

    /**
     * @return The acceptable error bounds on the heading for which the event is relevant
     */
    public int getTolerance() {
        return this.tolerance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ApplicableHeading)) {
            return false;
        }
        ApplicableHeading applicableHeading = (ApplicableHeading) o;
        return headingDeg == applicableHeading.headingDeg && tolerance == applicableHeading.tolerance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(headingDeg, tolerance);
    }

    @Override
    public String toString() {
        return "{" +
            " headingDeg='" + getHeadingDeg() + "'" +
            ", tolerance='" + getTolerance() + "'" +
            "}";
    }
}
