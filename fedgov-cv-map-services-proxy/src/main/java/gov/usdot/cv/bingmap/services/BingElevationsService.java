/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gov.usdot.cv.bingmap.services;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.usdot.cv.bingmap.models.BingElevationResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 *
 * @author dev
 */
@Service
public class BingElevationsService {

    private static final String URL_BASE ="https://dev.virtualearth.net/REST/v1/Elevation/List?hts=ellipsoid&points=";
    private static final String HTS="ellipsoid";
    private Logger logger = LogManager.getLogger(BingElevationsService.class);

    private String composeFullURL(String latitude, String longitude, String session_key){
        String fullURL = URL_BASE + latitude + ',' + longitude;
        fullURL += "&key=" + session_key;
        return fullURL;
    }

     public BingElevationResponse getElevation(String latitude, String longitude, String session_key){
        String fullURL = composeFullURL(latitude, longitude, session_key);
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
        .url(fullURL)
        .build();
        String respponse = "{\n" + //
                        "    \"authenticationResultCode\": \"ValidCredentials\",\n" + //
                        "    \"brandLogoUri\": \"https://dev.virtualearth.net/Branding/logo_powered_by.png\",\n" + //
                        "    \"copyright\": \"Copyright © 2024 Microsoft and its suppliers. All rights reserved. This API cannot be accessed and the content and any results may not be used, reproduced or transmitted in any manner without express written permission from Microsoft Corporation.\",\n" + //
                        "    \"resourceSets\": [\n" + //
                        "        {\n" + //
                        "            \"estimatedTotal\": 1,\n" + //
                        "            \"resources\": [\n" + //
                        "                {\n" + //
                        "                    \"__type\": \"ElevationData:http://schemas.microsoft.com/search/local/ws/rest/v1\",\n" + //
                        "                    \"elevations\": [\n" + //
                        "                        148\n" + //
                        "                    ],\n" + //
                        "                    \"zoomLevel\": 14\n" + //
                        "                }\n" + //
                        "            ]\n" + //
                        "        }\n" + //
                        "    ],\n" + //
                        "    \"statusCode\": 200,\n" + //
                        "    \"statusDescription\": \"OK\",\n" + //
                        "    \"traceId\": \"c03fc7ae1593384adec635386af1f2de\"\n" + //
                        "}";
        ObjectMapper objMapper = new ObjectMapper();
        BingElevationResponse elevationData;
        try {
            JsonNode node = objMapper.readTree(respponse);
            elevationData = objMapper.readValue(node.get("resourceSets").get(0).get("resources").get(0).toString(), BingElevationResponse.class);
            logger.info("Successfully get elevation by calling: " + fullURL);
            logger.info("Response: "+ elevationData.toString());
            return elevationData;

            // Response response = client.newCall(req).execute();
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
