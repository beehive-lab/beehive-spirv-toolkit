package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.nio.ByteBuffer;

public abstract class SPIRVEnum implements SPIRVOperand{
    protected final int value;

    protected SPIRVEnum(int value) {
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
