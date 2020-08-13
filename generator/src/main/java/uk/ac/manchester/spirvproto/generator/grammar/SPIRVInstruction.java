package uk.ac.manchester.spirvproto.generator.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVInstruction {
    @JsonProperty("opname")
    public String name;

    @JsonProperty("opcode")
    public int opCode;

    @JsonProperty("operands")
    public SPIRVOperand[] operands;

    public String superClass;

    public boolean hasReturnType;

    public boolean hasResult;

    public SPIRVInstruction() {
        hasReturnType = false;
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

    public String getSuperClass() {
        return superClass;
    }

    public boolean getHasReturnType() {
        return hasReturnType;
    }

    public boolean getHasResult() {
        return hasResult;
    }
}
