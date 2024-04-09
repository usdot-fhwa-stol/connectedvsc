package gov.usdot.cv.msg.builder.util;

import gov.usdot.cv.mapencoder.Encoder;
import gov.usdot.cv.mapencoder.MapData;
import gov.usdot.cv.mapencoder.DataParameters;
import gov.usdot.cv.mapencoder.IntersectionGeometry;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class J2735HelperTest {
    
    // Encoder encoder;
    J2735Helper helper;
    MapData mockMap;
    DataParameters mockDataParameters;
    IntersectionGeometry mockIntersectionGeometry;
    String hexres;
    

    @Before
    public void setup() {
        helper = new J2735Helper();
        mockMap = mock(MapData.class);
        mockDataParameters = mock(DataParameters.class);
        mockIntersectionGeometry = mock(IntersectionGeometry.class);
        when(mockMap.getLayerId()).thenReturn(1);
        when(mockMap.getTimeStamp()).thenReturn(12L);
        when(mockMap.getMsgCount()).thenReturn(1);
        when(mockMap.getLayerType()).thenReturn(1);
        when(mockDataParameters.getProcessMethod()).thenReturn("a");
        when(mockDataParameters.getProcessAgency()).thenReturn("b");
        when(mockDataParameters.getLastCheckedDate()).thenReturn("a");
        when(mockDataParameters.getGeoidUsed()).thenReturn("a");
        when(mockMap.getDataParameters()).thenReturn(mockDataParameters);
        when(mockIntersectionGeometry.getName()).thenReturn("name");
        when(mockMap.getIntersections()).thenReturn(new IntersectionGeometry[0]);
        when(mockMap.intersectionExists()).thenReturn(false);
    }

    @Test
    public void HelperTest() {
        hexres = helper.getHexString(mockMap);
        System.out.println("HEX: " + hexres);

        String expected = "00120430011020";
        Assert.assertEquals(expected, hexres);
    }
}