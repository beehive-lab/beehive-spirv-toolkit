package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public interface SPIRVOperand {
    void write(ByteBuffer output);
    int getWordCount();

    void print(PrintStream output);
}
