package gov.usdot.cv.msg.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.Base64;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import gov.usdot.cv.msg.builder.input.KmlConversionResponse;
import gov.usdot.cv.msg.builder.input.RsmFile;
import gov.usdot.cv.msg.builder.input.RsmInputData;
import gov.usdot.cv.msg.builder.util.JSONMapper;
import gov.usdot.cv.msg.rsmparser.RsmParser;
import gov.usdot.cv.msg.rsm.RoadsideSafetyMessage;
import gov.usdot.cv.msg.rsmconverter.RsmKmlGenerator;

/**
 * JAX-RS endpoint for handling RSM file ingestion for the ISD/TIM tool
 * <p>
 * Converts CAMP RoadsideSafetyMessage formatted as XML into a KML stencil renderable
 * by the OpenLayers map tile engine used on the ISD/TIM tool front end
 */
@Path("/messages/rsm_converter")
public class RsmKmlBuilder {

    /**
     * REST endpoint for handling the conversion of a set of RSM documents into a KML stencil
     * @param jsonRequest The request from the front end containing the user's uploaded RSM file data
     * @return A {@link KmlConversionResponse} object containing an error message if there was a failure,
     * or containing the stitched KML document if the total conversion process was successful.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response convertRsmToKml(String jsonRequest) {
        // Process the input from JSON into our basic POJO object
        RsmInputData inputSet = null;
        try {
            inputSet = JSONMapper.jsonStringToPojo(jsonRequest, RsmInputData.class);

            // Validate input and throw HTTP 400 - BAD REQUEST if the input is invalid
            if (inputSet == null) {
                return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new KmlConversionResponse(false, 
                        "Unable to parse request object due to mapping failure", 
                        ""))
                    .build();
            }

            if (inputSet.getFiles() == null 
                || inputSet.getFiles().size() == 0) {
                return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new KmlConversionResponse(false, 
                        "No files provided for parsing", 
                        ""))
                    .build();
            }
        } catch (IOException e) {
                return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new KmlConversionResponse(false, 
                        "Unable to parse request object due to IO exception", 
                        ""))
                    .build();
        }

        // Parse RSMs into Java objects
        RsmParser rsmParser = new RsmParser();
        List<RoadsideSafetyMessage> parsedRsms = new ArrayList<>();
        for (RsmFile file : inputSet.getFiles()) {
            if (file == null || file.getFilename() == null || file.getText() == null) {
                return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new KmlConversionResponse(false, 
                        "Improperly formatted request object, file element not properly structured", 
                        ""))
                    .build();
            }

            try {
                byte[] bytes = Base64.getDecoder().decode(file.getText());
                String decodedRsmString = new String(bytes);
                RoadsideSafetyMessage rsm = rsmParser.parseRsmGeometry(decodedRsmString);
                parsedRsms.add(rsm);
            } catch (DataFormatException dfe) {
                return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new KmlConversionResponse(false, 
                        "Error in file " + file.getFilename() + dfe.getMessage(), 
                        ""))
                    .build();
            }
        }

        if (parsedRsms.size() < 1) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new KmlConversionResponse(false, 
                    "No valid RSMs for conversion", 
                    ""))
                .build();
        }

        // Convert to KML
        String kmlString = RsmKmlGenerator.convertRsmToKmlString(parsedRsms);

        // Return with an HTTP 200 code for success
        return Response
            .ok(new KmlConversionResponse(true, "", kmlString))
            .build();
    }
}
