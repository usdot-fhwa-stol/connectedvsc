package gov.usdot.cv.msg.builder.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONMapper {

	private static final ObjectMapper mapper;
	
	static {
		mapper = new ObjectMapper();
	}
	
	public static <T> T jsonStringToPojo(String jsonString, Class<T> genericType) throws JsonParseException, JsonMappingException, IOException  {
		InputStream is = new ByteArrayInputStream(jsonString.getBytes());
		T configBean = mapper.readValue(is, genericType);
		return configBean;
	}
	
	public static <T> T jsonFileToPojo(String fileName, Class<T> genericType) throws JsonParseException, JsonMappingException, IOException  {
		InputStream is = getFileAsStream(fileName);
		T configBean = mapper.readValue(is, genericType);
		return configBean;
	}
	
	public static <T> List<T> jsonFileToPojoList(String fileName, Class<T> genericType) throws JsonParseException, JsonMappingException, IOException  {
		InputStream is = getFileAsStream(fileName);
		List<T> configBeanList = mapper.readValue(is, mapper.getTypeFactory().constructCollectionType(List.class, genericType));
		return configBeanList;
	}
	
	private static InputStream getFileAsStream(String fileName) throws FileNotFoundException  {
		InputStream is = null;
		File f = new File(fileName);
		if (f.exists()) {
			is = new FileInputStream(f);
		} else {
			is = JSONMapper.class.getClassLoader().getResourceAsStream(fileName);
		}
		if (is == null) {
			throw new FileNotFoundException(fileName + " could not be found");
		}
		return is;
	}
}
