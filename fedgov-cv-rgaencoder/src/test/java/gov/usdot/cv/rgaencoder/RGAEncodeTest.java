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


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.usdot.cv.mapencoder.ByteArrayObject;
import gov.usdot.cv.mapencoder.Position3D;

public class RGAEncodeTest {
    private static final Logger logger = LogManager.getLogger(RGAEncodeTest.class);
    Encoder encoder;
    RGAData mockRGA;
    BaseLayer mockBaseLayer;
    // Position3D mockLocation;
    // DDate mockTimeOfCalculation;
    // DDateTime mockContentDateTime;
    
    @Before
    public void setup() {
        mockRGA = mock(RGAData.class); 
        mockBaseLayer = mock(BaseLayer.class);
        //mockLocation = mock(Position3D.class);
        // mockTimeOfCalculation = mock(DDate.class);
        // mockContentDateTime = mock(DDateTime.class);

        encoder = new Encoder();

        when(mockBaseLayer.getMajorVer()).thenReturn(2);
        //when(mockBaseLayer.getMinorVer()).thenReturn(1);
        // // when(mockBaseLayer.getRelativeToRdAuthID()).thenReturn("a");
        // when(mockBaseLayer.getContentVer()).thenReturn(1);

        //location
        // when(mockLocation.getLatitude()).thenReturn((double)7.2);
        // when(mockLocation.getLongitude()).thenReturn((double)11.1);
        // when(mockLocation.isElevationExists()).thenReturn(true);
        // when(mockLocation.getElevation()).thenReturn((float)13.12);

        // //TimeOfCalculation
        // when(mockTimeOfCalculation.getMonth()).thenReturn(8);
        // when(mockTimeOfCalculation.getDay()).thenReturn(21);
        // when(mockTimeOfCalculation.getYear()).thenReturn(2024);

        // //ContentDateTime
        // when(mockContentDateTime.getHour()).thenReturn(13);
        // when(mockContentDateTime.getMinute()).thenReturn(51);
        // when(mockContentDateTime.getSecond()).thenReturn(20);
        // when(mockBaseLayer.getLocation()).thenReturn(mockLocation);
        when(mockRGA.getBaseLayer()).thenReturn(mockBaseLayer);
    }


    @Test
    public void rgaEncodeTester() {
        ByteArrayObject res = encoder.encode(mockRGA);
        byte[] expected = { 0, 43, 2, 64, 0 };
        //Assert.assertArrayEquals(expected, res.getMessage());
    }
}