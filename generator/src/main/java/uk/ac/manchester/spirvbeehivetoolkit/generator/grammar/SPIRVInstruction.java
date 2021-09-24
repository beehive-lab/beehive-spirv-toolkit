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

package uk.ac.manchester.spirvbeehivetoolkit.generator.grammar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVInstruction {
    @JsonProperty("opname")
    public String name;

    @JsonProperty("opcode")
    public int opCode;

    @JsonProperty("operands")
    public SPIRVOperand[] operands;

    @JsonProperty("capabilities")
    public String[] capabilities;

    public String superClass;

    public boolean hasReturnType;

    public boolean hasResult;

    public SPIRVInstruction() {
        hasReturnType = false;
        hasResult = false;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return name;
    }

    public int getOpCode() {
        return opCode;
    }

    public SPIRVOperand[] getOperands() {
        return operands;
    }

    public String getSuperClass() {
        return superClass;
    }

    public boolean getHasReturnType() {
        return hasReturnType;
    }

    public boolean getHasResult() {
        return hasResult;
    }

    public String[] getCapabilities() {
        return capabilities;
    }
}
