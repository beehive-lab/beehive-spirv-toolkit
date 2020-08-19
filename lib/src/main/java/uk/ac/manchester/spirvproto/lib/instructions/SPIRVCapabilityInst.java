package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVCapabilityInst extends SPIRVInstruction {
    protected SPIRVCapabilityInst(int opCode, int wordCount, String name) {
        super(opCode, wordCount, name);
    }
}
