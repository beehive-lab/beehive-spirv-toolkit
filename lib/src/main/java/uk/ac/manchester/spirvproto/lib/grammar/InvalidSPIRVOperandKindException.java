package uk.ac.manchester.spirvproto.lib.grammar;

public class InvalidSPIRVOperandKindException extends Exception {
    public InvalidSPIRVOperandKindException(String operandKind) {
        super("Invalid operand kind: " + operandKind);
    }
}
