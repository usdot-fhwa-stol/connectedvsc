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

package gov.usdot.cv.rgaencoder; 

public class RGAData {
    private Integer majorVer;
    private Integer minorVer;
    
    public RGAData()
    {
        this.majorVer = null;
        this.minorVer = null;
    }
    
    public RGAData(int majorVer, int minorVer) {
        this.majorVer = majorVer;
        this.minorVer = minorVer;
    }

    public int getMajorVer() {
        return majorVer;
    }

    public int getMinorVer() {
        return minorVer;
    }

    public void setMajorVer(Integer majorVer){
        this.majorVer = majorVer;
    }

    public void setMinorVer(Integer minorVer){
        this.minorVer = minorVer;
    }

    @Override
    public String toString() {
        return "RGAData [majorVer=" + majorVer + ", minorVer=" + minorVer + "]";
    }
}
