package uk.ac.manchester.spirvproto.lib.instructions;

import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.nio.ByteBuffer;

public abstract class SPIRVInstruction {
    protected final int opCode;
    protected final int wordCount;

    protected SPIRVInstruction(int opCode, int wordCount) {
        this.opCode = opCode;
        this.wordCount = wordCount;
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

    public abstract SPIRVId getResultId();
}
