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
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.usdot.cv.mapencoder.ByteArrayObject;

public class RGAEncodeTest {
    private static final Logger logger = LogManager.getLogger(RGAEncodeTest.class);
    Encoder encoder;
    RGAData mockRGA;
    
    @Before
    public void setup() {
        mockRGA = mock(RGAData.class);
        
        encoder = new Encoder();

        when(mockRGA.getMajorVer()).thenReturn(2);
        when(mockRGA.getMinorVer()).thenReturn(1);
    }


    @Test
    public void MAPEncodeTest() {
        long start = System.currentTimeMillis();
        //logger.debug("mockMap timestamp: " + mockRGA.getTimeStamp());
        ByteArrayObject res = encoder.encode(mockRGA);
        long end = System.currentTimeMillis();
        try {
            for (byte b : res.getMessage()) {
                logger.debug(b + " ");
            }
        }
        catch(Exception e) {
            logger.error("Exception while printing res");
        }

        byte[] expected = { 0, 18, 103, 56, 1, 16, 32, 112, -39, 59, -70, 101, -27, -49, 46, 62, -102, 119, -18, 64,
                -58, 0, 24, 0, 22, 89, 53, -92, -23, 7, 107, 73, -46, 10, 16, 13, 0, -58, 4, 0, 72, 2, -26, -16, -77,
                48, -18, -54, -127, -113, -107, 84, -4, 0, 52, 0, 20, 16, 20, 6, 24, 9, 1, 69, 0, -48, 14, 56, 4, 64,
                36, -112, 5, 64, 22, 88, 0, -56, 0, -41, 53, -92, -23, 7, 53, -92, -23, 14, 14, 10, 4, 0, 76, 65, 16,
                -121, -63, 116, 2, 0, 24, 0, 23, -65, -63, -62 };

       //Assert.assertArrayEquals(expected, res.getMessage());
    }
}