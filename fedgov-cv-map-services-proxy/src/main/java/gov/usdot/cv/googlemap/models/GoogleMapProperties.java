package gov.usdot.cv.googlemap.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleMapProperties {
    @Value("${google.map.api.key}")
    private String GOOGLE_API_KEY;

    public String get_api_key(){
        return this.GOOGLE_API_KEY;
    }
}
