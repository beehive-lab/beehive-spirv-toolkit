package uk.ac.manchester.spirvproto.generator.grammar;

public class InvalidSPIRVOperandKindException extends Exception {
    public InvalidSPIRVOperandKindException(String operandKind) {
        super("Invalid operand kind: " + operandKind);
    }
}
