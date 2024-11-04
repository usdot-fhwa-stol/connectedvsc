package gov.usdot.cv.utils;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RequestComposer {
    private final Logger logger = LogManager.getLogger(RequestComposer.class);

    public String composeRequestBodyData(Map<String, String> fieldMap){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        for(Map.Entry<String, String> entry: fieldMap.entrySet()){
            node.put(entry.getKey(), entry.getValue());
        }
        String data = null;
        try {
            data = mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            logger.error("Failed to compose request body data!");
        }
        return data;
    }
}
