package uk.ac.manchester.spirvproto.lib.instructions.operands;

import uk.ac.manchester.spirvproto.lib.disassembler.SPIRVPrintingOptions;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.List;

public abstract class SPIRVEnum implements SPIRVOperand {
    protected int value;
    protected String name;
    protected List<SPIRVOperand> parameters;

    protected SPIRVEnum(int value, String name, List<SPIRVOperand> parameters) {
        this.value = value;
        this.name = name;
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

    @Override
    public void print(PrintStream output, SPIRVPrintingOptions options) {
        output.print(name);
        if (parameters.size() > 0) output.print(" ");
        for (int i = 0, parametersSize = parameters.size(); i < parametersSize; i++) {
            SPIRVOperand p = parameters.get(i);
            p.print(output, options);
            if (i < parameters.size() - 1) output.print(" ");
        }
    }
}
