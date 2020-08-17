package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.SPIRVInstruction;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVTypeInst;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.util.function.Consumer;

public interface SPIRVInstScope {
    SPIRVInstScope add(SPIRVInstruction instruction);
    SPIRVId getOrCreateId(String name);
    SPIRVIdGenerator getIdGen();
    void forEachInstruction(Consumer<SPIRVInstruction> instructionConsumer);

    SPIRVInstruction getInstruction(SPIRVId id);
}
