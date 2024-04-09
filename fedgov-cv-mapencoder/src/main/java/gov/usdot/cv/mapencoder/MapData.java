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

public class MapData {
    private Long timeStamp; // uint32 can be represented using Integer in Java
    private Boolean timeStampExists;
    private Byte msgIssueRevision; // uint8 can be represented using byte in Java
    private Integer layerType;
    private Integer layerId; // uint8 can be represented using Integer in Java
    private Boolean layerIdExists;
    private DataParameters dataParameters;
    private RoadSegmentList roadSegments;
    private Boolean roadSegmentsExists;
    private IntersectionGeometry[] intersections;
    private Boolean intersectionsExists;
    private RestrictionClassList restrictionList;
    private Boolean restrictionListExists;
    
    public MapData()
    {
        this.timeStamp = null; 
        this.timeStampExists = false;
        this.msgIssueRevision = null;
        this.layerType = null;
        this.layerId = null;
        this.layerIdExists = false; 
        this.intersections = null;
        this.intersectionsExists = false; 
        this.dataParameters = null; 
        this.restrictionList = null;
        this.restrictionListExists = false; 
        this.roadSegments = null;
        this.restrictionListExists = false; 
    }
    
    // Constructor with layerId and intersectionsExists
    public MapData(boolean timeStampExists, byte msgIssueRevision, int layerType,
                   int layerId, boolean layerIdExists, boolean intersectionsExists, DataParameters dataParameters, RoadSegmentList roadSegments,
                   IntersectionGeometry[] intersections, RestrictionClassList restrictionList) {
        this.timeStamp = null; // Set timeStamp to null as it is not provided
        this.timeStampExists = timeStampExists;
        this.msgIssueRevision = msgIssueRevision;
        this.layerType = layerType;
        this.layerId = layerId;
        this.layerIdExists = layerIdExists;
        this.intersectionsExists = intersectionsExists;
        this.dataParameters = dataParameters;
        this.roadSegments = roadSegments;
        this.intersections = intersections;
        this.restrictionList = restrictionList;
    }

    // Getters and setters for each field
    public int getLayerId() {
        return layerId;
    }

    public long getTimeStamp() {
        
        if (timeStamp != null) {
            return timeStamp;
        } else {
            return 0;
        }
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getMsgCount() {
        return msgIssueRevision;
    }

    public int getLayerType() {
        return layerType;
    }

    public boolean intersectionExists() {
        return intersectionsExists;
    }

    public void setIntersectionExists(boolean intersectionsExists) {
        this.intersectionsExists = intersectionsExists;
    }

    public void setIntersections(IntersectionGeometry[] intersections)
    {
        this.intersections = intersections;
    }

    public void setLayerIdExists(boolean layerIdExists) {
        this.layerIdExists = layerIdExists;
    }

    public IntersectionGeometry[] getIntersections() {
        return this.intersections;
        // return new IntersectionGeometry[0];
    }

    public RestrictionClassList getRestrictionClassList() {
        return this.restrictionList;
    }

    public DataParameters getDataParameters() {
        return this.dataParameters;
    }

    public RoadSegmentList getRoadSegmentList()
    {
        return this.roadSegments;
    }

    public void setLayerID(Integer layerId)
    {
        this.layerId = layerId;
    }

    public void setLayerType(Integer layerType)
    {
        this.layerType = layerType;
    }

    public void setMsgIssueRevision(byte msg_count)
    {
        this.msgIssueRevision = msg_count;
    }

    public Boolean getTimeStampExists() {
        return timeStampExists;
    }

    public void setTimeStampExists(Boolean timeStampExists) {
        this.timeStampExists = timeStampExists;
    }

    public Boolean getLayerIdExists() {
        return layerIdExists;
    }

    public void setLayerIdExists(Boolean layerIdExists) {
        this.layerIdExists = layerIdExists;
    }

    public Boolean getRoadSegmentsExists() {
        return roadSegmentsExists;
    }

    public void setRoadSegmentsExists(Boolean roadSegmentsExists) {
        this.roadSegmentsExists = roadSegmentsExists;
    }

    public Boolean getRestrictionListExists() {
        return restrictionListExists;
    }

    public void setRestrictionListExists(Boolean restrictionListExists) {
        this.restrictionListExists = restrictionListExists;
    }

    @Override
    public String toString() {
        return "MapData [timeStamp=" + timeStamp + ", msgIssueRevision=" + msgIssueRevision + ", layerType="
                + layerType + ", layerId=" + layerId + "]";
    }
    
    // Other methods or logic can be added here
}
