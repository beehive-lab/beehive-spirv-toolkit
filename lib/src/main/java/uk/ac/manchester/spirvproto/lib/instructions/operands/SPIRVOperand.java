package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.nio.ByteBuffer;

public interface SPIRVOperand {
    public void write(ByteBuffer output);
    public int getWordCount();
}
