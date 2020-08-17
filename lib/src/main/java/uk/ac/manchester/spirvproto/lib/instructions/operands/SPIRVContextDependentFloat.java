package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.nio.ByteBuffer;

public class SPIRVContextDependentFloat extends SPIRVLiteralContextDependentNumber {
    private final float value;

    public SPIRVContextDependentFloat(float value) {
        this.value = value;
    }

    @Override
    public void write(ByteBuffer output) {
        output.putFloat(value);
    }

    @Override
    public int getWordCount() {
        return 1;
    }
}
