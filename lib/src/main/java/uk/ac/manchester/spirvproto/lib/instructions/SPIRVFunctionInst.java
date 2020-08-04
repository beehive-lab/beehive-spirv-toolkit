package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVFunctionInst extends SPIRVInstruction {
    protected SPIRVFunctionInst(int opCode, int wordCount) {
        super(opCode, wordCount);
    }
}
