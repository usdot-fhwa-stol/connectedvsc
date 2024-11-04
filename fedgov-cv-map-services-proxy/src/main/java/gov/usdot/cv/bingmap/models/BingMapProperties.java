package gov.usdot.cv.bingmap.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BingMapProperties {
    @Value("${bing.map.api.key}")
    private String BING_MAP_API_KEY;

    public String get_api_key(){
        return this.BING_MAP_API_KEY;
    }
}
