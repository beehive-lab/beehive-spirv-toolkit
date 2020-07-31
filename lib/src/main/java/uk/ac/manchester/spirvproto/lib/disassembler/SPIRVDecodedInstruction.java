package uk.ac.manchester.spirvproto.lib.disassembler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SPIRVDecodedInstruction {
    public final String operationName;
    public final List<SPIRVDecodedOperand> operands;
    public SPIRVDecodedOperand result;

    public SPIRVDecodedInstruction(String operationName, int requiredOperands) {
        this.operationName = operationName;
        operands = new ArrayList<>(requiredOperands);
    }

    public SPIRVDecodedOperand getLastOperand() {
        if (operands.isEmpty()) throw new NoSuchElementException();

        return operands.get(operands.size() - 1);
    }

    public void setResult(SPIRVDecodedOperand id) {
        result = id;
    }

    public void addOperand(String operand, SPIRVOperandCategory category) {
        operands.add(new SPIRVDecodedOperand(operand, category));
    }
}
