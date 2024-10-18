package gov.usdot.cv.googlemap.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleElevationResponse {
    private double elevation;
    private GoogleLocation location;
    private double resolution;

    public GoogleElevationResponse() {
    }


    public GoogleElevationResponse(double elevation, GoogleLocation location, double resolution) {
        this.elevation = elevation;
        this.location = location;
        this.resolution = resolution;
    }
    public double getElevation() {
        return this.elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public GoogleLocation getLocation() {
        return this.location;
    }

    public void setLocation(GoogleLocation location) {
        this.location = location;
    }

    public double getResolution() {
        return this.resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return "{" +
            " elevation='" + getElevation() + "'" +
            ", location='" + getLocation() + "'" +
            ", resolution='" + getResolution() + "'" +
            "}";
    }

}
