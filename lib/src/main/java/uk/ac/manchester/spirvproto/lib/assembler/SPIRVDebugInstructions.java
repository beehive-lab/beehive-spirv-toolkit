package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SPIRVDebugInstructions {
    private final List<SPIRVSourceInst> sourceInstructions;
    private final List<SPIRVNameInst> nameInstructions;
    private final List<SPIRVModuleProcessedInst> modules;
    private final SPIRVModule module;

    public SPIRVDebugInstructions(SPIRVModule module) {
        this.module = module;
        sourceInstructions = new ArrayList<>();
        nameInstructions = new ArrayList<>();
        modules = new ArrayList<>();
    }

    public void add(SPIRVDebugInst instruction) {
        if (instruction instanceof SPIRVSourceInst) {
            sourceInstructions.add((SPIRVSourceInst) instruction);
        }
        else if (instruction instanceof  SPIRVNameInst) {
            nameInstructions.add((SPIRVNameInst) instruction);
        }
        else if (instruction instanceof SPIRVModuleProcessedInst) {
            modules.add((SPIRVModuleProcessedInst) instruction);
        }
        else {
            throw new IllegalArgumentException("Instruction: " + instruction.getClass().getName() + " is not a valid debug instruction");
        }
    }

    public void forEachInstruction(Consumer<SPIRVInstruction> instructionConsumer) {
        sourceInstructions.forEach(instructionConsumer);
        nameInstructions.forEach(instructionConsumer);
        modules.forEach(instructionConsumer);
    }
}
