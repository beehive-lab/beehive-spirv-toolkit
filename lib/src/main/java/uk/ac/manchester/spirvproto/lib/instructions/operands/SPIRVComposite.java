package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.nio.ByteBuffer;

public class SPIRVComposite implements SPIRVOperand {
    @Override
    public void write(ByteBuffer output) {
        throw new RuntimeException("Composite types are not implemented yet!");
    }

    @Override
    public int getWordCount() {
        throw new RuntimeException("Composite types are not implemented yet!");
    }
}
