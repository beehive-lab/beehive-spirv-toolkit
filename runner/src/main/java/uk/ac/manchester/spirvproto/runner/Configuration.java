package uk.ac.manchester.spirvproto.runner;

import java.io.File;
import java.io.PrintStream;

public class Configuration {
    public final boolean debug;
    public final File inputFile;
    public final PrintStream output;
    public final boolean inlineNames;

    public Configuration(boolean debug, boolean inlineNames, String fileName, PrintStream output) {
        this.debug = debug;
        this.inlineNames = inlineNames;
        this.inputFile = new File(fileName);
        this.output = output;
    }
}
