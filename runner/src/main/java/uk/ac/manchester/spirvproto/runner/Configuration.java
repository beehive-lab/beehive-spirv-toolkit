package uk.ac.manchester.spirvproto.runner;

public class Configuration {
    public final boolean debug;
    public final String fileName;

    public Configuration(boolean debug, String fileName) {
        this.debug = debug;
        this.fileName = fileName;
    }
}
