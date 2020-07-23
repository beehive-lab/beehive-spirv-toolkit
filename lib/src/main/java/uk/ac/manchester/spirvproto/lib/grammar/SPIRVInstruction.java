package uk.ac.manchester.spirvproto.lib.grammar;

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
}
