package gov.usdot.cv.googlemap.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleLocation {
    @JsonProperty("lat")
    @JsonAlias("latitude")
    private double lat;
    
    @JsonProperty("lng")
    @JsonAlias("longitude")
    private double lng;

    public GoogleLocation() {
    }

    public GoogleLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "{" +
            " lat='" + getLat() + "'" +
            ", lng='" + getLng() + "'" +
            "}";
    }
}