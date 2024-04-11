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

public class Connection {
    private ConnectingLane connectingLane;
    private boolean remoteIntersectionExists;
    private IntersectionReferenceID remoteIntersection;
    private boolean signalGroupExists;
    private long signalGroup;
    private boolean userClassExists;
    private long userClass;
    private boolean connectionIDExists;
    private long connectionID;

    // Constructors
    public Connection() {
    }

    public Connection(ConnectingLane connectingLane, boolean remoteIntersectionExists,
            IntersectionReferenceID remoteIntersection, boolean signalGroupExists, long signalGroup,
            boolean userClassExists, long userClass, boolean connectionIDExists, long connectionID) {
        this.connectingLane = connectingLane;
        this.remoteIntersectionExists = remoteIntersectionExists;
        this.remoteIntersection = remoteIntersection;
        this.signalGroupExists = signalGroupExists;
        this.signalGroup = signalGroup;
        this.userClassExists = userClassExists;
        this.userClass = userClass;
        this.connectionIDExists = connectionIDExists;
        this.connectionID = connectionID;
    }

    public ConnectingLane getConnectingLane() {
        return connectingLane;
    }

    // Getter and Setter methods for each field...
    public void setConnectingLane(ConnectingLane connectingLane) {
        this.connectingLane = connectingLane;
    }

    public boolean getRemoteIntersectionExists() {
        return remoteIntersectionExists;
    }

    public void setRemoteIntersectionExists(boolean remoteIntersectionExists) {
        this.remoteIntersectionExists = remoteIntersectionExists;
    }

    public IntersectionReferenceID getRemoteIntersection() {
        return remoteIntersection;
    }

    public void setRemoteIntersection(IntersectionReferenceID remoteIntersection) {
        this.remoteIntersection = remoteIntersection;
    }

    public boolean getSignalGroupExists() {
        return signalGroupExists;
    }

    public void setSignalGroupExists(boolean signalGroupExists) {
        this.signalGroupExists = signalGroupExists;
    }

    public long getSignalGroup() {
        return signalGroup;
    }

    public void setSignalGroup(long signalGroup) {
        this.signalGroup = signalGroup;
    }

    public boolean getUserClassExists() {
        return userClassExists;
    }

    public void setUserClassExists(boolean userClassExists) {
        this.userClassExists = userClassExists;
    }

    public long getUserClass() {
        return userClass;
    }

    public void setUserClass(long userClass) {
        this.userClass = userClass;
    }

    public boolean getConnectionIDExists() {
        return connectionIDExists;
    }

    public void setConnectionIDExists(boolean connectionIDExists) {
        this.connectionIDExists = connectionIDExists;
    }

    public long getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(long connectionID) {
        this.connectionID = connectionID;
    }

    @Override
    public String toString() {
        return "Connection [connectingLane=" + connectingLane + ", remoteIntersectionExists=" + remoteIntersectionExists
                + ", remoteIntersection=" + remoteIntersection + ", signalGroupExists=" + signalGroupExists
                + ", signalGroup=" + signalGroup + ", userClassExists=" + userClassExists + ", userClass=" + userClass
                + ", connectionIDExists=" + connectionIDExists + ", connectionID=" + connectionID + "]";
    }
}
