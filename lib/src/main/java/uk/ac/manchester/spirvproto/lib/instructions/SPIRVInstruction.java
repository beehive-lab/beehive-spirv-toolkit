package uk.ac.manchester.spirvproto.lib.instructions;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public abstract class SPIRVInstruction {
    protected final int opCode;
    protected final int wordCount;
    protected final String name;

    protected SPIRVInstruction(int opCode, int wordCount, String name) {
        this.opCode = opCode;
        this.wordCount = wordCount;
        this.name = name;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void write(ByteBuffer output) {
        int operation = (wordCount << 16) | (opCode);
        output.putInt(operation);
        writeOperands(output);
    }

    protected abstract void writeOperands(ByteBuffer output);

    public void print(PrintStream output) {
        output.print(name + " ");
        printOperands(output);
        output.println();
    }

    protected abstract void printOperands(PrintStream output);
}
