package uk.ac.manchester.spirvproto.lib.assembler;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SPIRVFunctionDefinition extends SPIRVFunctionDeclaration {
    private final List<SPIRVBlock> blocks;

    public SPIRVFunctionDefinition() {
        blocks = new ArrayList<>();
    }

    @Override
    public void write(ByteBuffer output) {
        functionDeclaration.write(output);
        parameters.forEach(p -> p.write(output));
        blocks.forEach(b -> b.write(output));
        end.write(output);
    }
}
