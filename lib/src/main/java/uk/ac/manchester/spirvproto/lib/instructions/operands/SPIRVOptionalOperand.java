package uk.ac.manchester.spirvproto.lib.instructions.operands;

import uk.ac.manchester.spirvproto.lib.disassembler.SPIRVPrintingOptions;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public class SPIRVOptionalOperand<T extends SPIRVOperand> implements SPIRVOperand {
    private T operand;

    public SPIRVOptionalOperand() {
        operand = null;
    }

    public SPIRVOptionalOperand(T operand) {
        this.operand = operand;
    }

    @Override
    public void write(ByteBuffer output) {
        if (operand != null) operand.write(output);
    }

    @Override
    public int getWordCount() {
        return operand == null ? 0: operand.getWordCount();
    }

    @Override
    public void print(PrintStream output, SPIRVPrintingOptions options) {
        if (operand != null) operand.print(output, options);
    }

    public void setValue(T value) {
        operand = value;
    }
}
