package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import java.util.Arrays;
import java.util.Iterator;

public class SPIRVInstMapper {
    public static SPIRVInstScope addToScope(SPIRVToken instruction, SPIRVToken[] tokens, SPIRVInstScope scope) {
        Iterator<SPIRVToken> tokenIterator = Arrays.stream(tokens).iterator();
        SPIRVInstruction decoded;
        switch (instruction.value) {
            case "OpCapability": decoded = createOpCapability(tokenIterator, scope); break;
            case "OpExtInstImport": decoded = createOpExtInstImport(tokenIterator, scope); break;
            case "OpMemoryModel": decoded = createOpMemoryModel(tokenIterator, scope); break;
            case "OpEntryPoint": decoded = createOpEntryPoint(tokenIterator, scope); break;
            case "OpString": decoded = createOpString(tokenIterator, scope); break;
            case "OpSource": decoded = createOpSource(tokenIterator, scope); break;
            case "OpName": decoded = createOpName(tokenIterator, scope); break;
            case "OpDecorate": decoded = createOpDecorate(tokenIterator, scope); break;
            case "OpDecorationGroup": decoded = createOpDecorationGroup(tokenIterator, scope); break;
            case "OpGroupDecorate": decoded = createOpGroupDecorate(tokenIterator, scope); break;
            case "OpTypeInt": decoded = createOpTypeInt(tokenIterator, scope); break;
            case "OpTypeVector": decoded = createOpTypeVector(tokenIterator, scope); break;
            case "OpTypePointer": decoded = createOpTypePointer(tokenIterator, scope); break;
            case "OpTypeVoid": decoded = createOpTypeVoid(tokenIterator, scope); break;
            case "OpTypeFunction": decoded = createOpTypeFunction(tokenIterator, scope); break;
            case "OpTypeFloat": decoded = createOpTypeFloat(tokenIterator, scope); break;
            case "OpTypeBool": decoded = createOpTypeBool(tokenIterator, scope); break;
            case "OpVariable": decoded = createOpVariable(tokenIterator, scope); break;
            case "OpFunction": decoded = createOpFunction(tokenIterator, scope); break;
            case "OpFunctionParameter": decoded = createOpFunctionParameter(tokenIterator, scope); break;
            case "OpLabel": decoded = createOpLabel(tokenIterator, scope); break;
            case "OpStore": decoded = createOpStore(tokenIterator, scope); break;
            case "OpLoad": decoded = createOpLoad(tokenIterator, scope); break;
            case "OpCompositeExtract": decoded = creatOpCompositeExtract(tokenIterator, scope); break;
            case "OpInBoundsPtrAccessChain": decoded = createOpInBoundsPtrAccessChain(tokenIterator, scope); break;
            case "OpIAdd": decoded = createOpIAdd(tokenIterator, scope); break;
            case "OpFAdd": decoded = createOpFAdd(tokenIterator, scope); break;
            case "OpReturn": decoded = createOpReturn(tokenIterator, scope); break;
            case "OpFunctionEnd": decoded = createOpFunctionEnd(tokenIterator, scope); break;
            case "OpExecutionMode": decoded = createOpExecutionMode(tokenIterator, scope); break;
            case "OpULessThan": decoded = createOpULessThan(tokenIterator, scope); break;
            case "OpBranchConditional": decoded = createOpBranchConditional(tokenIterator, scope); break;
            case "OpBranch": decoded = createOpBranch(tokenIterator, scope); break;
            default: throw new IllegalArgumentException(instruction.value);
        }

        return scope.add(decoded);
    }

    private static SPIRVInstruction createOpBranch(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        return new SPIRVOpBranch(operand1);
    }

    private static SPIRVInstruction createOpFAdd(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand3 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand4 = SPIRVOperandMapper.mapId(tokens, scope);
        return new SPIRVOpFAdd(operand2, operand1, operand3, operand4);
    }

    private static SPIRVInstruction createOpBranchConditional(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand3 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVMultipleOperands<SPIRVLiteralInteger> operand4 = new SPIRVMultipleOperands<>();
        return new SPIRVOpBranchConditional(operand1, operand2, operand3, operand4);
    }

    private static SPIRVInstruction createOpULessThan(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand3 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand4 = SPIRVOperandMapper.mapId(tokens, scope);
        return new SPIRVOpULessThan(operand2, operand1, operand3, operand4);
    }

