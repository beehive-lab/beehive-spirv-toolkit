package uk.ac.manchester.spirvproto.runner;

import uk.ac.manchester.spirvproto.lib.SPIRVTool;

public class Configuration {
    public final boolean debug;
    public final SPIRVTool tool;

    public Configuration(boolean debug, SPIRVTool tool) {
        this.debug = debug;
        this.tool = tool;
    }
}
