package gov.usdot.cv.googlemap.models;

public class GoogleLocation {
    private double lat;
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