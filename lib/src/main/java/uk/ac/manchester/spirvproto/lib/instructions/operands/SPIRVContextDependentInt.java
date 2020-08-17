package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class SPIRVContextDependentInt extends SPIRVLiteralContextDependentNumber {
    private final BigInteger value;

    public SPIRVContextDependentInt(BigInteger value) {
        this.value = value;
    }

    @Override
    public void write(ByteBuffer output) {
        output.putInt(value.intValue());
    }

    @Override
    public int getWordCount() {
        return 1;
    }
}
