package uk.ac.manchester.spirvproto.runner;

import java.io.File;
import java.io.PrintStream;

public class Configuration {
    public final boolean debug;
    public final File inputFile;
    public final PrintStream output;

    public Configuration(boolean debug, String fileName, PrintStream output) {
        this.debug = debug;
        this.inputFile = new File(fileName);
        this.output = output;
    }
}
