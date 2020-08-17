package uk.ac.manchester.spirvproto.lib.instructions.operands;

import uk.ac.manchester.spirvproto.lib.disassembler.SPIRVPrintingOptions;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public class SPIRVContextDependentDouble extends SPIRVLiteralContextDependentNumber {
    private final double value;

    public SPIRVContextDependentDouble(double value) {
        this.value = value;
    }

    @Override
    public void write(ByteBuffer output) {
        output.putDouble(value);
    }

    @Override
    public int getWordCount() {
        return 2;
    }

    @Override
    public void print(PrintStream output, SPIRVPrintingOptions options) {
        output.print(value);
    }
}
