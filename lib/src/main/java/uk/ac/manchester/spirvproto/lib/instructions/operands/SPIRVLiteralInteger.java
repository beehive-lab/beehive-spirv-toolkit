package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.io.PrintStream;
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

    @Override
    public void print(PrintStream output) {
        output.print(value);
    }
}
