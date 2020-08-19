package uk.ac.manchester.spirvproto.lib.instructions.operands;

import uk.ac.manchester.spirvproto.lib.disassembler.SPIRVPrintingOptions;

import java.io.PrintStream;
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

    @Override
    public void print(PrintStream output, SPIRVPrintingOptions options) {
        output.print(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SPIRVContextDependentFloat) return this.value == ((SPIRVContextDependentFloat) other).value;
        else return super.equals(other);
    }
}
