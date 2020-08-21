package uk.ac.manchester.spirvproto.lib.instructions.operands;

import uk.ac.manchester.spirvproto.lib.instructions.SPIRVInstruction;

public abstract class SPIRVGlobal extends SPIRVInstruction {
    protected SPIRVGlobal(int opCode, int wordCount, String name, SPIRVCapability... capabilities) {
        super(opCode, wordCount, name, capabilities);
    }
}
