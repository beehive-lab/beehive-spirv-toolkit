package uk.ac.manchester.spirvproto.lib.instructions.operands;

import uk.ac.manchester.spirvproto.lib.disassembler.SPIRVPrintingOptions;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public class SPIRVId implements SPIRVOperand {
    private final int id;
    private final String name;

    public SPIRVId(int id) {
        this.id = id;
        name = "%" + id;
    }

    @Override
    public void write(ByteBuffer output) {
        output.putInt(id);
    }

    @Override
    public int getWordCount() {
        return 1;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public void print(PrintStream output, SPIRVPrintingOptions options) {
        output.print(options.highlighter.highlightId(name));
    }

    public int nameSize() {
        return name.length();
    }
}
