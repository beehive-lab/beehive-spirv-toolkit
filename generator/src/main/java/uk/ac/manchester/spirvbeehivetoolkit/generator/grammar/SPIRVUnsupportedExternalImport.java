package uk.ac.manchester.spirvbeehivetoolkit.generator.grammar;

public class SPIRVUnsupportedExternalImport extends Exception {
    public SPIRVUnsupportedExternalImport(String name) {
        super("Unsupported external import: " + name);
    }
}
