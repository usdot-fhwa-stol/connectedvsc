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

import java.util.Arrays;

public class NodeSetXY {
    private short[] nodeSetHeader;
    private NodeXY[] nodeSetXY;

    // Constructors
    public NodeSetXY() {
    }

    public NodeSetXY(short[] nodeSetHeader, NodeXY[] nodeSetXY) {
        this.nodeSetHeader = nodeSetHeader;
        this.nodeSetXY = nodeSetXY;
    }

    // Getters and Setters
    public short[] getNodeSetHeader() {
        return nodeSetHeader;
    }

    public void setNodeSetHeader(short[] nodeSetHeader) {
        this.nodeSetHeader = nodeSetHeader;
    }

    public NodeXY[] getNodeSetXY() {
        return nodeSetXY;
    }

    public void setNodeSetXY(NodeXY[] nodeSetXY) {
        this.nodeSetXY = nodeSetXY;
    }

    @Override
    public String toString() {
        return "NodeSetXY{" +
                "nodeSetHeader=" + Arrays.toString(nodeSetHeader) +
                ", nodeSetXY=" + Arrays.toString(nodeSetXY) +
                '}';
    }

}
