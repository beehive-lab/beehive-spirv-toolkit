package uk.ac.manchester.spirvbeehivetoolkit.generator;

import java.util.HashMap;

public class SPIRVInstructionSuperClassMapping extends HashMap<String, String> {

    public SPIRVInstructionSuperClassMapping() {
        super(53);

        put("OpModuleProcessed", "SPIRVDebugInst");

        put("OpVariable", "SPIRVGlobalInst");
        put("OpConstant", "SPIRVGlobalInst");
        put("OpConstantTrue", "SPIRVGlobalInst");
        put("OpConstantFalse", "SPIRVGlobalInst");
        put("OpConstantComposite", "SPIRVGlobalInst");
        put("OpConstantSampler", "SPIRVGlobalInst");
        put("OpConstantNull", "SPIRVGlobalInst");
        put("OpConstantPipeStorage", "SPIRVGlobalInst");
        put("OpUndef", "SPIRVGlobalInst");
        put("OpTypeVoid", "SPIRVGlobalInst");
        put("OpTypeBool", "SPIRVGlobalInst");
        put("OpTypeInt", "SPIRVGlobalInst");
        put("OpTypeFloat", "SPIRVGlobalInst");
        put("OpTypeVector", "SPIRVGlobalInst");
        put("OpTypeMatrix", "SPIRVGlobalInst");
        put("OpTypeImage", "SPIRVGlobalInst");
        put("OpTypeSampler", "SPIRVGlobalInst");
        put("OpTypeSampledImage", "SPIRVGlobalInst");
        put("OpTypeArray", "SPIRVGlobalInst");
        put("OpTypeRuntimeArray", "SPIRVGlobalInst");
        put("OpTypeStruct", "SPIRVGlobalInst");
        put("OpTypeOpaque", "SPIRVGlobalInst");
        put("OpTypePointer", "SPIRVGlobalInst");
        put("OpTypeFunction", "SPIRVGlobalInst");
        put("OpTypeEvent", "SPIRVGlobalInst");
        put("OpTypeDeviceEvent", "SPIRVGlobalInst");
        put("OpTypeReserveId", "SPIRVGlobalInst");
        put("OpTypeQueue", "SPIRVGlobalInst");
        put("OpTypePipe", "SPIRVGlobalInst");
        put("OpTypeForwardPointer", "SPIRVGlobalInst");
        put("OpTypePipeStorage", "SPIRVGlobalInst");
        put("OpTypeNamedBarrier", "SPIRVGlobalInst");

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
    }
}
