package uk.ac.manchester.spirvproto.lib.grammar;

public class InvalidSPIRVEnumerantException extends Exception {
    public InvalidSPIRVEnumerantException(String kind, String value) {
        super("Enumerant " + kind + " cannot have value: " + value);
    }
}
