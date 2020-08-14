package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVFunctionControl;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SPIRVFunctionDefinition extends SPIRVFunctionDeclaration implements SPIRVInstScope {
    private final List<SPIRVBlock> blocks;
    private final SPIRVInstScope enclosingScope;
    private final SPIRVIdGenerator idGen;

    public SPIRVFunctionDefinition(SPIRVId resultType,
                                   SPIRVId funcType,
                                   SPIRVId result,
                                   SPIRVFunctionControl control,
                                   SPIRVIdGenerator idGen,
                                   SPIRVFunctionParameterInst... params) {
        super(resultType, funcType, result, control, params);
        this.idGen = idGen;
        blocks = new ArrayList<>();
        enclosingScope = null;
    }

    public SPIRVFunctionDefinition(SPIRVFunctionInst instruction, SPIRVInstScope enclosingScope) {
        super(instruction);
        this.enclosingScope = enclosingScope;
        idGen = enclosingScope.getIdGen();
        blocks = new ArrayList<>();
    }

    public SPIRVBlock addBlock() {
        SPIRVBlock newBlock = new SPIRVBlock(idGen);
        blocks.add(newBlock);
        return newBlock;
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

}
