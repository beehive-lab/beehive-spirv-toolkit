package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.SPIRVFunctionParameterInst;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVFunctionControl;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SPIRVFunctionDefinition extends SPIRVFunctionDeclaration {
    private final List<SPIRVBlock> blocks;

    private final SPIRVIdGenerator idGen;

    public SPIRVFunctionDefinition(SPIRVId resultType, SPIRVId funcType, SPIRVId result, SPIRVFunctionControl control, SPIRVIdGenerator idGen, SPIRVFunctionParameterInst... params) {
        super(resultType, funcType, result, control, params);
        this.idGen = idGen;
        blocks = new ArrayList<>();
    }

    public SPIRVBlock addBlock() {
        SPIRVBlock newBlock = new SPIRVBlock(idGen);
        blocks.add(newBlock);
        return newBlock;
    }

    @Override
    public int getWordCount() {
        return super.getWordCount() + blocks.stream().mapToInt(SPIRVBlock::getWordCount).sum();
    }

    @Override
    public void write(ByteBuffer output) {
        functionDeclaration.write(output);
        parameters.forEach(p -> p.write(output));
        blocks.forEach(b -> b.write(output));
        end.write(output);
    }
}
