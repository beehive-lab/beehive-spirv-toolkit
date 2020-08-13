package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public class SPIRVId implements SPIRVOperand {
    private final int id;

    public SPIRVId(int id) {
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

    @Override
    public void print(PrintStream output) {
        output.print("%" + id);
    }
}
