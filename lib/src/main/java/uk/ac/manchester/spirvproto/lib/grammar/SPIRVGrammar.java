package uk.ac.manchester.spirvproto.lib.grammar;

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

    public SPIRVInstruction getInstructionByOpCode(int opcode) {
        // Unfortunately any non-custom binary search implementation needs a dummy object
        SPIRVInstruction dummy = new SPIRVInstruction();
        dummy.opCode = opcode;
        int index = Arrays.binarySearch(instructions, dummy);
        //System.out.println("opcode: " + opcode + " index: " + index);

        return instructions[index];
    }

    public SPIRVOperandKind getOperandKind(String kind) {
        for (SPIRVOperandKind operandKind : operandKinds) {
            if (operandKind.kind.equals(kind)) return operandKind;
        }
        return null;
    }
}
