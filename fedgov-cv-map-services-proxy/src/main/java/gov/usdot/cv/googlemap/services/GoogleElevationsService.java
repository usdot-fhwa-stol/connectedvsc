package gov.usdot.cv.googlemap.services;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.usdot.cv.googlemap.models.GoogleElevationResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Service
public class GoogleElevationsService {

    private static final String URL_BASE ="https://maps.googleapis.com/maps/api/elevation";
    private static final String TYPE="json";
    private Logger logger = LogManager.getLogger(GoogleElevationsService.class);

    private String composeFullURL(String latitude, String longitude, String api_key){
        String fullURL = URL_BASE+ TYPE +"?locations="+ latitude + ',' + longitude;
        fullURL += "&key=" + api_key;
        return fullURL;
    }

     public GoogleElevationResponse getElevation(String latitude, String longitude, String api_key){
        String fullURL = composeFullURL(latitude, longitude, api_key);
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
        .url(fullURL)
        .build();
        String respponse ="{\"results\":[{\"elevation\":1608.637939453125,\"location\":{\"lat\":39.7391536,\"lng\":-104.9847034},\"resolution\":4.771975994110107}],\"status\":\"OK\"}";
        ObjectMapper objMapper = new ObjectMapper();
        GoogleElevationResponse elevationData;
        try {
            logger.info("Elevation call: " + fullURL);
            JsonNode node = objMapper.readTree(respponse);
            logger.info(node.toString());
            elevationData = objMapper.readValue(node.get("results").get(0).toString(), GoogleElevationResponse.class);
            logger.info("Response: "+ elevationData.toString());
            return elevationData;
            

            // Response response = client.newCall(req).execute();
            // logger.info("Elevation call: " + fullURL);
            // if(response.isSuccessful()){
            //     logger.info("Successfully get elevation by calling: " + fullURL);
            //     logger.info(response.body().string());
            //     JsonNode node = objMapper.readTree(respponse);
            //     elevationData = objMapper.readValue(node.get("resourceSets").get(0).get("resources").get(0).toString(), ElevationData.class);
            //     return elevationData;
            // }else{
            //     logger.info("Failed to call " + fullURL + ". Detailed error: "+ response);
            // }
        }
        catch (JsonProcessingException ex) {
            logger.info("Failed to parse response body: " + fullURL + ". Detailed error: "+ ex.getMessage());
        }
        catch(IOException ex){
            logger.info("Failed to call " + fullURL + ". Detailed error: "+ ex.getMessage());
        }
        return null;

    }

}
