package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVSourceInst extends SPIRVDebugInst {
    protected SPIRVSourceInst(int opCode, int wordCount) {
        super(opCode, wordCount);
    }
}
