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

public class NodeAttributeXY {
    private byte node_attribute_xy;

    public enum NodeAttributeEnum {
        RESERVED((byte) 0),
        STOPLINE((byte) 1),
        ROUNDEDCAPSTYLEA((byte) 2),
        ROUNDEDCAPSTYLEB((byte) 3),
        MERGEPOINT((byte) 4),
        DIVERGEPOINT((byte) 5),
        DOWNSTREAMSTOPLINE((byte) 6),
        DOWNSTREAMSTARTNODE((byte) 7),
        CLOSEDTOTRAFFIC((byte) 8),
        SAFEISLAND((byte) 9),
        CURBPRESENTATSTEPOFF((byte) 10),
        HYDRANTPRESENT((byte) 11);

        private final byte value;

        NodeAttributeEnum(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }

    // Constructors

    public NodeAttributeXY() {
    }

    public NodeAttributeXY(byte node_attribute_xy) {
        this.node_attribute_xy = node_attribute_xy;
    }

    // Getters and Setters

    public byte getNode_attribute_xy() {
        return node_attribute_xy;
    }

    public void setNode_attribute_xy(byte node_attribute_xy) {
        this.node_attribute_xy = node_attribute_xy;
    }

    @Override
    public String toString() {
        return "NodeAttributeXY{" +
                "node_attribute_xy=" + node_attribute_xy +
                '}';
    }
}
