package uk.ac.manchester.spirvproto.generator;

import java.util.HashMap;

public class SPIRVInstructionSuperClassMapping extends HashMap<String, String> {

    public SPIRVInstructionSuperClassMapping() {
        super(53);

        put("OpCapability", "SPIRVCapabilityInst");
        put("OpExtension", "SPIRVExtensionInst");
        put("OpExtInstImport", "SPIRVImportInst");
        put("OpMemoryModel", "SPIRVMemoryModelInst");
        put("OpEntryPoint", "SPIRVEntryPointInst");
        put("OpModuleProcessed", "SPIRVModuleProcessedInst");
        put("OpVariable", "SPIRVVariableInst");
        put("OpConstant", "SPIRVConstantInst");
        put("OpFunction", "SPIRVFunctionInst");
        put("OpFunctionParameter", "SPIRVFunctionParameterInst");
        put("OpFunctionEnd", "SPIRVFunctionEndInst");
        put("OpLabel", "SPIRVLabelInst");

        put("OpExecutionMode", "SPIRVExecutionModeInst");
        put("OpExecutionModeId", "SPIRVExecutionModeInst");

        put("OpName", "SPIRVNameInst");
        put("OpMemberName", "SPIRVNameInst");

        put("OpString", "SPIRVSourceInst");
        put("OpSourceExtension", "SPIRVSourceInst");
        put("OpSource", "SPIRVSourceInst");
        put("OpSourceContinued", "SPIRVSourceInst");

        put("OpDecorate", "SPIRVAnnotationInst");
        put("OpMemberDecorate", "SPIRVAnnotationInst");
        put("OpGroupDecorate", "SPIRVAnnotationInst");
        put("OpGroupMemberDecorate", "SPIRVAnnotationInst");
        put("OpDecorationGroup", "SPIRVAnnotationInst");

        put("OpBranch", "SPIRVTerminationInst");
        put("OpBranchConditional", "SPIRVTerminationInst");
        put("OpSwitch", "SPIRVTerminationInst");
        put("OpReturn", "SPIRVTerminationInst");
        put("OpReturnValue", "SPIRVTerminationInst");
        put("OpKill", "SPIRVTerminationInst");
        put("OpUnreachable", "SPIRVTerminationInst");

        put("OpTypeVoid", "SPIRVTypeInst");
        put("OpTypeBool", "SPIRVTypeInst");
        put("OpTypeInt", "SPIRVTypeInst");
        put("OpTypeFloat", "SPIRVTypeInst");
        put("OpTypeVector", "SPIRVTypeInst");
        put("OpTypeMatrix", "SPIRVTypeInst");
        put("OpTypeImage", "SPIRVTypeInst");
        put("OpTypeSampler", "SPIRVTypeInst");
        put("OpTypeSampledImage", "SPIRVTypeInst");
        put("OpTypeArray", "SPIRVTypeInst");
        put("OpTypeRuntimeArray", "SPIRVTypeInst");
        put("OpTypeStruct", "SPIRVTypeInst");
        put("OpTypeOpaque", "SPIRVTypeInst");
        put("OpTypePointer", "SPIRVTypeInst");
        put("OpTypeFunction", "SPIRVTypeInst");
        put("OpTypeEvent", "SPIRVTypeInst");
        put("OpTypeDeviceEvent", "SPIRVTypeInst");
        put("OpTypeReserveId", "SPIRVTypeInst");
        put("OpTypeQueue", "SPIRVTypeInst");
        put("OpTypePipe", "SPIRVTypeInst");
        put("OpTypeForwardPointer", "SPIRVTypeInst");
        put("OpTypePipeStorage", "SPIRVTypeInst");
        put("OpTypeNamedBarrier", "SPIRVTypeInst");
    }
}
