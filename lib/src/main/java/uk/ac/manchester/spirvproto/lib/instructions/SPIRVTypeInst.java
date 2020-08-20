package uk.ac.manchester.spirvproto.lib.instructions;

import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVGlobal;

public abstract class SPIRVTypeInst extends SPIRVGlobal {
    protected SPIRVTypeInst(int opCode, int wordCount, String name) {
        super(opCode, wordCount, name);
    }
}
