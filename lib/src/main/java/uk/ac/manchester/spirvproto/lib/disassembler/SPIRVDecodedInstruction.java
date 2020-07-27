package uk.ac.manchester.spirvproto.lib.disassembler;

import java.util.ArrayList;
import java.util.List;

public class SPIRVDecodedInstruction {
    public final String operationName;
    public final List<SPIRVDecodedOperand> operands;
    public SPIRVDecodedOperand result;

    public SPIRVDecodedInstruction(String operationName, int requiredOperands) {
        this.operationName = operationName;
        operands = new ArrayList<>(requiredOperands);
    }

    public void setResult(SPIRVDecodedOperand id) {
        result = id;
    }
}
