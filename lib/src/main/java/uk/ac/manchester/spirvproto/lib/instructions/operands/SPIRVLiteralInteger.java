package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.nio.ByteBuffer;

public class SPIRVLiteralInteger implements SPIRVOperand {
    private final int value;

    public SPIRVLiteralInteger(int value) {
        this.value = value;
    }

    @Override
    public void write(ByteBuffer output) {
        output.putInt(value);
    }

    @Override
    public int getWordCount() {
        return 1;
    }
}
