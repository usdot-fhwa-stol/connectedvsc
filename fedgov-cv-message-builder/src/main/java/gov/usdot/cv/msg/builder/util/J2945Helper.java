package gov.usdot.cv.msg.builder.util;

import gov.usdot.cv.rgaencoder.Encoder;
import gov.usdot.cv.rgaencoder.RGAData;
import gov.usdot.cv.mapencoder.ByteArrayObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class J2945Helper {

	private static final Logger logger = LogManager.getLogger(J2945Helper.class);
	
	public static String getHexString(RGAData message) 
	{

		byte[] bytes = getBytes(message);
		return Hex.encodeHexString(bytes);
	}

	public static byte[] getBytes(RGAData message) {
		Encoder encoder = new Encoder();
		logger.debug("Getting bytes from J2945 helper:  ");
		ByteArrayObject encodedMsg = encoder.encode(message);
		return encodedMsg.getMessage();
	}
}