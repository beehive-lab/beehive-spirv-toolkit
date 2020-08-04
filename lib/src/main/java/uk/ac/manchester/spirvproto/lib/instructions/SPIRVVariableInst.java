package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVVariableInst extends SPIRVInstruction {
    protected SPIRVVariableInst(int opCode, int wordCount) {
        super(opCode, wordCount);
    }
}
