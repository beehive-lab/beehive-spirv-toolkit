package uk.ac.manchester.spirvbeehivetoolkit.runner;

import uk.ac.manchester.spirvbeehivetoolkit.lib.SPIRVTool;

public class Configuration {
    private final boolean debug;
    private final SPIRVTool tool;

    public Configuration(boolean debug, SPIRVTool tool) {
        this.debug = debug;
        this.tool = tool;
    }

    public SPIRVTool getTool() {
        return this.tool;
    }

    public boolean isDebug() {
        return debug;
    }
}
