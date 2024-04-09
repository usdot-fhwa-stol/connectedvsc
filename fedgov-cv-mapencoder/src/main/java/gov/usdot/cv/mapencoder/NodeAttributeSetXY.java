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

public class NodeAttributeSetXY {
    private NodeAttributeXYList localNode;
    private boolean localNodeExists;

    private SegmentAttributeXYList disabled;
    private boolean disabledExists;

    private SegmentAttributeXYList enabled;
    private boolean enabledExists;

    private LaneDataAttributeList data;
    private boolean dataExists;

    private float dWidth;
    private boolean dWidthExists;

    private float dElevation;
    private boolean dElevationExists;

    // Constructors

    public NodeAttributeSetXY() {
    }

    public NodeAttributeSetXY(NodeAttributeXYList localNode, boolean localNodeExists,
                              SegmentAttributeXYList disabled, boolean disabledExists,
                              SegmentAttributeXYList enabled, boolean enabledExists,
                              LaneDataAttributeList data, boolean dataExists,
                              float dWidth, boolean dWidthExists,
                              float dElevation, boolean dElevationExists) {
        this.localNode = localNode;
        this.localNodeExists = localNodeExists;
        this.disabled = disabled;
        this.disabledExists = disabledExists;
        this.enabled = enabled;
        this.enabledExists = enabledExists;
        this.data = data;
        this.dataExists = dataExists;
        this.dWidth = dWidth;
        this.dWidthExists = dWidthExists;
        this.dElevation = dElevation;
        this.dElevationExists = dElevationExists;
    }

    // Getters and Setters
    // (Getter and setter methods for localNode, disabled, enabled, data, dWidth, and dElevation)

    public boolean isLocal_node_exists() {
        return localNodeExists;
    }

    public void setLocalNodeExists(boolean localNodeExists) {
        this.localNodeExists = localNodeExists;
    }

    public boolean isDisabledExists() {
        return disabledExists;
    }

    public void setDisabledExists(boolean disabledExists) {
        this.disabledExists = disabledExists;
    }

    public boolean isEnabledExists() {
        return enabledExists;
    }

    public void setEnabledExists(boolean enabledExists) {
        this.enabledExists = enabledExists;
    }

    public boolean isDataExists() {
        return dataExists;
    }

    public void setDataExists(boolean dataExists) {
        this.dataExists = dataExists;
    }

    public LaneDataAttributeList getData() {
        return data;
    }

    public void setData(LaneDataAttributeList data) {
        this.data = data;
    }

    public boolean isDWidthExists() {
        return dWidthExists;
    }

    public void setDWidthExists(boolean dWidthExists) {
        this.dWidthExists = dWidthExists;
    }

    public float getDWidth() {
        return dWidth;
    }

    public void setDWidth(float dWidth) {
        this.dWidth = dWidth;
    }

    public boolean isDElevationExists() {
        return dElevationExists;
    }

    public void setDElevationExists(boolean dElevationExists) {
        this.dElevationExists = dElevationExists;
    }

    public float getDElevation() {
        return dElevation;
    }

    public void setDElevation(float dElevation) {
        this.dElevation = dElevation;
    }

    @Override
    public String toString() {
        return "NodeAttributeSetXY{" +
                "localNode=" + localNode +
                ", localNodeExists=" + localNodeExists +
                ", disabled=" + disabled +
                ", disabledExists=" + disabledExists +
                ", enabled=" + enabled +
                ", enabledExists=" + enabledExists +
                ", data=" + data +
                ", dataExists=" + dataExists +
                ", dWidth=" + dWidth +
                ", dWidthExists=" + dWidthExists +
                ", dElevation=" + dElevation +
                ", dElevationExists=" + dElevationExists +
                '}';
    }
}
