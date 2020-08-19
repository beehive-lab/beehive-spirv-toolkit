package uk.ac.manchester.spirvproto.lib;

import uk.ac.manchester.spirvproto.lib.instructions.SPIRVInstruction;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.util.function.Consumer;

public interface SPIRVInstScope {
    SPIRVInstScope add(SPIRVInstruction instruction);
    SPIRVId getOrCreateId(String name);
    SPIRVIdGenerator getIdGen();
    void forEachInstruction(Consumer<SPIRVInstruction> instructionConsumer);
    SPIRVInstruction getInstruction(SPIRVId id);
    SPIRVId getOrAddId(int id);
}
