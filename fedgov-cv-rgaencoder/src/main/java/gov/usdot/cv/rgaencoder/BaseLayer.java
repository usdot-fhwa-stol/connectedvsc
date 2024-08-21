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

import gov.usdot.cv.mapencoder.Position3D;

public class BaseLayer {
    //DataSetFormatVersionInfo
    private Integer majorVer;
    private Integer minorVer;

    //ReferencePointInfo
    private Position3D location;
    private DDate timeOfCalculation;

    //RoadGeometryRefIDInfo
    private String relativeToRdAuthID;

    //DataSetContentIdentification
    private Integer contentVer;
    private DDateTime contentDateTime;

    public BaseLayer()
    {
        this.majorVer = null;
        this.minorVer = null;
        this.location = null;
        this.timeOfCalculation = null;
        this.relativeToRdAuthID = null;
        this.contentVer = null;
        this.contentDateTime = null;
    }
    
    public BaseLayer(Integer majorVer, Integer minorVer, Position3D location, DDate timeOfCalculation, String relativeToRdAuthID, Integer contentVer, DDateTime contentDateTime) {
        this.majorVer = majorVer;
        this.minorVer = minorVer;
        this.location = location;
        this.timeOfCalculation = timeOfCalculation;
        this.relativeToRdAuthID = relativeToRdAuthID;
        this.contentVer = contentVer;
        this.contentDateTime = contentDateTime;
    }

    public Integer getMajorVer() {
        return majorVer;
    }

    public Integer getMinorVer() {
        return minorVer;
    }

    public Position3D getLocation() {
        return location;
    }

    public DDate getTimeOfCalculation() {
        return timeOfCalculation;
    }
    
    public String getRelativeToRdAuthID() {
        return relativeToRdAuthID;
    }

    public Integer getContentVer() {
        return contentVer;
    }

    public DDateTime getContentDateTime() {
        return contentDateTime;
    }

    public void setMajorVer(Integer majorVer) {
        this.majorVer = majorVer;
    }

    public void setMinorVer(Integer minorVer) {
        this.minorVer = minorVer;
    }

    public void setLocation(Position3D location) {
        this.location = location;
    }

    public void setTimeOfCalculation(DDate timeOfCalculation) {
        this.timeOfCalculation = timeOfCalculation;
    }

    public void setRelativeToRdAuthID(String relativeToRdAuthID) {
        this.relativeToRdAuthID = relativeToRdAuthID;
    }

    public void setContentVer(Integer contentVer) {
        this.contentVer = contentVer;
    }

    public void setContentDateTime(DDateTime contentDateTime) {
        this.contentDateTime = contentDateTime;
    }

    @Override
    public String toString() {
        return "RGAData [majorVer=" + majorVer + ", minorVer=" + minorVer + ", location="+ location + ", timeOfCalculation" + timeOfCalculation + ", contentVer=" + contentVer + ", contentDate Time" + contentDateTime + "]";
    }
}
 