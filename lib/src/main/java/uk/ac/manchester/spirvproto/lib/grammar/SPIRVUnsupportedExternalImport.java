package uk.ac.manchester.spirvproto.lib.grammar;

public class SPIRVUnsupportedExternalImport extends Exception {
    public SPIRVUnsupportedExternalImport(String name) {
        super("Unsupported external import: " + name);
    }
}
