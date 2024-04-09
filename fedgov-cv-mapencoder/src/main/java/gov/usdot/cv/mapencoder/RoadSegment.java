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

public class RoadSegment {
    private String name;
    private boolean nameExists;
    private RoadSegmentReferenceID id;
    private int revision;
    private Position3D refPoint;
    private Integer laneWidth;
    private boolean laneWidthExists;
    private SpeedLimitList speedLimits;
    private boolean speedLimitsExists;
    private RoadLaneSetList roadLaneSet;

    public RoadSegment() {
    }

    public RoadSegment(String name, boolean nameExists, RoadSegmentReferenceID id, int revision, Position3D refPoint, Integer laneWidth,
                    boolean laneWidthExists, SpeedLimitList speedLimits, boolean speedLimitsExists, RoadLaneSetList roadLaneSet) 
    {
        this.name = name;
        this.nameExists = nameExists;
        this.id = id;
        this.revision = revision;
        this.refPoint = refPoint;
        this.laneWidth = laneWidth;
        this.laneWidthExists = laneWidthExists;
        this.speedLimits = speedLimits;
        this.speedLimitsExists = speedLimitsExists;
        this.roadLaneSet = roadLaneSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNameExists() {
        return nameExists;
    }

    public void setNameExists(boolean nameExists) {
        this.nameExists = nameExists;
    }

    public RoadSegmentReferenceID getId() {
        return id;
    }

    public void setId(RoadSegmentReferenceID id) {
        this.id = id;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public Position3D getRefPoint() {
        return refPoint;
    }

    public void setRefPoint(Position3D refPoint) {
        this.refPoint = refPoint;
    }

    public Integer getLaneWidth() {
        return laneWidth;
    }

    public void setLaneWidth(Integer laneWidth) {
        this.laneWidth = laneWidth;
    }

    public boolean isLaneWidthExists() {
        return laneWidthExists;
    }

    public void setLaneWidthExists(boolean laneWidthExists) {
        this.laneWidthExists = laneWidthExists;
    }

    public SpeedLimitList getSpeedLimits() {
        return speedLimits;
    }

    public void setSpeedLimits(SpeedLimitList speedLimits) {
        this.speedLimits = speedLimits;
    }

    public boolean isSpeedLimitsExists() {
        return speedLimitsExists;
    }

    public void setSpeedLimitsExists(boolean speedLimitsExists) {
        this.speedLimitsExists = speedLimitsExists;
    }

    public RoadLaneSetList getRoadLaneSet() {
        return roadLaneSet;
    }

    public void setRoadLaneSet(RoadLaneSetList roadLaneSet) {
        this.roadLaneSet = roadLaneSet;
    }
}
