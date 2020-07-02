package uk.ac.manchester.spirvtool.runner;

public class Configuration {
    public final boolean debug;
    public final String fileName;

    public Configuration(boolean debug, String fileName) {
        this.debug = debug;
        this.fileName = fileName;
    }
}
