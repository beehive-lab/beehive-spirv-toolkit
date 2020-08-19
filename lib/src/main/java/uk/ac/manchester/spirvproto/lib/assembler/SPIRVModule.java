package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.SPIRVHeader;
import uk.ac.manchester.spirvproto.lib.disassembler.SPIRVPrintingOptions;
import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SPIRVModule implements SPIRVInstScope {
    private final SPIRVHeader header;

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
    private final Map<SPIRVId, SPIRVInstruction> idToInstMap;

    public SPIRVModule(SPIRVHeader header) {
        this.header = header;
        capabilities = new ArrayList<>();
        extensions = new ArrayList<>();
        imports = new ArrayList<>();
        this.memoryModel = null;
        entryPoints = new ArrayList<>();
        executionModes = new ArrayList<>();
        debugInstructions = new SPIRVDebugInstructions(this);
        annotations = new ArrayList<>();
        types = new ArrayList<>();
        constants = new ArrayList<>();
        globals = new ArrayList<>();
        functions = new ArrayList<>();

        idGen = new SPIRVIdGenerator();
        idToInstMap = new HashMap<>();
    }

    public SPIRVInstScope add(SPIRVInstruction instruction) {
        if (instruction instanceof SPIRVCapabilityInst) capabilities.add((SPIRVCapabilityInst) instruction);
        else if (instruction instanceof SPIRVExtensionInst) addExtension((SPIRVExtensionInst) instruction);
        else if (instruction instanceof SPIRVImportInst) addImport((SPIRVImportInst) instruction);
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

        SPIRVId resultId = instruction.getResultId();
        if (resultId != null) idToInstMap.put(resultId, instruction);

        return this;
    }

    private void addImport(SPIRVImportInst instruction) {
        imports.add(instruction);
        if (instruction instanceof SPIRVOpExtInstImport) {
            String name = ((SPIRVOpExtInstImport) instruction)._name.value;
            if (name.equals("OpenCL.std")) {
                if (header.genMagicNumber == Assembler.GenNumber) uk.ac.manchester.spirvproto.lib.assembler.SPIRVExtInstMapper.loadOpenCL();
                else uk.ac.manchester.spirvproto.lib.disassembler.SPIRVExtInstMapper.loadOpenCL();
            }
            else {
                throw new RuntimeException("Unsupported external import: " + name);
            }
        }
    }

    private void addExtension(SPIRVExtensionInst instruction) {
        extensions.add(instruction);
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

        header.setBound(idGen.getCurrentBound());
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

    public void print(PrintStream output, SPIRVPrintingOptions options) {
        this.forEachInstruction(i -> i.print(output, options));
    }

    @Override
    public SPIRVInstruction getInstruction(SPIRVId id) {
        return idToInstMap.get(id);
    }

    @Override
    public SPIRVId getOrAddId(int id) {
        return idGen.getOrAddId(id);
    }

    public class SPIRVModuleWriter {
        protected SPIRVModuleWriter() { }

        public void write(ByteBuffer output) {
            header.write(output);
            forEachInstruction(i -> i.write(output));
        }
    }
}
