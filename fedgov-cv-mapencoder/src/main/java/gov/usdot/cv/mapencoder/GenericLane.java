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
 
package gov.usdot.cv.mapencoder;

public class GenericLane {
    private int laneId;
    private String name;
    private boolean nameExists;
    private byte ingressApproach;
    private boolean ingressApproachExists;
    private byte egressApproach;
    private boolean egressApproachExists;
    private LaneAttributes laneAttributes;
    private AllowedManeuvers maneuvers;
    private boolean maneuversExists;
    private NodeListXY nodeList;
    private boolean connectsToExists;
    private Connection[] connections;
    private OverlayLaneList overlayLaneList;
    private boolean overlayLaneListExists;

    // Constructors
    public GenericLane() {
    }

    public GenericLane(
            int laneId,
            String name,
            boolean nameExists,
            byte ingressApproach,
            boolean ingressApproachExists,
            byte egressApproach,
            boolean egressApproachExists,
            LaneAttributes laneAttributes,
            AllowedManeuvers maneuvers,
            boolean maneuversExists,
            NodeListXY nodeList,
            boolean connectsToExists,
            Connection[] connections,
            OverlayLaneList overlayLaneList,
            boolean overlayLaneListExists) {
        this.laneId = laneId;
        this.name = name;
        this.nameExists = nameExists;
        this.ingressApproach = ingressApproach;
        this.ingressApproachExists = ingressApproachExists;
        this.egressApproach = egressApproach;
        this.egressApproachExists = egressApproachExists;
        this.laneAttributes = laneAttributes;
        this.maneuvers = maneuvers;
        this.maneuversExists = maneuversExists;
        this.connectsToExists = connectsToExists;
        this.connections = connections;
        this.overlayLaneList = overlayLaneList;
        this.overlayLaneListExists = overlayLaneListExists;
    }

    // Getter and Setter methods for each field...
    public int getLaneID() {
        return laneId;
    }

    public void setLaneID(int laneID) {
        this.laneId = laneID;
    }

    public boolean isNameExists() {
        return nameExists;
    }

    public void setNameExists(boolean nameExists) {
        this.nameExists = nameExists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIngressApproachExists() {
        return ingressApproachExists;
    }

    public void setIngressApproachExists(boolean ingressApproachExists) {
        this.ingressApproachExists = ingressApproachExists;
    }

    public byte getIngressApproach() {
        return ingressApproach;
    }

    public void setIngressApproach(byte ingressApproach) {
        this.ingressApproach = ingressApproach;
    }

    public boolean isEgressApproachExists() {
        return egressApproachExists;
    }

    public void setEgressApproachExists(boolean egressApproachExists) {
        this.egressApproachExists = egressApproachExists;
    }

    public byte getEgressApproach() {
        return egressApproach;
    }

    public void setEgressApproach(byte egressApproach) {
        this.egressApproach = egressApproach;
    }

    public boolean isManeuversExists() {
        return maneuversExists;
    }

    public void setManeuversExists(boolean maneuversExists) {
        this.maneuversExists = maneuversExists;
    }

    public AllowedManeuvers getManeuvers() {
        return maneuvers;
    }

    public void setManeuvers(AllowedManeuvers maneuvers) {
        this.maneuvers = maneuvers;
    }

    public LaneAttributes getLaneAttributes() {
        return laneAttributes;
    }

    public void setLaneAttributes(LaneAttributes laneAttributes) {
        this.laneAttributes = laneAttributes;
    }

    public NodeListXY getNodeList() {
        return nodeList;
    }

    public void setNodeList(NodeListXY nodeList) {
        this.nodeList = nodeList;
    }

    public boolean isConnectsToExists() {
        return connectsToExists;
    }

    public void setConnectsToExists(boolean connectsToExists) {
        this.connectsToExists = connectsToExists;
    }

    public Connection[] getConnections() {
        return connections;
    }

    public void setConnections(Connection[] connections) {
        this.connections = connections;
    }

    @Override
    public String toString() {
        return "GenericLane{" +
                "laneId=" + laneId +
                ", name='" + name + '\'' +
                ", nameExists=" + nameExists +
                ", ingressApproach=" + ingressApproach +
                ", ingressApproachExists=" + ingressApproachExists +
                ", egressApproach=" + egressApproach +
                ", egressApproachExists=" + egressApproachExists +
                ", laneAttributes=" + laneAttributes +
                ", maneuvers=" + maneuvers +
                ", maneuversExists=" + maneuversExists +
                ", nodeList=" + nodeList +
                // ", connectsTo=" + connectsTo +
                ", connectsToExists=" + connectsToExists +
                ", overlayLaneList=" + overlayLaneList +
                ", overlayLaneListExists=" + overlayLaneListExists +
                '}';
    }

}
