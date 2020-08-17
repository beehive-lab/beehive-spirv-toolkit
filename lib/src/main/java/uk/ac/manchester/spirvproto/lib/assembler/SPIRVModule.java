package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.SPIRVHeader;
import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SPIRVModule implements SPIRVInstScope {
    private final List<SPIRVCapabilityInst> capabilities;
    private final List<SPIRVExtensionInst> extensions;
    private final List<SPIRVImportInst> imports;
    private SPIRVMemoryModelInst memoryModel;
    private final List<SPIRVEntryPointInst> entryPoints;
    private final List<SPIRVExecutionModeInst> executionModes;
    private final SPIRVDebugInstructions debugInstructions;
    private final List<SPIRVAnnotationInst> annotations;
    private final List<SPIRVTypeInst> types;
    private final List<SPIRVConstantInst> constants;
    private final List<SPIRVVariableInst> globals;
    private final List<SPIRVFunction> functions;

    private final SPIRVIdGenerator idGen;

    public SPIRVModule() {
        capabilities = new ArrayList<>();
        extensions = new ArrayList<>();
        imports = new ArrayList<>();
        this.memoryModel = null;
        entryPoints = new ArrayList<>();
        executionModes = new ArrayList<>();
        debugInstructions = new SPIRVDebugInstructions();
        annotations = new ArrayList<>();
        types = new ArrayList<>();
        constants = new ArrayList<>();
        globals = new ArrayList<>();
        functions = new ArrayList<>();

        idGen = new SPIRVIdGenerator();
    }

    public SPIRVInstScope add(SPIRVInstruction instruction) {
        if (instruction instanceof SPIRVCapabilityInst) capabilities.add((SPIRVCapabilityInst) instruction);
        else if (instruction instanceof SPIRVExtensionInst) extensions.add((SPIRVExtensionInst) instruction);
        else if (instruction instanceof SPIRVImportInst) imports.add((SPIRVImportInst) instruction);
        else if (instruction instanceof SPIRVEntryPointInst) entryPoints.add((SPIRVEntryPointInst) instruction);
        else if (instruction instanceof SPIRVExecutionModeInst) executionModes.add((SPIRVExecutionModeInst) instruction);
        else if (instruction instanceof SPIRVDebugInst) debugInstructions.add((SPIRVDebugInst) instruction);
        else if (instruction instanceof SPIRVAnnotationInst) annotations.add((SPIRVAnnotationInst) instruction);
        else if (instruction instanceof SPIRVTypeInst) types.add((SPIRVTypeInst) instruction);
        else if (instruction instanceof SPIRVConstantInst) constants.add((SPIRVConstantInst) instruction);
        else if (instruction instanceof SPIRVVariableInst) globals.add((SPIRVVariableInst) instruction);
        else if (instruction instanceof SPIRVMemoryModelInst) memoryModel = (SPIRVMemoryModelInst) instruction;
        else if (instruction instanceof SPIRVFunctionInst) return createFunction(instruction);
        else throw new IllegalArgumentException("Instruction: " + instruction.getClass().getName() + " is not a valid global instruction");

        return this;
    }

    private SPIRVInstScope createFunction(SPIRVInstruction instruction) {
        SPIRVFunction function = new SPIRVFunction((SPIRVFunctionInst) instruction, this);
        functions.add(function);
        return function;
    }

    public SPIRVModuleWriter validate() throws InvalidSPIRVModuleException {
        if (capabilities.size() < 1) {
            throw new InvalidSPIRVModuleException("There were no capabilities declared");
        }
        if (memoryModel == null) {
            throw new InvalidSPIRVModuleException("There was no memory model defined");
        }
        if (functions.size() < 1) {
            throw new InvalidSPIRVModuleException("There are no functions declared or defined");
        }
        if (entryPoints.size() < 1) {
            //TODO: Look for Linkage Capability
            throw new InvalidSPIRVModuleException("There were no entry points added");
        }

        return new SPIRVModuleWriter();
    }

    public int getByteCount() {
        final int[] wordCount = {0};
        this.forEachInstruction(i -> wordCount[0] += i.getWordCount());
        wordCount[0] += 5; // for the header

        return wordCount[0] * 4;
    }

    public SPIRVId getNextId() {
        return idGen.getNextId();
    }

    public SPIRVId getOrCreateId(String name) {
        return idGen.getOrCreateId(name);
    }

    @Override
    public SPIRVIdGenerator getIdGen() {
        return idGen;
    }

    @Override
    public void forEachInstruction(Consumer<SPIRVInstruction> instructionConsumer) {
        capabilities.forEach(instructionConsumer);
        extensions.forEach(instructionConsumer);
        imports.forEach(instructionConsumer);
        instructionConsumer.accept(memoryModel);
        entryPoints.forEach(instructionConsumer);
        executionModes.forEach(instructionConsumer);
        debugInstructions.forEachInstruction(instructionConsumer);
        annotations.forEach(instructionConsumer);
        types.forEach(instructionConsumer);
        constants.forEach(instructionConsumer);
        globals.forEach(instructionConsumer);

        Map<Boolean, List<SPIRVFunction>> functionGroups = functions.stream().collect(
                Collectors.partitioningBy(SPIRVFunction::hasBlocks));
        functionGroups.get(false).forEach(f -> f.forEachInstruction(instructionConsumer));
        functionGroups.get(true).forEach(f -> f.forEachInstruction(instructionConsumer));
    }

    public class SPIRVModuleWriter {
        protected SPIRVModuleWriter() { }

        public void write(ByteBuffer output) {
            new SPIRVHeader(0x07230203, 0x00010200, 0, idGen.getCurrentBound(), 0).write(output);
            forEachInstruction(i -> i.write(output));
        }
    }
}
