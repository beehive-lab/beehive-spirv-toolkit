package uk.ac.manchester.spirvproto.lib.instructions.operands;

import java.nio.ByteBuffer;
import java.util.List;

public abstract class SPIRVEnum implements SPIRVOperand{
    protected int value;
    protected List<SPIRVOperand> parameters;

    protected SPIRVEnum(int value, List<SPIRVOperand> parameters) {
        this.value = value;
        this.parameters = parameters;
    }

    @Override
    public void write(ByteBuffer output) {
        output.putInt(value);
    }

    @Override
    public int getWordCount() {
        return 1 + parameters.stream().mapToInt(SPIRVOperand::getWordCount).sum();
    }
}
