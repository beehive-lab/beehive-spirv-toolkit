package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVLabelInst extends SPIRVInstruction {
    protected SPIRVLabelInst(int opCode, int wordCount) {
        super(opCode, wordCount);
    }
}