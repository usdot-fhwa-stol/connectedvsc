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

public class NodeOffsetPointXY {
    private byte choice;
    private NodeXY20b nodeXY1;
    private NodeXY22b nodeXY2;
    private NodeXY24b nodeXY3;
    private NodeXY26b nodeXY4;
    private NodeXY28b nodeXY5;
    private NodeXY32b nodeXY6;
    private NodeLLmD64b nodeLatLon;

    // Constants for choice field
    public static final byte NODE_XY1 = 1;
    public static final byte NODE_XY2 = 2;
    public static final byte NODE_XY3 = 3;
    public static final byte NODE_XY4 = 4;
    public static final byte NODE_XY5 = 5;
    public static final byte NODE_XY6 = 6;
    public static final byte NODE_LAT_LON = 7;


    // Constructors
    public NodeOffsetPointXY() {
    }

    public NodeOffsetPointXY(byte choice, NodeXY20b nodeXY1, NodeXY22b nodeXY2, NodeXY24b nodeXY3,
                             NodeXY26b nodeXY4, NodeXY28b nodeXY5, NodeXY32b nodeXY6, NodeLLmD64b nodeLatLon) {
        this.choice = choice;
        this.nodeXY1 = nodeXY1;
        this.nodeXY2 = nodeXY2;
        this.nodeXY3 = nodeXY3;
        this.nodeXY4 = nodeXY4;
        this.nodeXY5 = nodeXY5;
        this.nodeXY6 = nodeXY6;
        this.nodeLatLon = nodeLatLon;
    }

    // Getters and Setters
    public byte getChoice() {
        return choice;
    }

    public void setChoice(byte choice) {
        this.choice = choice;
    }

    public NodeXY20b getNodeXY1() {
        return nodeXY1;
    }

    public void setNodeXY1(NodeXY20b nodeXY1) {
        this.nodeXY1 = nodeXY1;
    }

    public NodeXY22b getNodeXY2() {
        return nodeXY2;
    }

    public void setNodeXY2(NodeXY22b nodeXY2) {
        this.nodeXY2 = nodeXY2;
    }

    public NodeXY24b getNodeXY3() {
        return nodeXY3;
    }

    public void setNodeXY3(NodeXY24b nodeXY3) {
        this.nodeXY3 = nodeXY3;
    }

    public NodeXY26b getNodeXY4() {
        return nodeXY4;
    }

    public void setNodeXY4(NodeXY26b nodeXY4) {
        this.nodeXY4 = nodeXY4;
    }

    public NodeXY28b getNodeXY5() {
        return nodeXY5;
    }

    public void setNodeXY5(NodeXY28b nodeXY5) {
        this.nodeXY5 = nodeXY5;
    }

    public NodeXY32b getNodeXY6() {
        return nodeXY6;
    }

    public void setNodeXY6(NodeXY32b nodeXY6) {
        this.nodeXY6 = nodeXY6;
    }

    public NodeLLmD64b getNodeLatLon() {
        return nodeLatLon;
    }

    public void setNodeLatLon(NodeLLmD64b nodeLatLon) {
        this.nodeLatLon = nodeLatLon;
    }

    @Override
    public String toString() {
        return "NodeOffsetPointXY{" +
                "choice=" + choice +
                ", nodeXY1=" + nodeXY1 +
                ", nodeXY2=" + nodeXY2 +
                ", nodeXY3=" + nodeXY3 +
                ", nodeXY4=" + nodeXY4 +
                ", nodeXY5=" + nodeXY5 +
                ", nodeXY6=" + nodeXY6 +
                ", nodeLatLon=" + nodeLatLon +
                '}';
    }
}
