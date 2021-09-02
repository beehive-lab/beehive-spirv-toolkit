/*
 * MIT License
 *
 * Copyright (c) 2021, APT Group, Department of Computer Science,
 * The University of Manchester.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
