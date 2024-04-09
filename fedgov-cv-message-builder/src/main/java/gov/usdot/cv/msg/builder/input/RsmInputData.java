package gov.usdot.cv.msg.builder.input;

import java.util.ArrayList;
import java.util.List;


/** 
 * Input data class for the RSM conversion process
 * <p>
 * Describes a set of related RSM files (sharing a single reference point) submitted to the 
 * message builder server for conversion to a single KML stencil
 * 
 * Intended to be received from the web front end as JSON so this class is designed to be
 * mappable by Jackson
 */
public class RsmInputData {

    private List<RsmFile> files = new ArrayList<>();

    public RsmInputData() {
    }

    /**
     * Get the RSM file data contained in this request object
     * @return A list of {@link RsmFile} instances uploaded by the user
     */
    public List<RsmFile> getFiles() {
        return files;
    }

    /**
     * Set the RSM file data contained in this request object
     */
    public void setFiles(List<RsmFile> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        String out = "RsmInputData {\n";
        out += "  files: [\n";

        String fileString = "";
        for (RsmFile file : files) {
            fileString += "    {\n      filename: \"" + file.getFilename() + "\",\n";
            fileString += "      text: \"" + file.getText() + "\",\n    },\n";
        }
        // Strip trailing comma
        int lastCommaIdx = fileString.lastIndexOf(",");
        if (lastCommaIdx >= 0) {
            fileString = fileString.substring(0, lastCommaIdx);
        }

        out += fileString + "  ]\n}";
        return out;
    }
}