package uk.ac.manchester.spirvproto.lib;

import java.io.IOException;
import java.io.InputStream;

public class SPVByteStreamReader implements BinaryWordStream {

    private final InputStream input;
    private boolean littleEndian;

    public SPVByteStreamReader(InputStream input) {
        this.input = input;
        littleEndian = true;
    }

    @Override
    public int getNextWord() {
        byte[] bytes = new byte[4];
        try {
            input.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        int word = 0;
        if (littleEndian) {
            word |= bytes[3];
            word <<= 8;
            word |= bytes[2];
            word <<= 8;
            word |= bytes[1];
            word <<= 8;
            word |= bytes[0];
        }
        else {
            word |= bytes[0];
            word <<= 8;
            word |= bytes[1];
            word <<= 8;
            word |= bytes[2];
            word <<= 8;
            word |= bytes[3];
        }
        return word;
    }

    @Override
    public void changeEndianness() {
        littleEndian = !littleEndian;
    }
}
