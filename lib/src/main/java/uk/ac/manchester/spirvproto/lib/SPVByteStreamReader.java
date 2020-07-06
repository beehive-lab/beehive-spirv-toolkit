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
            int status = input.read(bytes);
            if (status == -1) {
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        int word;
        if (littleEndian) {
            word =
                    ((0xFF & bytes[3]) << 24) |
                    ((0xFF & bytes[2]) << 16) |
                    ((0xFF & bytes[1]) << 8) |
                    (0xFF & bytes[0]);
        }
        else {
            word =
                    ((0xFF & bytes[0]) << 24) |
                    ((0xFF & bytes[1]) << 16) |
                    ((0xFF & bytes[2]) << 8) |
                    (0xFF & bytes[3]);
        }
        return word;
    }

    @Override
    public void changeEndianness() {
        littleEndian = !littleEndian;
    }
}
