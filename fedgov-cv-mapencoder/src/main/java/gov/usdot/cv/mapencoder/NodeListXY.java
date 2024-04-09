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

public class NodeListXY {
    private byte choice;
    private NodeSetXY nodes;
    private ComputedLane computed;

    // Constants for choice field
    public static final byte NODE_SET_XY = 0;
    public static final byte COMPUTED_LANE = 1;

    // Constructors
    public NodeListXY() {
    }

    public NodeListXY(byte choice, NodeSetXY nodes, ComputedLane computed) {
        this.choice = choice;
        this.nodes = nodes;
        this.computed = computed;
    }

    // Getters and Setters
    public byte getChoice() {
        return choice;
    }

    public void setChoice(byte choice) {
        this.choice = choice;
    }

    public NodeSetXY getNodes() {
        return nodes;
    }

    public void setNodes(NodeSetXY nodes) {
        this.nodes = nodes;
    }

    public ComputedLane getComputed() {
        return computed;
    }

    public void setComputed(ComputedLane computed) {
        this.computed = computed;
    }

    @Override
    public String toString() {
        return "NodeListXY{" +
                "choice=" + choice +
                ", nodes=" + nodes +
                ", computed=" + computed +
                '}';
    }

}
