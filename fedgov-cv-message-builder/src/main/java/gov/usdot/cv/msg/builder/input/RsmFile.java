package gov.usdot.cv.msg.builder.input;

/**
 * Representation of a single RSM file for conversion
 * <p>
 * Part of the {@link RsmInputData} request message
 */
public class RsmFile {
    private String filename;
    private String text;

    public RsmFile() {
        filename = "";
        text = "";
    }

    /**
     * Get the name of the file this data orignated from
     * @return The filename as a String
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Set the name of the file this data orignated from
     * @param filename The name of the file on the user's hard drive that this data originates from
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Get the textual content (in XML form) of the RSM file being converted
     * @return The Base64-encoded text content of the RSM file as a String
     */
    public String getText() {
        return text;
    }

    /**
     * Set the textual content (in XML form) of the RSM file being converted
     * @param text The Base64-encoded text content of the RSM file as a String
     */
    public void setText(String text) {
        this.text = text;
    }
}
