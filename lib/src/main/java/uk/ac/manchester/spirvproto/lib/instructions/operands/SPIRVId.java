package uk.ac.manchester.spirvproto.lib.instructions.operands;

import uk.ac.manchester.spirvproto.lib.disassembler.SPIRVPrintingOptions;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public class SPIRVId implements SPIRVOperand {
    public final int id;
    private String name;

    public SPIRVId(int id) {
        this.id = id;
        name = null;
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
        checkName(options.shouldInlineNames);
        output.print(options.highlighter.highlightId("%" + name));
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SPIRVId) return this.id == ((SPIRVId) other).id;
        return super.equals(other);
    }

    public int nameSize(boolean inlineNames) {
        checkName(inlineNames);
        return name.length() + 1;
    }

    private void checkName(boolean inlineNames) {
        if (!inlineNames || name == null) name = Integer.toString(id);
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        if (name == null) return "";
        return name;
    }
}
