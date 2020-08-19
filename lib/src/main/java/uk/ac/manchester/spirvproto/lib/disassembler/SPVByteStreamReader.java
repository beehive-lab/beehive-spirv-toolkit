package uk.ac.manchester.spirvproto.lib.disassembler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SPVByteStreamReader implements BinaryWordStream {

    private final InputStream input;
    private ByteOrder endianness;

    public SPVByteStreamReader(InputStream input) {
        this.input = input;
        endianness = ByteOrder.LITTLE_ENDIAN;
    }

    @Override
    public int getNextWord() throws IOException {
        byte[] bytes = new byte[4];
        int status = input.read(bytes);
        if (status == -1) return -1;
        return ByteBuffer
                .wrap(bytes)
                .order(endianness)
                .getInt();
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
        if (reverse) {
            byte[] reversed = new byte[4];
            reversed[0] = bytes[3];
            reversed[1] = bytes[2];
            reversed[2] = bytes[1];
            reversed[3] = bytes[0];

            return reversed;
        }

        return bytes;
    }

    @Override
    public void setEndianness(ByteOrder endianness) {
        this.endianness = endianness;
    }

    @Override
    public ByteOrder getEndianness() {
        return endianness;
    }
}
