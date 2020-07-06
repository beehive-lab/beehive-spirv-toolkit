package uk.ac.manchester.spirvproto.lib;

public interface BinaryWordStream {
    int getNextWord();
    void changeEndianness();
}
