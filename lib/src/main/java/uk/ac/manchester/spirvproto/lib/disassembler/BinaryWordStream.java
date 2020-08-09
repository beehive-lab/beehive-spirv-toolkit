package uk.ac.manchester.spirvproto.lib.disassembler;

import java.io.IOException;

public interface BinaryWordStream {
    int getNextWord() throws IOException;
    void changeEndianness();
    byte[] getNextWordInBytes() throws IOException;
    byte[] getNextWordInBytes(boolean reverse) throws IOException;

    boolean isLittleEndian();
}
