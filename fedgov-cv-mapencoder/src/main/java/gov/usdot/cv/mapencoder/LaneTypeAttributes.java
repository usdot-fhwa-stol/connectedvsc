/*
 * Copyright (C) 2024 LEIDOS.
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

public class LaneTypeAttributes {
    private byte choice;
    private LaneAttributesVehicle vehicle;
    private LaneAttributesCrosswalk crosswalk;
    private LaneAttributesBike bikeLane;
    private LaneAttributesSidewalk sidewalk;
    private LaneAttributesBarrier median;
    private LaneAttributesStriping striping;
    private LaneAttributesTrackedVehicle trackedVehicle;
    private LaneAttributesParking parking;

    // Constants
    public static final byte VEHICLE = 0;
    public static final byte CROSSWALK = 1;
    public static final byte BIKE_LANE = 2;
    public static final byte SIDEWALK = 3;
    public static final byte MEDIAN = 4;
    public static final byte STRIPING = 5;
    public static final byte TRACKED_VEHICLE = 6;
    public static final byte PARKING = 7;

    // Constructors
    public LaneTypeAttributes() {
    }

    public LaneTypeAttributes(
        byte choice,
        LaneAttributesVehicle vehicle,
        LaneAttributesCrosswalk crosswalk,
        LaneAttributesBike bikeLane,
        LaneAttributesSidewalk sidewalk,
        LaneAttributesBarrier median,
        LaneAttributesStriping striping,
        LaneAttributesTrackedVehicle trackedVehicle,
        LaneAttributesParking parking
    ) {
        this.choice = choice;
        this.vehicle = vehicle;
        this.crosswalk = crosswalk;
        this.bikeLane = bikeLane;
        this.sidewalk = sidewalk;
        this.median = median;
        this.striping = striping;
        this.trackedVehicle = trackedVehicle;
        this.parking = parking;
    }

    // Getter and Setter methods for each field...
    public byte getChoice() {
        return choice;
    }

    public void setChoice(byte choice) {
        this.choice = choice;
    }

    public LaneAttributesVehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(LaneAttributesVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LaneAttributesCrosswalk getCrosswalk() {
        return crosswalk;
    }

    public void setCrosswalk(LaneAttributesCrosswalk crosswalk) {
        this.crosswalk = crosswalk;
    }

    public LaneAttributesBike getBikeLane() {
        return bikeLane;
    }

    public void setBikeLane(LaneAttributesBike bikeLane) {
        this.bikeLane = bikeLane;
    }

    public LaneAttributesSidewalk getSidewalk() {
        return sidewalk;
    }

    public void setSidewalk(LaneAttributesSidewalk sidewalk) {
        this.sidewalk = sidewalk;
    }

    public LaneAttributesBarrier getMedian() {
        return median;
    }

    public void setMedian(LaneAttributesBarrier median) {
        this.median = median;
    }

    public LaneAttributesStriping getStriping() {
        return striping;
    }

    public void setStriping(LaneAttributesStriping striping) {
        this.striping = striping;
    }

    public LaneAttributesTrackedVehicle getTrackedVehicle() {
        return trackedVehicle;
    }

    public void setTrackedVehicle(LaneAttributesTrackedVehicle trackedVehicle) {
        this.trackedVehicle = trackedVehicle;
    }

    public LaneAttributesParking getParking() {
        return parking;
    }

    public void setParking(LaneAttributesParking parking) {
        this.parking = parking;
    }

    @Override
    public String toString() {
        return "LaneTypeAttributes{" +
                "choice=" + choice +
                ", vehicle=" + vehicle +
                ", crosswalk=" + crosswalk +
                ", bikeLane=" + bikeLane +
                ", sidewalk=" + sidewalk +
                ", median=" + median +
                ", striping=" + striping +
                ", trackedVehicle=" + trackedVehicle +
                ", parking=" + parking +
                '}';
    }

}
