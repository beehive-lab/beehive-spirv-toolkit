package uk.ac.manchester.spirvproto.lib.disassembler;

import uk.ac.manchester.spirvproto.lib.grammar.SPIRVInstruction;

public class InvalidSPIRVWordCountException extends Exception {
    public InvalidSPIRVWordCountException(SPIRVInstruction instruction, int operandsLength, int wordcount) {
        super(String.format("Instruction %s has %d operands, therefore cannot have %d as wordcount.", instruction.getName(), operandsLength, wordcount));
    }
}
