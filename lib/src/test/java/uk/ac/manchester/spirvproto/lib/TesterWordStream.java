package uk.ac.manchester.spirvproto.lib;

import uk.ac.manchester.spirvproto.lib.disassembler.BinaryWordStream;

import java.nio.ByteOrder;

public class TesterWordStream implements BinaryWordStream {
    private final int[] words;
    private int position;

    public TesterWordStream(int[] words) {
        // Prepend header
        this.words = new int[words.length + 5];
        this.words[0] = 0x07230203;
        this.words[1] = 0x00010000;
        this.words[2] = 6;
        this.words[3] = 37;
        this.words[4] = 0;
        System.arraycopy(words, 0, this.words, 5, words.length);

        position = 0;
    }

    @Override
    public int getNextWord() {
        return words[position++];
    }

    @Override
    public byte[] getNextWordInBytes() {
        return getNextWordInBytes(false);
    }

    @Override
    public byte[] getNextWordInBytes(boolean reverse) {
        int nextWord = words[position++];
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (0xFF & nextWord);
        bytes[1] = (byte) (0xFF & (nextWord >> 8));
        bytes[2] = (byte) (0xFF & (nextWord >> 16));
        bytes[3] = (byte) (0xFF & (nextWord >> 24));

        return bytes;
    }

    @Override
    public void setEndianness(ByteOrder endianness) {

    }

}
