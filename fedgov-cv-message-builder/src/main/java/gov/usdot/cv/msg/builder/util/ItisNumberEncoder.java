package gov.usdot.cv.msg.builder.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Encode small and large numbers per J2540 standard
 *
 */
public class ItisNumberEncoder {
	
	private class ItisNumberRange {
		private final int first, last, step, start;
		ItisNumberRange(int first, int last, int step, int start) {
			this.first = first;
			this.last  = last;
			this.step  = step;
			this.start = start;
		}
		boolean inRange(int value) {
			return value >= first && value <= last;
		}
		
		int getCode(int value) {
			assert(value >= first && value <= last);
			int count = (value - first)/step;
			return start + count;
		}
		
		int getValue(int value) {
			assert(value >= first && value <= last);
			int count = (value - first)/step;
			return first + count*step;
		}
	}
	
	private ItisNumberRange[] ranges = {
			new ItisNumberRange(35000, 100000, 5000, 11600),
			new ItisNumberRange(11000,  30000, 1000, 11580),
			new ItisNumberRange( 3500,  10000,  500, 11566),
			new ItisNumberRange( 1100,   3000,  100, 11546),
			new ItisNumberRange(  300,   1000,   50, 11531),
	};
	
	// ^(n((1-((4|8|10|16)th|3rd|2)|3-4)|[1-9]\d*)|\d+)$
	private static String valieNumberRegExpression = "^(n((1-((4|8|10|16)th|3rd|2)|3-4)|[1-9]\\d*)|\\d+)$";
	private static Pattern validNumberPattern = Pattern.compile(valieNumberRegExpression);
	
	private List<Integer> result;

	private ItisNumberEncoder() {
		result = new ArrayList<Integer>();
	}
	
	public static int[] encode(String[] values) throws IllegalArgumentException {
		List<Integer> result = new ArrayList<Integer>();
		for( String value : values ) {
			if ( !isValid(value) )
				throw new IllegalArgumentException("Invalid ITIS numeric value: '" + value +"'.") ;
			result.addAll(encodeValue(value).result);
		}
		return toArray(result);
	}
	
	public static int[] encode(String value) throws IllegalArgumentException {
		if ( !isValid(value) )
			throw new IllegalArgumentException("Invalid ITIS numeric value: '" + value +"'.") ;
		return encodeValue(value).getResult();
	}
	
	public static int[] encodeSafe(String value) {
		if ( !isValid(value) )
			return null;
		return encodeValue(value).getResult();
	}
	
	public static boolean isValid(String value) {
		if ( value == null )
			return false;
		value = value.trim();
		return validNumberPattern.matcher(value).matches();
	}
	
	private static ItisNumberEncoder encodeValue(String value) {
		assert(isValid(value));
		value = value.trim();
		ItisNumberEncoder encoder = new ItisNumberEncoder();
		if ( !value.startsWith("n") ) {
			int codeValue = Integer.parseInt(value);
			encoder.result.add(codeValue);
		} else {
			switch(value) {
			case "n1-16th":
				encoder.result.add(11521);
				break;
			case "n1-10th":
				encoder.result.add(11522);
				break;
			case "n1-8th":
				encoder.result.add(11523);
				break;
			case "n1-4th":
				encoder.result.add(11524);
				break;
			case "n1-3rd":
				encoder.result.add(11525);
				break;
			case "n1-2":
				encoder.result.add(11526);
				break;
			case "n3-4":
				encoder.result.add(11527);
				break;
			default:
				int numberValue = Integer.parseInt(value.substring(1));
				encoder.encode(numberValue);
				break;
			}
		}
		return encoder;
	}
	
	private void encode(int numberValue) {
		assert(numberValue >= 0);
		if ( numberValue == 0 ) {
			result.add(0);
		} else if ( numberValue <= 255 ) {
			encodeSmallNumber(numberValue);
		} else if ( numberValue > 255 && numberValue < 300 ) {
			encodeSmallNumber(255);
			encodeSmallNumber(numberValue - 255);
		} else {
			encodeLargeNumber(numberValue);
		}
	}
	
	private void encodeSmallNumber(int value) {
		assert(value >= 1 && value <= 255);
		result.add(12544 + value);
	}
	
	private void encodeLargeNumber(int value) {
		assert(value >= 300);
		final int maxRange = 100000;
		if ( value > maxRange ) {
			int count = value/maxRange;
			for ( int i = 0; i < count; i++ ) {
				encodeLargeNumber(maxRange);
			}
			encode(value - count*maxRange);
		} else {
			for(ItisNumberRange range : ranges ) {
				if ( range.inRange(value) ) {
					result.add(range.getCode(value));
					value -= range.getValue(value);
				}
			}
			if ( value > 0 )
				encode(value);
		}
	}
	
	private int[] getResult() {
		return result != null ? toArray(result) : null;
	}
	
	private static int[] toArray(List<Integer> list) {
		return list != null ? list.stream().mapToInt(i->i).toArray() : null;
	}

}
