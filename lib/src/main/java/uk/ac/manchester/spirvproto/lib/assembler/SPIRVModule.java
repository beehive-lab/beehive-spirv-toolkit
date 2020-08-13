package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.SPIRVHeader;
import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVFunctionControl;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

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
    private final List<SPIRVFunctionDeclaration> functionDeclarations;
    private final List<SPIRVFunctionDefinition> functionDefinitions;

    private final SPIRVIdGenerator idGen;

    private SPIRVInstScope currentScope;

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
        functionDeclarations = new ArrayList<>();
        functionDefinitions = new ArrayList<>();

        idGen = new SPIRVIdGenerator();
        currentScope = this;
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
        SPIRVFunctionDefinition def = new SPIRVFunctionDefinition((SPIRVFunctionInst) instruction, this);
        functionDefinitions.add(def);
        return def;
    }

    public SPIRVFunctionDeclaration createFunctionDeclaration(SPIRVId returnType, SPIRVId funcType, SPIRVId result, SPIRVFunctionControl control, SPIRVFunctionParameterInst... params) {
        SPIRVFunctionDeclaration declaration = new SPIRVFunctionDeclaration(returnType, funcType, result, control, params);
        functionDeclarations.add(declaration);
        return declaration;
    }

    public SPIRVFunctionDefinition createFunctionDefinition(SPIRVId returnType, SPIRVId funcType, SPIRVId result, SPIRVFunctionControl control, SPIRVFunctionParameterInst... params) {
        SPIRVFunctionDefinition definition = new SPIRVFunctionDefinition(returnType, funcType, result, control, idGen, params);
        functionDefinitions.add(definition);
        return definition;
    }

    public SPIRVModuleWriter validate() throws InvalidSPIRVModuleException {
        if (capabilities.size() < 1) {
            throw new InvalidSPIRVModuleException("There were no capabilities declared");
        }
        if (memoryModel == null) {
            throw new InvalidSPIRVModuleException("There was no memory model defined");
        }
        if (functionDeclarations.size() + functionDefinitions.size() < 1) {
            throw new InvalidSPIRVModuleException("There are no functions declared or defined");
        }
        if (entryPoints.size() < 1) {
            //TODO: Look for Linkage Capability
            throw new InvalidSPIRVModuleException("There were no entry points added");
        }

        return new SPIRVModuleWriter();
    }

    public int getByteCount() {
        int wordCount = memoryModel.getWordCount();
        wordCount += sumLists(SPIRVInstruction::getWordCount,
                capabilities,
                extensions,
                imports,
                entryPoints,
                executionModes,
                annotations,
                types,
                constants,
                globals);
        wordCount += sumLists(SPIRVFunctionDeclaration::getWordCount, functionDeclarations, functionDefinitions);
        wordCount += debugInstructions.getWordCount();
        wordCount += 5; // for the header

        return wordCount * 4;
    }

    @SafeVarargs
    private final <T> int sumLists(ToIntFunction<T> mapper, List<? extends T>... lists) {
        int wordCount = 0;
        for (List<? extends T> list : lists) {
            wordCount += list.stream().mapToInt(mapper).sum();
        }
        return wordCount;
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

    public void print(PrintStream output) {
        capabilities.forEach(c -> c.print(output));
        extensions.forEach(e -> e.print(output));
        imports.forEach(i -> i.print(output));
        memoryModel.print(output);
        entryPoints.forEach(e -> e.print(output));
        executionModes.forEach(e -> e.print(output));
        debugInstructions.print(output);
        annotations.forEach(a -> a.print(output));
        types.forEach(t -> t.print(output));
        constants.forEach(c -> c.print(output));
        globals.forEach(g -> g.print(output));
        for (SPIRVFunctionDeclaration functionDeclaration : functionDeclarations) {
            functionDeclaration.print(output);
        }
        for (SPIRVFunctionDefinition f : functionDefinitions) {
            f.print(output);
        }
    }

    public class SPIRVModuleWriter {
        protected SPIRVModuleWriter() { }

        public void write(ByteBuffer output) throws InvalidSPIRVModuleException {
            new SPIRVHeader(0x07230203, 0x00010200, 0, idGen.getCurrentBound(), 0).write(output);
            capabilities.forEach(c -> c.write(output));
            extensions.forEach(e -> e.write(output));
            imports.forEach(i -> i.write(output));
            memoryModel.write(output);
            entryPoints.forEach(e -> e.write(output));
            executionModes.forEach(e -> e.write(output));
            debugInstructions.write(output);
            annotations.forEach(a -> a.write(output));
            types.forEach(t -> t.write(output));
            constants.forEach(c -> c.write(output));
            globals.forEach(g -> g.write(output));
            for (SPIRVFunctionDeclaration functionDeclaration : functionDeclarations) {
                functionDeclaration.write(output);
            }
            for (SPIRVFunctionDefinition f : functionDefinitions) {
                f.write(output);
            }
        }
    }
}
