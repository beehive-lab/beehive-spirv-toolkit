package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.nio.ByteBuffer;

public class SPIRVID implements SPIRVOperand {
    private final int id;

    public SPIRVID(int id) {
        this.id = id;
    }

    @Override
    public void write(ByteBuffer output) {
        output.putInt(id);
    }

    @Override
    public int getWordCount() {
        return 1;
    }
}
