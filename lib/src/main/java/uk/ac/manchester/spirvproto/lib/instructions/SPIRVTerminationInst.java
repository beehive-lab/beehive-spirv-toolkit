package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVTerminationInst extends SPIRVInstruction {
    protected SPIRVTerminationInst(int opCode, int wordCount) {
        super(opCode, wordCount);
    }
}
