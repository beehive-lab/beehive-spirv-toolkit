package uk.ac.manchester.spirvbeehivetoolkit.generator.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

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
