package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVSourceInst extends SPIRVInstruction {
    protected SPIRVSourceInst(int opCode, int wordCount) {
        super(opCode, wordCount);
    }
}
