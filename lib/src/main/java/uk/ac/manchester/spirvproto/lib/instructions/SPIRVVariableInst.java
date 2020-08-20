package uk.ac.manchester.spirvproto.lib.instructions;

import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVGlobal;

public abstract class SPIRVVariableInst extends SPIRVGlobal {
    protected SPIRVVariableInst(int opCode, int wordCount, String name) {
        super(opCode, wordCount, name);
    }
}
