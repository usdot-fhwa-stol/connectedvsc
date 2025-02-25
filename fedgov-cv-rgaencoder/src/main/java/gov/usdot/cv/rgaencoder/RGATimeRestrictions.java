/*
* Copyright (C) 2025 LEIDOS.
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

public class RGATimeRestrictions {
    private TimeWindowItemControlInfo fixedTimeWindowItemCtrl;
    private OtherDSItemControlInfo otherDataSetItemCtrl;

    public RGATimeRestrictions() {
        this.fixedTimeWindowItemCtrl = null;
        this.otherDataSetItemCtrl = null;
    }

    public RGATimeRestrictions(TimeWindowItemControlInfo fixedTimeWindowItemCtrl, OtherDSItemControlInfo otherDataSetItemCtrl) {
        this.fixedTimeWindowItemCtrl = fixedTimeWindowItemCtrl;
        this.otherDataSetItemCtrl = otherDataSetItemCtrl;
    }

    public TimeWindowItemControlInfo getFixedTimeWindowCtrl() {
        return fixedTimeWindowItemCtrl;
    }

    public void setFixedTimeWindowCtrl(TimeWindowItemControlInfo fixedTimeWindowItemCtrl) {
        this.fixedTimeWindowItemCtrl = fixedTimeWindowItemCtrl;
    }

    public OtherDSItemControlInfo getOtherDataSetItemCtrl() {
        return otherDataSetItemCtrl;
    }

    public void setOtherDataSetItemCtrl(OtherDSItemControlInfo otherDataSetItemCtrl) {
        this.otherDataSetItemCtrl = otherDataSetItemCtrl;
    }

    @Override
    public String toString() {
        return "RGATimeRestrictions [fixedTimeWindowItemCtrl=" + fixedTimeWindowItemCtrl + " , otherDataSetItemCtrl= " + otherDataSetItemCtrl + "]";
    }




    
}
