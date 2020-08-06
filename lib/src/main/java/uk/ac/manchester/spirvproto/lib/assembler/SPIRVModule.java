package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVFunctionControl;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVIdRef;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVIdResultType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SPIRVModule {
    private final List<SPIRVCapabilityInst> capabilities;
    private final List<SPIRVExtensionInst> extensions;
    private final List<SPIRVImportInst> imports;
    private final SPIRVMemoryModelInst memoryModel;
    private final List<SPIRVEntryPointInst> entryPoints;
    private final List<SPIRVExecutionModeInst> executionModes;
    private final SPIRVDebugInstructions debugInstructions;
    private final List<SPIRVAnnotationInst> annotations;
    private final List<SPIRVTypeInst> types;
    private final List<SPIRVConstantInst> constants;
    private final List<SPIRVVariableInst> globals;
    private final List<SPIRVFunctionDeclaration> functionDeclarations;
    private final List<SPIRVFunctionDefinition> functionDefinitions;

    public SPIRVModule(SPIRVMemoryModelInst memoryModel) {
        capabilities = new ArrayList<>();
        extensions = new ArrayList<>();
        imports = new ArrayList<>();
        this.memoryModel = memoryModel;
        entryPoints = new ArrayList<>();
        executionModes = new ArrayList<>();
        debugInstructions = new SPIRVDebugInstructions();
        annotations = new ArrayList<>();
        types = new ArrayList<>();
        constants = new ArrayList<>();
        globals = new ArrayList<>();
        functionDeclarations = new ArrayList<>();
        functionDefinitions = new ArrayList<>();
    }

    public void add(SPIRVInstruction instruction) {
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

        else throw new IllegalArgumentException("Instruction: " + instruction.getClass().getName() + " is not a valid global instruction");
    }

    public SPIRVFunctionDeclaration createFunctionDeclaration(SPIRVIdResultType returnType, SPIRVIdRef funcType, SPIRVFunctionControl control, SPIRVFunctionParameterInst... params) {
        SPIRVFunctionDeclaration declaration = new SPIRVFunctionDeclaration(returnType, funcType, control, params);
        functionDeclarations.add(declaration);
        return declaration;
    }

    public SPIRVFunctionDefinition createFunctionDefinition(SPIRVIdResultType returnType, SPIRVIdRef funcType, SPIRVFunctionControl control, SPIRVFunctionParameterInst... params) {
        SPIRVFunctionDefinition definition = new SPIRVFunctionDefinition(returnType, funcType, control, params);
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

    public class SPIRVModuleWriter {
        protected SPIRVModuleWriter() { }

        public void write(ByteBuffer output) {
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
            functionDeclarations.forEach(f -> f.write(output));
            functionDefinitions.forEach(f -> f.write(output));
        }
    }
}
