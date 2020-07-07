package uk.ac.manchester.spirvproto.lib;

import java.io.IOException;

public interface BinaryWordStream {
    int getNextWord() throws IOException;
    void changeEndianness();
    byte[] getNextWordInBytes() throws IOException;
    byte[] getNextWordInBytes(boolean reverse) throws IOException;
}
