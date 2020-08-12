package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.SPIRVInstruction;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

public interface SPIRVInstScope {
    SPIRVInstScope add(SPIRVInstruction instruction);
    SPIRVId getOrCreateId(String name);
    SPIRVIdGenerator getIdGen();
}
