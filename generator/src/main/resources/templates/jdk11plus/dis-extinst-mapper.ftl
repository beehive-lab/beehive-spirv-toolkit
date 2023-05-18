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

package uk.ac.manchester.beehivespirvtoolkit.lib.disassembler;

import java.util.HashMap;
import javax.annotation.processing.Generated;

/**
 * SPIR-V handles built in functions by OpenCL or GLSL by importing their standard.
 * This standard contains all of the functions provided by them and
 * a mapping between the function names and corresponding numbers.
 */
@Generated("beehive-lab.beehivespirvtoolkit.generator")
public class SPIRVExtInstMapper {
    private static HashMap<Integer, String> extInstNameMap;

    /**
     * Get the name of the function mapped to the given number.
     * @param opCode - The number of the external function
     * @return The name of the external function mapped to this number.
     */
    public static String get(int opCode) {
        return extInstNameMap.get(opCode);
    }

    /**
     * Load all of the mapped values into memory (otherwise it is not available in order save space)
     */
    public static void loadOpenCL() {
        extInstNameMap = new HashMap<>(${instructions?size?string.computer});
        <#list instructions as instruction>
        extInstNameMap.put(${instruction.opCode?string.computer}, "${instruction.opName}");
        </#list>
    }
}