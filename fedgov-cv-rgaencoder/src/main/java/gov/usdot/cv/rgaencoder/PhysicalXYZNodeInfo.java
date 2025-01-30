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

import java.util.ArrayList;
import java.util.List;

public class PhysicalXYZNodeInfo {
    private List<IndividualXYZNodeGeometryInfo> nodeXYZGeometryNodeSet;

    public PhysicalXYZNodeInfo() {
        this.nodeXYZGeometryNodeSet = new ArrayList<>();
    }

    public PhysicalXYZNodeInfo(List<IndividualXYZNodeGeometryInfo> nodeXYZGeometryNodeSet) {
        this.nodeXYZGeometryNodeSet = nodeXYZGeometryNodeSet;
    }

    public List<IndividualXYZNodeGeometryInfo> getNodeXYZGeometryNodeSet() {
        return nodeXYZGeometryNodeSet;
    }

    public void setNodeXYZGeometryNodeSet(List<IndividualXYZNodeGeometryInfo> nodeXYZGeometryNodeSet) {
        this.nodeXYZGeometryNodeSet = nodeXYZGeometryNodeSet;
    }

    public void addIndividualXYZNodeGeometryInfo(IndividualXYZNodeGeometryInfo individualXYZNodeGeometryInfo) {
        if (individualXYZNodeGeometryInfo != null) {
            this.nodeXYZGeometryNodeSet.add(individualXYZNodeGeometryInfo);
        }
    }

    @Override
    public String toString() {
        return "PhysicalXYZNodeInfo [nodeXYZGeometryNodeSet=" + nodeXYZGeometryNodeSet + "]";
    }
}
