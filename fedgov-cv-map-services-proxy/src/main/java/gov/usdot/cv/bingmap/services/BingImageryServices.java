package gov.usdot.cv.bingmap.services;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.usdot.cv.bingmap.models.BingImageryMetadata;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Service
public class BingImageryServices {

    private static final String URL_BASE ="https://dev.virtualearth.net/REST/v1/Imagery/Metadata/Aerial/";
    private static final String SCHEMA="https";
    private Logger logger = LogManager.getLogger(BingImageryServices.class);

    private String composeFullURL(String latitude, String longitude, String zoomLevel, String session_key){
        String fullURL = URL_BASE + latitude + "," + longitude + "?uriScheme="+SCHEMA+"&zl=" + zoomLevel;
        fullURL += "&key=" + session_key;
        return fullURL;
    }

    public BingImageryMetadata getImageryMetadata(String latitude, String longitude, String zoomLevel, String session_key){
        String fullURL = composeFullURL(latitude, longitude, zoomLevel,session_key);
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
        .url(fullURL)
        .build();
        String respponse = "{\"authenticationResultCode\":\"ValidCredentials\",\"brandLogoUri\":\"https://dev.virtualearth.net/Branding/logo_powered_by.png\",\"copyright\":\"Copyright © 2024 Microsoft and its suppliers. All rights reserved. This API cannot be accessed and the content and any results may not be used, reproduced or transmitted in any manner without express written permission from Microsoft Corporation.\",\"resourceSets\":[{\"estimatedTotal\":1,\"resources\":[{\"__type\":\"ImageryMetadata:http://schemas.microsoft.com/search/local/ws/rest/v1\",\"imageHeight\":256,\"imageUrl\":\"https://ecn.t1.tiles.virtualearth.net/tiles/a030223203133020211.jpeg?g=14731\",\"imageUrlSubdomains\":null,\"imageWidth\":256,\"imageryProviders\":null,\"vintageEnd\":\"04 Oct 2022 GMT\",\"vintageStart\":\"08 Sep 2022 GMT\",\"zoomMax\":18,\"zoomMin\":18}]}],\"statusCode\":200,\"statusDescription\":\"OK\",\"traceId\":\"6483e3ddf049409e2b7204a11f69c140\"}";
        ObjectMapper objMapper = new ObjectMapper();
        BingImageryMetadata medata;
        try {
            JsonNode node = objMapper.readTree(respponse);
            medata = objMapper.readValue(node.get("resourceSets").get(0).get("resources").get(0).toString(), BingImageryMetadata.class);
            logger.info("Successfully get imagery metadata by calling: " + fullURL);
            logger.info("Response: "+ medata.toString());
            return medata;

            // Response response = client.newCall(req).execute();
            // if(response.isSuccessful()){
            //     logger.info("Successfully get imagery metadata by calling: " + fullURL);
            //     logger.info(response.body().string());
            //     JsonNode node = objMapper.readTree(respponse);
            //     medata = objMapper.readValue(node.get("resourceSets").get(0).get("resources").get(0).toString(), ImageryMetadata.class);
            //     return medata;
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
