package uk.ac.manchester.spirvproto.lib.instructions;

import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVGlobal;

public abstract class SPIRVConstantInst extends SPIRVGlobal {
    protected SPIRVConstantInst(int opCode, int wordCount, String name) {
        super(opCode, wordCount, name);
    }
}
