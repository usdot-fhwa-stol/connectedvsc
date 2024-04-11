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

public class ComputedLane {
    private int referenceLaneId;
    private OffsetXaxis offset_x_axis;
    private OffsetYaxis offset_y_axis;
    private int rotate_xy;
    private int scale_x_axis;
    private int scale_y_axis;

    public static final int ROTATEXY_INVALID = 255;

    private boolean rotatexy_exists;
    private boolean scale_x_axis_exists;
    private boolean scale_y_axis_exists;

    // Constructors
    public ComputedLane() {
    }

    public ComputedLane(int referenceLaneId, OffsetXaxis offset_x_axis, OffsetYaxis offset_y_axis,
                        int rotate_xy, int scale_x_axis, int scale_y_axis) {
        this.referenceLaneId = referenceLaneId;
        this.offset_x_axis = offset_x_axis;
        this.offset_y_axis = offset_y_axis;
        this.rotate_xy = rotate_xy;
        this.scale_x_axis = scale_x_axis;
        this.scale_y_axis = scale_y_axis;
    }

    // Getters and Setters
    public int getReferenceLaneId() {
        return referenceLaneId;
    }

    public void setReferenceLaneId(int referenceLaneId) {
        this.referenceLaneId = referenceLaneId;
    }
    
    public OffsetXaxis getOffsetXaxis() {
        return offset_x_axis;
    }

    public void setOffsetXaxis(OffsetXaxis offset_x_axis) {
        this.offset_x_axis = offset_x_axis;
    }

    public OffsetYaxis getOffsetYaxis() {
        return offset_y_axis;
    }

    public void setOffsetYaxis(OffsetYaxis offset_y_axis) {
        this.offset_y_axis = offset_y_axis;
    }


    @Override
    public String toString() {
        return "ComputedLane{" +
                "referenceLaneId=" + referenceLaneId +
                ", offset_x_axis=" + offset_x_axis +
                ", offset_y_axis=" + offset_y_axis +
                ", rotate_xy=" + rotate_xy +
                ", scale_x_axis=" + scale_x_axis +
                ", scale_y_axis=" + scale_y_axis +
                ", rotatexy_exists=" + rotatexy_exists +
                ", scale_x_axis_exists=" + scale_x_axis_exists +
                ", scale_y_axis_exists=" + scale_y_axis_exists +
                '}';
    }
}
