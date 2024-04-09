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

public class NodeXY24b {
    private short x;
    private short y;

    public static final short UNKNOWN = -2048;
    public static final short MIN = -2047;
    public static final short MAX = 2047;

    // Constructors
    public NodeXY24b() {
    }

    public NodeXY24b(short x, short y) {
        this.x = x;
        this.y = y;
    }

    // Getters and Setters
    public short getX() {
        return x;
    }

    public void setX(short x) {
        this.x = x;
    }

    public short getY() {
        return y;
    }

    public void setY(short y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "NodeXY24b{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
