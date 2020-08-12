package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.nio.ByteBuffer;

public class SPIRVLiteralString implements SPIRVOperand {
    private final String value;

    public SPIRVLiteralString(String value) {
        this.value = value;
    }

    @Override
    public void write(ByteBuffer output) {
        for (byte b : value.getBytes()) {
            output.put(b);
        }
        int alignRemaining = 4 - value.length() % 4;
        for (int i = 0; i < alignRemaining; i++) {
            output.put((byte) 0);
        }
    }

    @Override
    public int getWordCount() {
        return (value.length() / 4) + 1;
    }
}
