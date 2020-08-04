package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVModuleProcessedInst extends SPIRVInstruction {
        protected SPIRVModuleProcessedInst(int opCode, int wordCount) {
        super(opCode, wordCount);
    }
}
