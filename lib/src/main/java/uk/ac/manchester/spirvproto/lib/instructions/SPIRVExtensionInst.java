package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVExtensionInst extends SPIRVInstruction {
    protected SPIRVExtensionInst(int opCode, int wordCount, String name) {
        super(opCode, wordCount, name);
    }
}
