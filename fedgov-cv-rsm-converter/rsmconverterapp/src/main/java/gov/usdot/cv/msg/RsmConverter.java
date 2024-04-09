/*
 * Copyright (C) 2018-2019 LEIDOS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package gov.usdot.cv.msg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import gov.usdot.cv.msg.rsm.RoadsideSafetyMessage;
import gov.usdot.cv.msg.rsmconverter.RsmKmlGenerator;
import gov.usdot.cv.msg.rsmparser.RsmParser;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Primary entrypoint class for the RSM Converter Application
 * <p>
 * Uses picocli to produce a command line interface that can handle mulitple options
 * and positional parameters
 */
public class RsmConverter implements Runnable {

    @Option(names = { "-o", "--output" }, description = "Write converted KML to the specified file rather than STDOUT")
    private File outputFile;

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "Display help and exit")
    private boolean help;

    @Parameters(arity = "1..*", paramLabel = "FILE(s)", description = "RSM XML file or file segments to convert")
    private File inputFiles[];

    /**
     * Main method of the application
     * <p>
     * Delegates to picocli {@link CommandLine#run(Runnable, String...)}
     * 
     * @param args The standard system command line args variable
     */
    public static void main(String[] args) {
        CommandLine.run(new RsmConverter(), args);
    }

    /**
     * Runnable for primary application behavior
     */
    @Override
    public void run() {
        // If user requests help documentation, print it and exit
        if (help) {
            CommandLine.usage(new RsmConverter(), System.out);
            return;
        }

        // Otherwise proceed to RSM processing
        RsmParser rsmParser = new RsmParser();
        if (inputFiles.length > 1) {
            // Multipart RSM stitching requested, process the files individually initially
            List<RoadsideSafetyMessage> rsmSegments = new ArrayList<>();
            for (File rsmFile : inputFiles) {
                try {
                    rsmSegments.add(rsmParser.parseRsmGeometry(rsmFile));
                } catch (IOException e) {
                    System.err.println("ERROR: Unable to open file: " + rsmFile.getAbsolutePath() + " for parsing.");
                } catch (DataFormatException e) {
                    System.err.println("ERROR: File " + rsmFile.getAbsolutePath() + " format did not match expected RSM format");
                }
            }

            // Determine if file output is requested or if final KML should be dumped to STDOUT
            if (outputFile != null) {
                try {
                    // Have the RSM KML Generator perform the stitching of the RSMs
                    RsmKmlGenerator.convertRsmToKmlFile(rsmSegments, outputFile);
                } catch (IOException e) {
                    System.err.println("ERROR: Unable to open file " + outputFile.getAbsolutePath() + " for writing");
                }
            } else {
                System.out.println(RsmKmlGenerator.convertRsmToKmlString(rsmSegments));
            }
        } else {
            // Only 1 RSM file to process
            try {
                RoadsideSafetyMessage rsm = rsmParser.parseRsmGeometry(inputFiles[0]);
                if (outputFile != null) {
                        RsmKmlGenerator.convertRsmToKmlFile(rsm, outputFile);
                } else {
                    System.out.println(RsmKmlGenerator.convertRsmToKmlString(rsm));
                }
            } catch (IOException e) {
                System.err.println("Unable to open file: " + inputFiles[0].getAbsolutePath() + " for parsing.");
            } catch (DataFormatException e) {
                System.err.println("File " + inputFiles[0].getAbsolutePath() + " format did not match expected RSM format");
            }
        }
    }
}