package uk.ac.manchester.spirvproto.lib.instructions;

public abstract class SPIRVImportInst extends SPIRVInstruction {
    protected SPIRVImportInst(int opCode, int wordCount, String name) {
        super(opCode, wordCount, name);
    }
}
