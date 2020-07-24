package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class SPIRVMultipleOperands<T extends SPIRVOperand> extends ArrayList<T> implements SPIRVOperand {

    @Override
    public void write(ByteBuffer output) {
        this.forEach(spirvOperand -> spirvOperand.write(output));
    }

    @Override
    public int getWordCount() {
        return this.stream().mapToInt(SPIRVOperand::getWordCount).sum();
    }
}
