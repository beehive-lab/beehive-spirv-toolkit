package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVTypeInst extends SPIRVInstruction {
    protected SPIRVTypeInst(int opCode, int wordCount, String name) {
        super(opCode, wordCount, name);
    }
}
