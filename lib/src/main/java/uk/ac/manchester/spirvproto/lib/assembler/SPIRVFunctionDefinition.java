package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.SPIRVFunctionParameterInst;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVFunctionControl;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVIdRef;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVIdResultType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SPIRVFunctionDefinition extends SPIRVFunctionDeclaration {
    private final List<SPIRVBlock> blocks;

    public SPIRVFunctionDefinition(SPIRVIdResultType resultType, SPIRVIdRef funcType, SPIRVFunctionControl control, SPIRVFunctionParameterInst... params) {
        super(resultType, funcType, control, params);
        blocks = new ArrayList<>();
    }

    public SPIRVBlock addBlock() {
        SPIRVBlock newBlock = new SPIRVBlock();
        blocks.add(newBlock);
        return newBlock;
    }

    @Override
    public void write(ByteBuffer output) {
        functionDeclaration.write(output);
        parameters.forEach(p -> p.write(output));
        blocks.forEach(b -> b.write(output));
        end.write(output);
    }
}
