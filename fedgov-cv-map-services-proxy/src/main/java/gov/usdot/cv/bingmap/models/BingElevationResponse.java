package gov.usdot.cv.bingmap.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BingElevationResponse {
    private ArrayList<Integer> elevations;
    private ArrayList<Integer> offsets;
    private int zoomLevel;

    public BingElevationResponse(ArrayList<Integer> elevations, ArrayList<Integer> offsets, int zoomLevel) {
        this.elevations = elevations;
        this.offsets = offsets;
        this.zoomLevel = zoomLevel;
    }

    public ArrayList<Integer> getElevations() {
        return this.elevations;
    }

    public void setElevations(ArrayList<Integer> elevations) {
        this.elevations = elevations;
    }

    public ArrayList<Integer> getOffsets() {
        return this.offsets;
    }

    public void setOffsets(ArrayList<Integer> offsets) {
        this.offsets = offsets;
    }

    public int getZoomLevel() {
        return this.zoomLevel;
    }

    public void setZoomLevel(int zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public BingElevationResponse() {
    }

    @Override
    public String toString() {
        return "{" +
            " elevations='" + getElevations() + "'" +
            ", offsets='" + getOffsets() + "'" +
            ", zoomLevel='" + getZoomLevel() + "'" +
            "}";
    }
}