    private static SPIRVInstruction createOpTypeBool(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        return new SPIRVOpTypeBool(operand1);
    }

    private static SPIRVInstruction createOpTypeFloat(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVLiteralInteger operand2 = SPIRVOperandMapper.mapLiteralInteger(tokens, scope);
        return new SPIRVOpTypeFloat(operand1, operand2);
    }

    private static SPIRVInstruction createOpExecutionMode(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVExecutionMode operand2 = SPIRVOperandMapper.mapExecutionMode(tokens, scope);
        return new SPIRVOpExecutionMode(operand1, operand2);
    }

    private static SPIRVInstruction createOpFunctionEnd(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        return new SPIRVOpFunctionEnd();
    }

    private static SPIRVInstruction createOpReturn(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        return new SPIRVOpReturn();
    }

    private static SPIRVInstruction createOpIAdd(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand3 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand4 = SPIRVOperandMapper.mapId(tokens, scope);
        return new SPIRVOpIAdd(operand2, operand1, operand3, operand4);
    }

    private static SPIRVInstruction createOpInBoundsPtrAccessChain(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand3 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand4 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVMultipleOperands<SPIRVId> operand5 = new SPIRVMultipleOperands<>();
        while (tokens.hasNext()) operand5.add(SPIRVOperandMapper.mapId(tokens, scope));
        return new SPIRVOpInBoundsPtrAccessChain(operand2, operand1, operand3, operand4, operand5);
    }

    private static SPIRVInstruction creatOpCompositeExtract(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand3 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVMultipleOperands<SPIRVLiteralInteger> operand4 = new SPIRVMultipleOperands<>();
        while (tokens.hasNext()) operand4.add(SPIRVOperandMapper.mapLiteralInteger(tokens, scope));
        return new SPIRVOpCompositeExtract(operand2, operand1, operand3, operand4);
    }

    private static SPIRVInstruction createOpLoad(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand3 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVOptionalOperand<SPIRVMemoryAccess> operand4 = new SPIRVOptionalOperand<>();
        if (tokens.hasNext()) operand4.setValue(SPIRVOperandMapper.mapMemoryAccess(tokens, scope));
        return new SPIRVOpLoad(operand2, operand1, operand3, operand4);
    }

    private static SPIRVInstruction createOpStore(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVOptionalOperand<SPIRVMemoryAccess> operand3 = new SPIRVOptionalOperand<>();
        if (tokens.hasNext()) operand3.setValue(SPIRVOperandMapper.mapMemoryAccess(tokens, scope));
        return new SPIRVOpStore(operand1, operand2, operand3);
    }

    private static SPIRVInstruction createOpLabel(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        return new SPIRVOpLabel(operand1);
    }

    private static SPIRVInstruction createOpFunctionParameter(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        return new SPIRVOpFunctionParameter(operand2, operand1);
    }

    private static SPIRVInstruction createOpFunction(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVFunctionControl operand3 = SPIRVOperandMapper.mapFunctionControl(tokens, scope);
        SPIRVId operand4 = SPIRVOperandMapper.mapId(tokens, scope);
        return new SPIRVOpFunction(operand2, operand1, operand3, operand4);
    }

    private static SPIRVInstruction createOpVariable(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVStorageClass operand3 = SPIRVOperandMapper.mapStorageClass(tokens, scope);
        SPIRVOptionalOperand<SPIRVId> operand4 = new SPIRVOptionalOperand<>();
        if (tokens.hasNext()) operand4.setValue(SPIRVOperandMapper.mapId(tokens, scope));
        return new SPIRVOpVariable(operand2, operand1, operand3, operand4);
    }

    private static SPIRVInstruction createOpTypeFunction(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVMultipleOperands<SPIRVId> operand3 = new SPIRVMultipleOperands<>();
        while (tokens.hasNext()) operand3.add(SPIRVOperandMapper.mapId(tokens, scope));
        return new SPIRVOpTypeFunction(operand1, operand2, operand3);
    }

    private static SPIRVInstruction createOpTypeVoid(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        return new SPIRVOpTypeVoid(operand1);
    }

