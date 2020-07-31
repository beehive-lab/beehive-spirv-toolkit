package uk.ac.manchester.spirvproto.lib.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVExternalInstruction implements Comparable<SPIRVExternalInstruction>{
    @JsonProperty("opname")
    public String opName;

    @JsonProperty("opcode")
    public int opCode;

    @JsonProperty("operands")
    public SPIRVOperand[] operands;

    @Override
    public int compareTo(SPIRVExternalInstruction o) {
        return this.opCode - o.opCode;
    }
}
