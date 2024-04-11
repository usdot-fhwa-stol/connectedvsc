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

public class NodeXY {
    private NodeOffsetPointXY delta;
    private NodeAttributeSetXY attributes;
    private boolean attributesExists;

    // Constructors
    public NodeXY() {
    }

    public NodeXY(NodeOffsetPointXY delta, NodeAttributeSetXY attributes, boolean attributesExists) {
        this.delta = delta;
        this.attributes = attributes;
        this.attributesExists = attributesExists;
    }

    // Getters and Setters
    public NodeOffsetPointXY getDelta() {
        return delta;
    }

    public void setDelta(NodeOffsetPointXY delta) {
        this.delta = delta;
    }

    public NodeAttributeSetXY getAttributes() {
        return attributes;
    }

    public void setAttributes(NodeAttributeSetXY attributes) {
        this.attributes = attributes;
    }

    public boolean isAttributesExists() {
        return attributesExists;
    }

    public void setAttributesExists(boolean attributesExists) {
        this.attributesExists = attributesExists;
    }

    @Override
    public String toString() {
        return "NodeXY{" +
                "delta=" + delta +
                ", attributes=" + attributes +
                ", attributesExists=" + attributesExists +
                '}';
    }

}