    private static SPIRVInstruction createOpTypePointer(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVStorageClass operand2 = SPIRVOperandMapper.mapStorageClass(tokens, scope);
        SPIRVId operand3 = SPIRVOperandMapper.mapId(tokens, scope);
        return new SPIRVOpTypePointer(operand1, operand2, operand3);
    }

    private static SPIRVInstruction createOpTypeVector(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVLiteralInteger operand3 = SPIRVOperandMapper.mapLiteralInteger(tokens, scope);
        return new SPIRVOpTypeVector(operand1, operand2, operand3);
    }

    private static SPIRVInstruction createOpTypeInt(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVLiteralInteger operand2 = SPIRVOperandMapper.mapLiteralInteger(tokens, scope);
        SPIRVLiteralInteger operand3 = SPIRVOperandMapper.mapLiteralInteger(tokens, scope);
        return new SPIRVOpTypeInt(operand1, operand2, operand3);
    }

    private static SPIRVInstruction createOpGroupDecorate(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVMultipleOperands<SPIRVId> operand2 = new SPIRVMultipleOperands<>();
        while (tokens.hasNext()) operand2.add(SPIRVOperandMapper.mapId(tokens, scope));
        return new SPIRVOpGroupDecorate(operand1, operand2);
    }

    private static SPIRVInstruction createOpDecorationGroup(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        return new SPIRVOpDecorationGroup(operand1);
    }

    private static SPIRVInstruction createOpDecorate(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {

        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVDecoration operand2 = SPIRVOperandMapper.mapDecoration(tokens, scope);
        return new SPIRVOpDecorate(operand1, operand2);
    }

    private static SPIRVInstruction createOpName(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVLiteralString operand2 = SPIRVOperandMapper.mapLiteralString(tokens, scope);
        return new SPIRVOpName(operand1, operand2);
    }

    private static SPIRVInstruction createOpSource(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVSourceLanguage operand1 = SPIRVOperandMapper.mapSourceLanguage(tokens, scope);
        SPIRVLiteralInteger operand2 = SPIRVOperandMapper.mapLiteralInteger(tokens, scope);
        SPIRVOptionalOperand<SPIRVId> operand3 = new SPIRVOptionalOperand<>();
        if (tokens.hasNext()) operand3.setValue(SPIRVOperandMapper.mapId(tokens, scope));
        SPIRVOptionalOperand<SPIRVLiteralString> operand4 = new SPIRVOptionalOperand<>();
        if (tokens.hasNext()) operand4.setValue(SPIRVOperandMapper.mapLiteralString(tokens, scope));
        return new SPIRVOpSource(operand1, operand2, operand3, operand4);
    }

    private static SPIRVInstruction createOpString(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVLiteralString operand2 = SPIRVOperandMapper.mapLiteralString(tokens, scope);
        return new SPIRVOpString(operand1, operand2);
    }

    private static SPIRVInstruction createOpEntryPoint(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVExecutionModel operand1 = SPIRVOperandMapper.mapExecutionModel(tokens, scope);
        SPIRVId operand2 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVLiteralString operand3 = SPIRVOperandMapper.mapLiteralString(tokens, scope);
        SPIRVMultipleOperands<SPIRVId> operand4 = new SPIRVMultipleOperands<>();
        while (tokens.hasNext()) {
            operand4.add(SPIRVOperandMapper.mapId(tokens, scope));
        }
        return new SPIRVOpEntryPoint(operand1, operand2, operand3, operand4);
    }

    private static SPIRVInstruction createOpMemoryModel(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVAddressingModel operand1 = SPIRVOperandMapper.mapAddressingModel(tokens, scope);
        SPIRVMemoryModel operand2 = SPIRVOperandMapper.mapMemoryModel(tokens, scope);
        return new SPIRVOpMemoryModel(operand1, operand2);
    }

    private static SPIRVInstruction createOpExtInstImport(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVId operand1 = SPIRVOperandMapper.mapId(tokens, scope);
        SPIRVLiteralString operand2 = SPIRVOperandMapper.mapLiteralString(tokens, scope);
        return new SPIRVOpExtInstImport(operand1, operand2);
    }

    private static SPIRVInstruction createOpCapability(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVCapability operand1 = SPIRVOperandMapper.mapCapability(tokens, scope);
        return new SPIRVOpCapability(operand1);
    }
}
