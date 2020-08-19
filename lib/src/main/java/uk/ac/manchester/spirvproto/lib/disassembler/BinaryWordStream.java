package uk.ac.manchester.spirvproto.lib.disassembler;

import java.io.IOException;
import java.nio.ByteOrder;

public interface BinaryWordStream {
    int getNextWord() throws IOException;
    byte[] getNextWordInBytes() throws IOException;
    byte[] getNextWordInBytes(boolean reverse) throws IOException;

    void setEndianness(ByteOrder endianness);
    ByteOrder getEndianness();
}
