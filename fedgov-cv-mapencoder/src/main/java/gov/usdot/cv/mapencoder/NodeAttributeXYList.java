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

public class NodeAttributeXYList {
    private NodeAttributeXY[] node_attribute_xy_list;

    // Constructors

    public NodeAttributeXYList() {
    }

    public NodeAttributeXYList(NodeAttributeXY[] node_attribute_xy_list) {
        this.node_attribute_xy_list = node_attribute_xy_list;
    }

    // Getters and Setters

    public NodeAttributeXY[] getNode_attribute_xy_list() {
        return node_attribute_xy_list;
    }

    public void setNode_attribute_xy_list(NodeAttributeXY[] node_attribute_xy_list) {
        this.node_attribute_xy_list = node_attribute_xy_list;
    }

    @Override
    public String toString() {
        return "NodeAttributeXYList{" +
                "node_attribute_xy_list=" + Arrays.toString(node_attribute_xy_list) +
                '}';
    }
}
