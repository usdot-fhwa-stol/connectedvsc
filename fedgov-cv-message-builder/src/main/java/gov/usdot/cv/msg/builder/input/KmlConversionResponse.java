package gov.usdot.cv.msg.builder.input;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Response object returned on successul or unsuccessful KML conversion of an RSM document(s)
 */
@XmlRootElement
public class KmlConversionResponse {
    private boolean successful;
    private String errorMessage;
    private String kmlDocument;

    public KmlConversionResponse() {
        successful = false;
        errorMessage = "Uninitialized response, system error";
        kmlDocument = "";
    }

    public KmlConversionResponse(boolean successful, String errorMessage, String kmlDocument) {
        this.successful = successful;
        this.errorMessage = errorMessage;
        this.kmlDocument = kmlDocument;
    }

    /**
     * Get the success status of the conversion attempt
     * @return True if the conversion was successful, false o.w.
     */
    public boolean isSuccessful() {
        return this.successful;
    }

    /**
     * Get the success status of the conversion attempt
     * @return True if the conversion was successful, false o.w.
     */
    public boolean getSuccessful() {
        return this.successful;
    }

    /**
     * Set the success status of the conversion attempt
     * @param successful True if the conversion was successful, false o.w.
     */
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    /**
     * Get the error message to be returned in the response. Should be left empty if the conversion was successful.
     * @return The error message stored in the response
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Set the error message to be returned in the response. Should be left empty if the conversion was successful.
     * @param errorMessage The error message to be stored in the response
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Get the converted KML document to be returned in the response. Should be left empty if the conversion was unsuccessful.
     * @return The converted KML document stored in the response
     */
    public String getKmlDocument() {
        return this.kmlDocument;
    }

    /**
     * Set the converted KML document to be returned in the response. Should be left empty if the conversion was unsuccessful.
     * @param kmlDocument The converted KML document to be stored in the response
     */
    public void setKmlDocument(String kmlDocument) {
        this.kmlDocument = kmlDocument;
    }
}