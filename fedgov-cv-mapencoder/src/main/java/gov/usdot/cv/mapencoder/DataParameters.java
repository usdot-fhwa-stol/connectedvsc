/*
 * Copyright (C) 2023 LEIDOS.
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
 
package gov.usdot.cv.mapencoder;

public class DataParameters {
    private String process_method;
    private String process_agency;
    private String last_checked_date;
    private String geoid_used;

    // Constructors
    public DataParameters() {
    }

    public DataParameters(String process_method, String process_agency, String last_checked_date, String geoid_used) {
        this.process_method = process_method;
        this.process_agency = process_agency;
        this.last_checked_date = last_checked_date;
        this.geoid_used = geoid_used;
    }

    // Getters and Setters
    public String getProcessMethod() {
        return process_method;
    }

    public void setProcessMethod(String process_method) {
        this.process_method = process_method;
    }

    public String getProcessAgency() {
        return process_agency;
    }

    public void setProcessAgency(String process_agency) {
        this.process_agency = process_agency;
    }

    public String getLastCheckedDate() {
        return last_checked_date;
    }

    public void setLastCheckedDate(String last_checked_date) {
        this.last_checked_date = last_checked_date;
    }

    public String getGeoidUsed() {
        return geoid_used;
    }

    public void setGeoidUsed(String geoid_used) {
        this.geoid_used = geoid_used;
    }

    @Override
    public String toString() {
        return "DataParameters{" +
                "process_method='" + process_method + '\'' +
                ", process_agency='" + process_agency + '\'' +
                ", last_checked_date='" + last_checked_date + '\'' +
                ", geoid_used='" + geoid_used + '\'' +
                '}';
    }
}
