package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVExecutionModeInst extends SPIRVInstruction{
    protected SPIRVExecutionModeInst(int opCode, int wordCount) {
        super(opCode, wordCount);
    }
}
