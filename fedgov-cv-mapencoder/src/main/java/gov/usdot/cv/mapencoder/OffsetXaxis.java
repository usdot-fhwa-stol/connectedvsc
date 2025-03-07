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

public class OffsetXaxis {
    private byte choice;
    public static final byte SMALL = 0;
    public static final byte LARGE = 1;

    private short small;
    private short large;

    // Constructors

    public OffsetXaxis() {
    }

    public OffsetXaxis(byte choice) {
        this.choice = choice;
    }

    // Getters and Setters

    public byte getChoice() {
        return choice;
    }

    public void setChoice(byte choice) {
        this.choice = choice;
    }

    public short getSmall() {
        return small;
    }

    public void setSmall(short small) {
        this.small = small;
    }

    public short getLarge() {
        return large;
    }

    public void setLarge(short large) {
        this.large = large;
    }

    @Override
    public String toString() {
        return "OffsetXaxis{" +
                "choice=" + choice +
                ", small=" + small +
                ", large=" + large +
                '}';
    }
}
