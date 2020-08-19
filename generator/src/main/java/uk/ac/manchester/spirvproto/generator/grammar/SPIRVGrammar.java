package uk.ac.manchester.spirvproto.generator.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVGrammar {
    @JsonProperty("magic_number")
    public String magicNumber;

    @JsonProperty("instructions")
    public SPIRVInstruction[] instructions;

    @JsonProperty("operand_kinds")
    public SPIRVOperandKind[] operandKinds;

    public SPIRVGrammar() {}

    public SPIRVInstruction[] getInstructions() {
        return instructions;
    }

    public SPIRVOperandKind[] getOperandKinds() {
        return operandKinds;
    }
}
