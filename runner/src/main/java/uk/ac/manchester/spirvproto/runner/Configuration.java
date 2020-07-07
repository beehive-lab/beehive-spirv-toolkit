package uk.ac.manchester.spirvproto.runner;

import java.io.File;

public class Configuration {
    public final boolean debug;
    public final File inputFile;

    public Configuration(boolean debug, String fileName) {
        this.debug = debug;
        this.inputFile = new File(fileName);
    }
}
