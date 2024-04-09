package gov.usdot.cv.msg.builder.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class ItisNumberEncoderTest {
	
	private class Value {
		final String number;
		final int[] codes;
		public Value( String number, int[] codes) {
			this.number = number;
			this.codes = codes;
		}
	}
	
	private Value[] values = {
			new Value("n1-16th", new int[] { 11521 } ),
			new Value("n1-10th", new int[] { 11522 } ),
			new Value("n1-8th",  new int[] { 11523 } ),
			new Value("n1-4th",  new int[] { 11524 } ),
			new Value("n1-3rd",  new int[] { 11525 } ),
			new Value("n1-2", 	 new int[] { 11526 } ),
			new Value("n3-4", 	 new int[] { 11527 } ),
			new Value("11527", 	 new int[] { 11527 } ),
			new Value("0", 	 	 new int[] { 0 } ),
			new Value("n1", 	 new int[] { 12545 } ),
			new Value("n34", 	 new int[] { 12578 } ),		
			new Value("n42", 	 new int[] { 12586 } ),
			new Value("n255", 	 new int[] { 12799 } ),
			new Value("n256", 	 new int[] { 12799, 12545 } ),
			new Value("n300", 	 new int[] { 11531 } ),
			new Value("n301", 	 new int[] { 11531, 12545 } ),			
			new Value("n342", 	 new int[] { 11531, 12586 } ),
			new Value("n4000", 	 new int[] { 11567 } ),
			new Value("n12345",  new int[] { 11581, 11531, 12589 } ),
			new Value("n95000",  new int[] { 11612 } ),
			new Value("n95342",  new int[] { 11612, 11531, 12586 } ),			
			new Value("n212345", new int[] { 11613, 11613, 11581, 11531, 12589 } ),
	};
	
	private String[] multipleValues = {
			"n1-8th",
			"11527",
			"n12345",
			"n212345",
	};
	
	private int[] multipleCodes = {
			11523,
			11527,
			11581, 11531, 12589,
			11613, 11613, 11581, 11531, 12589,
	};
	
	private String[] validNumbers = {
			"n1-16th",
			"n1-10th",
			"n1-8th",
			"n1-4th",
			"n1-3rd",
			"n1-2",
			"n3-4",
			"n300",
			"n255",
			"\tn1234567\t",
			"0",
			"  12345  ",
			"n1",
	};
	
	private String[] invalidNumbers = {
			"n",
			"n0",
			"n01200",
			"N1-16th",
			"n1-32th",
			"n3-10th",
			"n1-7th",
			"n1-3th",
			"n1-2rd",
			"n1-3",
			"n3-8",
			"n300a",
			"n 255",
			"\tn1234 567\t",
			"-1",
			"  +12345  ",	
	};

	@Test
	public void testValid() {
		testValidation(validNumbers, true);
	}
	
	@Test
	public void testinvalid() {
		testValidation(invalidNumbers, false);
	}
	
	private void testValidation(String[] values, boolean expectedResult) {
		for( String value : values)
			assertEquals("Testing: '" + value + "'", expectedResult, ItisNumberEncoder.isValid(value));
	}
	
	@Test
	public void testEncoding() {
		for( Value value : values ) {
			int[] codes = ItisNumberEncoder.encode(value.number);
			assertTrue(Arrays.equals(value.codes, codes));
			//System.out.println(value.number + ": \t" + Arrays.toString(codes));
		}
	}
	
	@Test
	public void testMultipleEncoding() {
		int[] codes = ItisNumberEncoder.encode(multipleValues);
		assertTrue(Arrays.equals(multipleCodes, codes));
		//System.out.println(Arrays.toString(multipleValues) + ":\n\t" + Arrays.toString(codes));
	}

}
