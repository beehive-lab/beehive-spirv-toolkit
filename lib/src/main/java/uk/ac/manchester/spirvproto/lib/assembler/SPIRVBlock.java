package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.SPIRVInstruction;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVLabelInst;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVTerminationInst;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SPIRVBlock implements SPIRVInstScope {
    private final SPIRVLabelInst label;
    private final SPIRVIdGenerator idGen;
    private final SPIRVInstScope enclosingScope;
    private final List<SPIRVInstruction> instructions;
    private SPIRVTerminationInst end;

    public SPIRVBlock(SPIRVLabelInst instruction, SPIRVInstScope enclosingScope) {
        label = instruction;
        this.enclosingScope = enclosingScope;
        this.idGen = enclosingScope.getIdGen();
        instructions = new ArrayList<>();
    }

    @Override
    public SPIRVInstScope add(SPIRVInstruction instruction) {
        if (instruction instanceof SPIRVTerminationInst) {
            end = (SPIRVTerminationInst) instruction;
            return enclosingScope;
        }

        instructions.add(instruction);
        return this;
    }

    @Override
    public SPIRVId getOrCreateId(String name) {
        return idGen.getOrCreateId(name);
    }

    @Override
    public SPIRVIdGenerator getIdGen() {
        return idGen;
    }

    @Override
    public void forEachInstruction(Consumer<SPIRVInstruction> instructionConsumer) {
        instructionConsumer.accept(label);
        instructions.forEach(instructionConsumer);
        instructionConsumer.accept(end);
    }

}
