package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import java.util.Iterator;

public class SPIRVOperandMapper {
    public static SPIRVId mapId(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        return scope.getOrCreateId(tokens.next().value);
    }

    public static SPIRVLiteralString mapLiteralString(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        return new SPIRVLiteralString(token.value.substring(1, token.value.length() - 1));
    }

    public static SPIRVLiteralInteger mapLiteralInteger(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        return new SPIRVLiteralInteger(Integer.parseInt(tokens.next().value));
    }

    public static SPIRVCapability mapCapability(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        switch (token.value) {
            case "Addresses" : return SPIRVCapability.Addresses();
            case "Linkage": return SPIRVCapability.Linkage();
            case "Kernel": return SPIRVCapability.Kernel();
            case "Float64": return SPIRVCapability.Float64();
            default: throw new IllegalArgumentException("Capability: " + token.value);
        }
    }

    public static SPIRVAddressingModel mapAddressingModel(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        switch (token.value) {
            case "Logical": return SPIRVAddressingModel.Logical();
            case "Physical32": return SPIRVAddressingModel.Physical32();
            case "Physical64": return SPIRVAddressingModel.Physical64();
            default: throw new IllegalArgumentException(token.value);
        }
    }

    public static SPIRVMemoryModel mapMemoryModel(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        switch (token.value) {
            case "Simple": return SPIRVMemoryModel.Simple();
            case "GLSL450": return SPIRVMemoryModel.GLSL450();
            case "OpenCL": return SPIRVMemoryModel.OpenCL();
            default: throw new IllegalArgumentException(token.value);
        }
    }

    public static SPIRVExecutionModel mapExecutionModel(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        switch (token.value) {
            case "Vertex": return SPIRVExecutionModel.Vertex();
            case "Kernel": return SPIRVExecutionModel.Kernel();
            default: throw new IllegalArgumentException(token.value);
        }

    }

    public static SPIRVSourceLanguage mapSourceLanguage(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        switch (token.value) {
            case "Unknown": return SPIRVSourceLanguage.Unknown();
            case "OpenCL_C": return SPIRVSourceLanguage.OpenCL_C();
            case "OpenCL_CPP": return SPIRVSourceLanguage.OpenCL_CPP();
            default: throw new IllegalArgumentException(token.value);
        }
    }

    public static SPIRVDecoration mapDecoration(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        switch (token.value) {
            case "FuncParamAttr": {
                SPIRVFunctionParameterAttribute operand1 = SPIRVOperandMapper.mapFunctionParameterAttribute(tokens, scope);
                return SPIRVDecoration.FuncParamAttr(operand1);
            }
            case "Alignment": {
                SPIRVLiteralInteger operand1 = SPIRVOperandMapper.mapLiteralInteger(tokens, scope);
                return SPIRVDecoration.Alignment(operand1);
            }
            case "BuiltIn": {
                SPIRVBuiltIn operand1 = SPIRVOperandMapper.mapBuiltIn(tokens, scope);
                return SPIRVDecoration.BuiltIn(operand1);
            }
            case "LinkageAttributes": {
                SPIRVLiteralString operand1 = SPIRVOperandMapper.mapLiteralString(tokens, scope);
                SPIRVLinkageType operand2 = SPIRVOperandMapper.mapLinkageType(tokens, scope);
                return SPIRVDecoration.LinkageAttributes(operand1, operand2);
            }
            case "Constant": return SPIRVDecoration.Constant();
            default: throw new IllegalArgumentException("Decoration: " + token.value);
        }
    }

    private static SPIRVLinkageType mapLinkageType(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        switch (token.value) {
            case "Export": return SPIRVLinkageType.Export();
            case "Import": return SPIRVLinkageType.Import();
            default: throw new IllegalArgumentException("LinkageType: " + token.value);
        }
    }

    private static SPIRVBuiltIn mapBuiltIn(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        switch (token.value) {
            case "GlobalInvocationId": return SPIRVBuiltIn.GlobalInvocationId();
            case "WorkDim": return SPIRVBuiltIn.WorkDim();
            default: throw new IllegalArgumentException("BuiltIn: " + token.value);
        }
    }

    private static SPIRVFunctionParameterAttribute mapFunctionParameterAttribute(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        switch (token.value) {
            case "NoWrite": return SPIRVFunctionParameterAttribute.NoWrite();
            case "NoReadWrite": return SPIRVFunctionParameterAttribute.NoReadWrite();
            default: throw new IllegalArgumentException(token.value);
        }
    }

    public static SPIRVStorageClass mapStorageClass(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        switch (token.value) {
            case "Input": return SPIRVStorageClass.Input();
            case "CrossWorkgroup": return SPIRVStorageClass.CrossWorkgroup();
            case "Function": return SPIRVStorageClass.Function();
            default: throw new IllegalArgumentException("StorageClass: " + token.value);
        }
    }

    public static SPIRVFunctionControl mapFunctionControl(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        String[] values = tokens.next().value.split("\\|");
        SPIRVFunctionControl retVal = SPIRVFunctionControl.None();
        for (String value : values) {
            switch (value) {
                case "None": retVal.add(SPIRVFunctionControl.None()); break;
                case "Inline": retVal.add(SPIRVFunctionControl.Inline()); break;
                case "DontInline": retVal.add(SPIRVFunctionControl.DontInline()); break;
                default: throw new IllegalArgumentException("FunctionControl: " + value);
            }
        }
        return retVal;
    }

    public static SPIRVMemoryAccess mapMemoryAccess(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        String[] values = tokens.next().value.split("\\|");
        SPIRVMemoryAccess retVal = SPIRVMemoryAccess.None();
        for (String value : values) {
            switch (value) {
                case "None": retVal.add(SPIRVMemoryAccess.None()); break;
                case "Volatile": retVal.add(SPIRVMemoryAccess.Volatile()); break;
                case "Aligned": {
                    SPIRVLiteralInteger operand1 = SPIRVOperandMapper.mapLiteralInteger(tokens, scope);
                    retVal.add(SPIRVMemoryAccess.Aligned(operand1));
                    break;
                }
                case "Nontemporal": retVal.add(SPIRVMemoryAccess.Nontemporal()); break;
                default: throw new IllegalArgumentException("MemoryAccess: " + value);
            }
        }
        return retVal;
    }

    public static SPIRVExecutionMode mapExecutionMode(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        switch (token.value) {
            case "ContractionOff": return SPIRVExecutionMode.ContractionOff();
            default: throw new IllegalArgumentException("ExecutionMode: " + token.value);
        }
    }
}
