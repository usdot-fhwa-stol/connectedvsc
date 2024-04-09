/*
 * Copyright (C) 2018-2019 LEIDOS.
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
package gov.usdot.cv.msg.rsm;

/**
 * SpeedLimitType data object from the RSM ASN.1 specification
 */
public enum SpeedLimitType {
    UNKNOWN,
    MAX_SPEED_IN_SCHOOL_ZONE,
    MAX_SPEED_IN_SCHOOL_ZONE_WHEN_CHILDREN_ARE_PRESENT,
    MAX_SPEED_IN_CONSTRUCTION_ZONE,
    VEHICLE_MIN_SPEED,
    VEHICLE_MAX_SPEED,
    VEHICLE_NIGHT_MAX_SPEED,
    TRUCK_MIN_SPEED,
    TRUCK_MAX_SPEED,
    TRUCK_NIGHT_MAX_SPEED,
    VEHICLES_WITH_TRAILERS_MIN_SPEED,
    VEHICLES_WITH_TRAILERS_MAX_SPEED,
    VEHICLES_WITH_TRAILERS_MAX_NIGHT_SPEED
}