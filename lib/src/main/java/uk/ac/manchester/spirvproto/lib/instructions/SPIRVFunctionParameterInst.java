package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVFunctionParameterInst extends SPIRVInstruction {
    protected SPIRVFunctionParameterInst(int opCode, int wordCount) {
        super(opCode, wordCount);
    }
}
