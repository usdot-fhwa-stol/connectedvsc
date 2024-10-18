package gov.usdot.cv.bingmap.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/***
 * @brief Definition of ImageryMetadata refer to: https://learn.microsoft.com/en-us/bingmaps/rest-services/imagery/imagery-metadata
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BingImageryMetadata{
   private String imageUrl;
   private String imageUrlSubdomains;
   private String imageWidth;
   private String imageHeight;
   private String vintageStart;
   private String vintageEnd;
   private String zoomMin;
   private String zoomMax;

   public BingImageryMetadata(){}
   public BingImageryMetadata(String imageUrl, String imageUrlSubdomains, String imageWidth, String imageHeight, String vintageStart, String vintageEnd, String zoomMin, String zoomMax )
   {
        this.imageUrl = imageUrl;
        this.imageUrlSubdomains = imageUrlSubdomains;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.vintageStart = vintageStart;
        this.vintageEnd = vintageEnd;
        this.zoomMin = zoomMin;
        this.zoomMax = zoomMax;
   }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlSubdomains() {
        return this.imageUrlSubdomains;
    }

    public void setImageUrlSubdomains(String imageUrlSubdomains) {
        this.imageUrlSubdomains = imageUrlSubdomains;
    }

    public String getImageWidth() {
        return this.imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageHeight() {
        return this.imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getVintageStart() {
        return this.vintageStart;
    }

    public void setVintageStart(String vintageStart) {
        this.vintageStart = vintageStart;
    }

    public String getVintageEnd() {
        return this.vintageEnd;
    }

    public void setVintageEnd(String vintageEnd) {
        this.vintageEnd = vintageEnd;
    }

    public String getZoomMin() {
        return this.zoomMin;
    }

    public void setZoomMin(String zoomMin) {
        this.zoomMin = zoomMin;
    }

    public String getZoomMax() {
        return this.zoomMax;
    }

    public void setZoomMax(String zoomMax) {
        this.zoomMax = zoomMax;
    }
   

    @Override
    public String toString() {
        return "{" +
            " imageUrl='" + getImageUrl() + "'" +
            ", imageUrlSubdomains='" + getImageUrlSubdomains() + "'" +
            ", imageWidth='" + getImageWidth() + "'" +
            ", imageHeight='" + getImageHeight() + "'" +
            ", vintageStart='" + getVintageStart() + "'" +
            ", vintageEnd='" + getVintageEnd() + "'" +
            ", zoomMin='" + getZoomMin() + "'" +
            ", zoomMax='" + getZoomMax() + "'" +
            "}";
    }


}