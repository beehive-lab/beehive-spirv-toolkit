package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVMemoryModelInst extends SPIRVInstruction {
    protected SPIRVMemoryModelInst(int opCode, int wordCount, String name) {
        super(opCode, wordCount, name);
    }
}
