package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVNameInst extends SPIRVDebugInst {
    protected SPIRVNameInst(int opCode, int wordCount, String name) {
        super(opCode, wordCount, name);
    }
}
