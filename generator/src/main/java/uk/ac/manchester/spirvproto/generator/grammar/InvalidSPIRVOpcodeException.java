package uk.ac.manchester.spirvproto.generator.grammar;

public class InvalidSPIRVOpcodeException extends Exception {
    public InvalidSPIRVOpcodeException(int opcode) {
        super("No operations exist with opcode: " + opcode);
    }
}
