package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.*;

import java.nio.ByteBuffer;
import java.util.List;

public class SPIRVDebugInstructions {
    private List<SPIRVSourceInst> sourceInstructions;
    private List<SPIRVNameInst> nameInstructions;
    private List<SPIRVModuleProcessedInst> modules;

    public void write(ByteBuffer output) {
        sourceInstructions.forEach(s -> s.write(output));
        nameInstructions.forEach(n -> n.write(output));
        modules.forEach(m -> m.write(output));
    }

    public void add(SPIRVDebugInst instruction) {
        if (instruction instanceof SPIRVSourceInst) sourceInstructions.add((SPIRVSourceInst) instruction);
        else if (instruction instanceof  SPIRVNameInst) nameInstructions.add((SPIRVNameInst) instruction);
        else if (instruction instanceof SPIRVModuleProcessedInst) modules.add((SPIRVModuleProcessedInst) instruction);
        else throw new IllegalArgumentException("Instruction: " + instruction.getClass().getName() + " is not a valid debug instruction");
    }

    public int getWordCount() {
        int wordCount = 0;
        wordCount += sourceInstructions.stream().mapToInt(SPIRVInstruction::getWordCount).sum();
        wordCount += nameInstructions.stream().mapToInt(SPIRVInstruction::getWordCount).sum();
        wordCount += modules.stream().mapToInt(SPIRVInstruction::getWordCount).sum();

        return wordCount;
    }
}
