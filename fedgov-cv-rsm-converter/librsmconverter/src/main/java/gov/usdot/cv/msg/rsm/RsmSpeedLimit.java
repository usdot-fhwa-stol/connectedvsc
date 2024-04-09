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

import java.util.Objects;

/**
 * RsmSpeedLimit data object from the RSM ASN.1 specification
 * <p>
 * Describes the speed limit and how it is to be obeyed
 */
public class RsmSpeedLimit {
    private SpeedLimitType type;
    private int speed;
    private SpeedUnits speedUnits;

    public RsmSpeedLimit(SpeedLimitType type, int speed, SpeedUnits speedUnits) {
        this.type = type;
        this.speed = speed;
        this.speedUnits = speedUnits;
    }

    /**
     * @return The regulatory type of the speed limit
     */
    public SpeedLimitType getType() {
        return this.type;
    }

    /**
     * @return The numerical speed that is the limit value
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * @return The units of the value returned by {@link RsmSpeedLimit#getSpeed()}
     */
    public SpeedUnits getSpeedUnits() {
        return this.speedUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RsmSpeedLimit)) {
            return false;
        }
        RsmSpeedLimit rsmSpeedLimit = (RsmSpeedLimit) o;
        return Objects.equals(type, rsmSpeedLimit.type) && speed == rsmSpeedLimit.speed && Objects.equals(speedUnits, rsmSpeedLimit.speedUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, speed, speedUnits);
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", speed='" + getSpeed() + "'" +
            ", speedUnits='" + getSpeedUnits() + "'" +
            "}";
    }
    
}