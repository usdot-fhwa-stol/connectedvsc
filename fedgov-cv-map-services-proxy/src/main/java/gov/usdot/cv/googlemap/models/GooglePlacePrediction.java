package gov.usdot.cv.googlemap.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GooglePlacePrediction {
    private GoogleText text;

    public GooglePlacePrediction(GoogleText text) {
        this.text = text;
    }

    public GooglePlacePrediction() {
    }

    public GoogleText getText() {
        return this.text;
    }

    public void setText(GoogleText text) {
        this.text = text;
    }
    
}
