package uk.ac.manchester.spirvproto.lib.disassembler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
        return ByteBuffer
                .wrap(bytes)
                .order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN)
                .getInt();
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
    public boolean isLittleEndian() {
        return littleEndian;
    }
}
