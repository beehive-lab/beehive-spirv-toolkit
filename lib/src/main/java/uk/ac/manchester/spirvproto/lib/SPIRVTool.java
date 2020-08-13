package uk.ac.manchester.spirvproto.lib;

import uk.ac.manchester.spirvproto.lib.assembler.InvalidSPIRVModuleException;

public interface SPIRVTool {
    void run() throws Exception, InvalidSPIRVModuleException;
}
