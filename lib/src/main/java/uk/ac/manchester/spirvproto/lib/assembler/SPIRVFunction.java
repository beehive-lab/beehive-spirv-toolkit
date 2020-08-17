package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SPIRVFunction implements SPIRVInstScope {
    protected final List<SPIRVBlock> blocks;
    protected final SPIRVInstScope enclosingScope;
    protected final SPIRVIdGenerator idGen;
    protected SPIRVFunctionInst functionDeclaration;
    protected List<SPIRVFunctionParameterInst> parameters;
    protected SPIRVFunctionEndInst end;

    public SPIRVFunction(SPIRVFunctionInst instruction, SPIRVInstScope enclosingScope) {
        functionDeclaration = instruction;
        parameters = new ArrayList<>();
        blocks = new ArrayList<>();
        this.enclosingScope = enclosingScope;
        idGen = enclosingScope.getIdGen();
    }

    @Override
    public SPIRVInstScope add(SPIRVInstruction instruction) {
        if (instruction instanceof SPIRVFunctionParameterInst) {
            parameters.add((SPIRVFunctionParameterInst) instruction);
            return this;
        } else if (instruction instanceof SPIRVFunctionEndInst) {
            end = (SPIRVFunctionEndInst) instruction;
            return enclosingScope;
        }
        else if (instruction instanceof SPIRVLabelInst) {
            SPIRVBlock newBlock = new SPIRVBlock((SPIRVLabelInst) instruction, this);
            blocks.add(newBlock);
            return newBlock;
        }
        throw new IllegalArgumentException(instruction.getClass().getSimpleName() + " is not a valid instruction in a function level scope");
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
        instructionConsumer.accept(functionDeclaration);
        parameters.forEach(instructionConsumer);
        blocks.forEach(b -> b.forEachInstruction(instructionConsumer));
        instructionConsumer.accept(end);
    }

    public boolean hasBlocks() {
        return blocks.size() > 0;
    }
}
