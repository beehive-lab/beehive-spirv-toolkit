package uk.ac.manchester.spirvproto.lib.disassembler;

public class SPIRVDecodedOperand {
    public final String operand;
    public final SPIRVOperandCategory category;

    public SPIRVDecodedOperand(String operand, SPIRVOperandCategory category) {
        this.operand = operand;
        this.category = category;
    }
}
