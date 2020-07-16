package uk.ac.manchester.spirvproto.lib.grammar;

public class InvalidSPIRVOpcodeException extends Exception {
    public InvalidSPIRVOpcodeException(int opcode) {
        super("No operations exist with opcode: " + opcode);
    }
}
