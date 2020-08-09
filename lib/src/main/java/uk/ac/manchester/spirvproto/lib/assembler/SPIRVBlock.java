package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.SPIRVInstruction;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVLabelInst;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpLabel;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVTerminationInst;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SPIRVBlock {
    private final SPIRVLabelInst label;
    private final List<SPIRVInstruction> instructions;
    private SPIRVTerminationInst end;

    public SPIRVBlock() {
        label = new SPIRVOpLabel(null);
        instructions = new ArrayList<>();
    }

    public void addInstruction(SPIRVInstruction instruction) {
        instructions.add(instruction);
    }

    public void write(ByteBuffer output) {
        label.write(output);
        instructions.forEach(i -> i.write(output));
        end.write(output);
    }

    public int getWordCount() {
        int wordCount = label.getWordCount() + end.getWordCount();
        wordCount += instructions.stream().mapToInt(SPIRVInstruction::getWordCount).sum();

        return wordCount;
    }
}
