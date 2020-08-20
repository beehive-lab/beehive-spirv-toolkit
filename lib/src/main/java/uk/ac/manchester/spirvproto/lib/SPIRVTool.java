package uk.ac.manchester.spirvproto.lib;

/**
 * A tool that processes SPIR-V modules in some format.
 */
public interface SPIRVTool {

    /**
     * Run the tool on the given inputs.
     * @throws Exception
     */
    void run() throws Exception;
}
