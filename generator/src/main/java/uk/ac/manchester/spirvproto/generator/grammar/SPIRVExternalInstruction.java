package uk.ac.manchester.spirvproto.generator.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVExternalInstruction {
    @JsonProperty("opname")
    public String opName;

    @JsonProperty("opcode")
    public int opCode;

    public int getOpCode() {
        return opCode;
    }

    public String getOpName() {
        return opName;
    }
}
