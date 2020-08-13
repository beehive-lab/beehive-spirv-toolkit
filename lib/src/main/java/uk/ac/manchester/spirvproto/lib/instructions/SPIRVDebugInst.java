package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVDebugInst extends SPIRVInstruction {
    protected SPIRVDebugInst(int opCode, int wordCount, String name) {
        super(opCode, wordCount, name);
    }
}
