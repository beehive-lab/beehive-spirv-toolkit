package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

public class SPIRVIdGenerator {
    private int currentId;

    public SPIRVIdGenerator() {
        currentId = 1;
    }

    public SPIRVId getNextId() {
        return new SPIRVId(currentId++);
    }

    public int getCurrentBound() {
        return currentId;
    }
}
