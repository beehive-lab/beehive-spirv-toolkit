package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVEntryPointInst extends SPIRVInstruction{
    protected SPIRVEntryPointInst(int opCode, int wordCount) {
        super(opCode, wordCount);
    }
}
