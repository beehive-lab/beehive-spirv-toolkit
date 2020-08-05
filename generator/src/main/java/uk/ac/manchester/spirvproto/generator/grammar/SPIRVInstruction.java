package uk.ac.manchester.spirvproto.generator.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVInstruction implements Comparable<SPIRVInstruction> {
    @JsonProperty("opname")
    public String name;

    @JsonProperty("opcode")
    public int opCode;

    @JsonProperty("operands")
    public SPIRVOperand[] operands;

    public String superClass;

    @Override
    public int compareTo(SPIRVInstruction o) {
        return this.getOpCode() - o.getOpCode();
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public int getOpCode() {
        return opCode;
    }

    public SPIRVOperand[] getOperands() {
        return operands;
    }

    public int getRequiredOperandCount() {
        int requiredOperandCount = 0;
        if (operands != null) {
            for (SPIRVOperand operand : operands) {
                if (operand.getQuantifier() != '*' && operand.getQuantifier() != '?') requiredOperandCount++;
            }
        }
        return requiredOperandCount;
    }

    public int getOperandCount() {
        return (operands != null) ? operands.length : 0;
    }

    public String getSuperClass() {
        return superClass;
    }
}
