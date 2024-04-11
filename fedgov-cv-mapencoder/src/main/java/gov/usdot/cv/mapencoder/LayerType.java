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

public class LayerType {
    // Field
    private byte layerType;

    // Constants
    public static final byte NONE = 0;
    public static final byte MIXED_CONTENT = 1;
    public static final byte GENERAL_MAP_DATA = 2;
    public static final byte INTERSECTION_DATA = 3;
    public static final byte CURVE_DATA = 4;
    public static final byte ROADWAY_SECTION_DATA = 5;
    public static final byte PARKING_AREA_DATA = 6;
    public static final byte SHARED_LANE_DATA = 7;

    // Constructors
    public LayerType(byte layerType) {
        this.layerType = layerType;
    }

    // Getter and Setter
    public byte getLayerType() {
        return layerType;
    }

    public void setLayerType(byte layerType) {
        this.layerType = layerType;
    }

    @Override
    public String toString() {
        return "LayerType{" +
                "layerType=" + layerType +
                '}';
    }
}
