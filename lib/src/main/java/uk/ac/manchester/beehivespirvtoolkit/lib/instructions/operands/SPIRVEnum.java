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
package uk.ac.manchester.beehivespirvtoolkit.lib.instructions.operands;

import uk.ac.manchester.beehivespirvtoolkit.lib.disassembler.SPIRVPrintingOptions;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.List;

public abstract class SPIRVEnum implements SPIRVOperand {
    protected int value;
    public String name;
    protected List<SPIRVOperand> parameters;
    public SPIRVCapability[] capabilities;

    protected SPIRVEnum(int value, String name, List<SPIRVOperand> parameters, SPIRVCapability... capabilities) {
        this.value = value;
        this.name = name;
        this.parameters = parameters;
        this.capabilities = capabilities;
    }

    @Override
    public void write(ByteBuffer output) {
        output.putInt(value);
    }

    @Override
    public int getWordCount() {
        return 1 + parameters.stream().mapToInt(SPIRVOperand::getWordCount).sum();
    }

    @Override
    public void print(PrintStream output, SPIRVPrintingOptions options) {
        output.print(name);
        if (parameters.size() > 0) output.print(" ");
        for (int i = 0, parametersSize = parameters.size(); i < parametersSize; i++) {
            SPIRVOperand p = parameters.get(i);
            p.print(output, options);
            if (i < parameters.size() - 1) output.print(" ");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SPIRVEnum) {
            SPIRVEnum otherEnum = (SPIRVEnum) other;
            if (this.value != otherEnum.value) return false;
            if (!this.name.equals(otherEnum.name)) return false;

            return this.parameters.equals(otherEnum.parameters);
        }

        return super.equals(other);
    }

    @Override
    public SPIRVCapability[] getCapabilities() {
        return capabilities;
    }
}
