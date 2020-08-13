package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

public class SPIRVMultipleOperands<T extends SPIRVOperand> extends ArrayList<T> implements SPIRVOperand {

    @SafeVarargs
    public SPIRVMultipleOperands(T... operands) {
        Collections.addAll(this, operands);
    }

    @Override
    public void write(ByteBuffer output) {
        this.forEach(spirvOperand -> spirvOperand.write(output));
    }

    @Override
    public int getWordCount() {
        return this.stream().mapToInt(SPIRVOperand::getWordCount).sum();
    }

    @Override
    public void print(PrintStream output) {
        this.forEach(o -> o.print(output));
    }
}
