package uk.ac.manchester.spirvproto.lib.disassembler;

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
    public int getNextWord() throws IOException {
        byte[] bytes = new byte[4];
        int status = input.read(bytes);
        if (status == -1) return -1;

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

    @Override
    public byte[] getNextWordInBytes() throws IOException {
        return getNextWordInBytes(false);
    }

    @Override
    public byte[] getNextWordInBytes(boolean reverse) throws IOException {
        byte[] bytes = new byte[4];
        int status = input.read(bytes);
        if (status == -1) return null;

        // Reorder bytes if on a little endian system
        if ((littleEndian && !reverse) || (!littleEndian && reverse)) {
            byte[] reversed = new byte[4];
            reversed[0] = bytes[3];
            reversed[1] = bytes[2];
            reversed[2] = bytes[1];
            reversed[3] = bytes[0];

            return reversed;
        }

        return bytes;
    }
}
