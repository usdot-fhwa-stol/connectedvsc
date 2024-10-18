package gov.usdot.cv.googlemap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.usdot.cv.googlemap.models.GoogleElevationResponse;
import gov.usdot.cv.googlemap.models.GoogleMapProperties;
import gov.usdot.cv.googlemap.services.GoogleElevationsService;

@RestController
@RequestMapping("/googlemap/api")
public class GoogleMapServicesController {
    @Autowired
    GoogleElevationsService elevationsService;

    @Autowired
    GoogleMapProperties googleMapProp;

    @GetMapping("/elevation/{latitude}/{longitude}")
    public ResponseEntity<GoogleElevationResponse> getElevation(@PathVariable String latitude, @PathVariable String longitude){
        GoogleElevationResponse eData = elevationsService.getElevation(latitude, longitude, googleMapProp.get_api_key());
        if(eData==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eData, HttpStatus.OK);
    }
}
