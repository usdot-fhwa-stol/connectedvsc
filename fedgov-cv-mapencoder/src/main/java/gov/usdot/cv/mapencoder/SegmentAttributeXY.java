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

public class SegmentAttributeXY {
    private byte segmentAttributeXY;

    public static final byte RESERVED = 0;
    public static final byte DONOTBLOCK = 1;
    public static final byte WHITELINE = 2;
    public static final byte MERGINGLANELEFT = 3;
    public static final byte MERGINGLANERIGHT = 4;
    public static final byte CURBONLEFT = 5;
    public static final byte CURBONRIGHT = 6;
    public static final byte LOADINGZONEONLEFT = 7;
    public static final byte LOADINGZONEONRIGHT = 8;
    public static final byte TURNOUTPOINTONLEFT = 9;
    public static final byte TURNOUTPOINTONRIGHT = 10;
    public static final byte ADJACENTPARKINGONLEFT = 11;
    public static final byte ADJACENTPARKINGONRIGHT = 12;
    public static final byte ADJACENTBIKELANEONLEFT = 13;
    public static final byte ADJACENTBIKELANEONRIGHT = 14;
    public static final byte SHAREDBIKELANE = 15;
    public static final byte BIKEBOXINFRONT = 16;
    public static final byte TRANSITSTOPONLEFT = 17;
    public static final byte TRANSITSTOPONRIGHT = 18;
    public static final byte TRANSITSTOPINLANE = 19;
    public static final byte SHAREDWITHTRACKEDVEHICLE = 20;
    public static final byte SAFEISLAND = 21;
    public static final byte LOWCURBSPRESENT = 22;
    public static final byte RUMBLESTRIPPRESENT = 23;
    public static final byte AUDIBLESIGNALINGPRESENT = 24;
    public static final byte ADAPTIVETIMINGPRESENT = 25;
    public static final byte RFSIGNALREQUESTPRESENT = 26;
    public static final byte PARTIALCURBINTRUSION = 27;
    public static final byte TAPERTOLEFT = 28;
    public static final byte TAPERTORIGHT = 29;
    public static final byte TAPERTOCENTERLINE = 30;
    public static final byte PARALLELPARKING = 31;
    public static final byte FREEPARKING = 32;
    public static final byte TIMERESTRICTIONSONPARKING = 33;
    public static final byte COSTTOPARK = 34;
    public static final byte MIDBLOCKCURBPRESENT = 35;
    public static final byte UNEVENPAVEMENTPRESENT = 36;

    // Constructors

    public SegmentAttributeXY() {
    }

    public SegmentAttributeXY(byte segmentAttributeXY) {
        this.segmentAttributeXY = segmentAttributeXY;
    }

    // Getters and Setters

    public byte getSegmentAttributeXY() {
        return segmentAttributeXY;
    }

    public void setSegmentAttributeXY(byte segmentAttributeXY) {
        this.segmentAttributeXY = segmentAttributeXY;
    }

    @Override
    public String toString() {
        return "SegmentAttributeXY{" +
                "segmentAttributeXY=" + segmentAttributeXY +
                '}';
    }
}
