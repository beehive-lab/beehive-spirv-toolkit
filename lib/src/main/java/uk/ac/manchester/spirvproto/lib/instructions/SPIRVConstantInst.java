package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVConstantInst extends SPIRVInstruction {
    protected SPIRVConstantInst(int opCode, int wordCount) {
        super(opCode, wordCount);
    }
}
