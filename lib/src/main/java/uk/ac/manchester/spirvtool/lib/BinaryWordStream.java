package uk.ac.manchester.spirvtool.lib;

public interface BinaryWordStream {
    int getNextWord();
    void changeEndianness();
}
