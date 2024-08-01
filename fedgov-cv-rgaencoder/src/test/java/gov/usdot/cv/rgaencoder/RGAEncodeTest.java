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
    public void rgaEncodeTester() {
        long start = System.currentTimeMillis();
        //logger.debug("mockRGA timestamp: " + mockRGA.getTimeStamp());
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

        byte[] expected = { 0, 43, 2, 64, 0 };

       Assert.assertArrayEquals(expected, res.getMessage());
    }
}