package gov.usdot.cv.googlemap.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleSearchTextResponse {
    private String formattedAddress;
    private GoogleLocation location;

    public GoogleSearchTextResponse() {
    }

    public GoogleSearchTextResponse(String formattedAddress, GoogleLocation location) {
        this.formattedAddress = formattedAddress;
        this.location = location;
    }

    public String getFormattedAddress() {
        return this.formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public GoogleLocation getLocation() {
        return this.location;
    }

    public void setLocation(GoogleLocation location) {
        this.location = location;
    }

    public GoogleSearchTextResponse formattedAddress(String formattedAddress) {
        setFormattedAddress(formattedAddress);
        return this;
    }

    public GoogleSearchTextResponse location(GoogleLocation location) {
        setLocation(location);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " formattedAddress='" + getFormattedAddress() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
    
}
