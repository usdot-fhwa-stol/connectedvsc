/*
 * Copyright (C) 2024 LEIDOS.
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

package gov.usdot.cv.rgaencoder; 

public class RGAData {
    private Long minuteOfTheYear;
    private Integer majorVer;
    private Integer minorVer;
    private Integer contentVersion;
    private String contentDateTime;
    private BaseLayer baseLayer;
    
    public RGAData()
    {
        this.minuteOfTheYear = null;
        this.majorVer = null;
        this.minorVer = null;
        this.contentVersion = null;
        this.contentDateTime = null;
        this.baseLayer = null;
    }
    
    public RGAData(Long minuteOfTheYear,int majorVer, int minorVer, int contentVersion, String contentDateTime) {
        this.minuteOfTheYear = minuteOfTheYear;
        this.majorVer = majorVer;
        this.minorVer = minorVer;
        this.contentVersion = contentVersion;
        this.contentDateTime = contentDateTime;
        this.baseLayer = baseLayer;
    }

    public Long getMinuteOfTheYear() {
        return minuteOfTheYear;
    }

    public int getMajorVer() {
        return majorVer;
    }

    public int getMinorVer() {
        return minorVer;
    }

    public int getContentVersion() {
        return contentVersion;
    }

    public String getContentDateTime() {
        return contentDateTime;
    }

    public BaseLayer getBaseLayer() {
        return this.baseLayer;
    }

    public void setMinuteOfTheYear(Long minuteOfTheYear) {
        this.minuteOfTheYear = minuteOfTheYear;
    }

    public void setMajorVer(Integer majorVer){
        this.majorVer = majorVer;
    }

    public void setMinorVer(Integer minorVer){
        this.minorVer = minorVer;
    }

    public void setContentVersion(Integer contentVersion){
        this.contentVersion = contentVersion;
    }

    public void setContentDateTime(String contentDateTime){
        this.contentDateTime = contentDateTime;
    }

    public void setBaseLayer(BaseLayer baseLayer){
        this.baseLayer = baseLayer;
    }

    @Override
    public String toString() {
        return "RGAData [minuteOfTheYear" + minuteOfTheYear + ", majorVer=" + majorVer + ", minorVer=" + minorVer + ", contentVer=" + contentVersion + ", contentDate Time" + contentDateTime + "]";
    }
}
