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
import java.util.ArrayList;
import java.util.Collections;

public class SPIRVMultipleOperands<T extends SPIRVOperand> extends ArrayList<T> implements SPIRVOperand {
    public final SPIRVCapability[] capabilities;

    @SafeVarargs
    public SPIRVMultipleOperands(T... operands) {
        Collections.addAll(this, operands);

        // Since these operands need to be the same type they will have the same capabilities
        if (operands.length > 0) capabilities = operands[0].getCapabilities();
        else capabilities = new SPIRVCapability[0];
    }

    @Override
    public void write(ByteBuffer output) {
        this.forEach(spirvOperand -> spirvOperand.write(output));
    }

    @Override
    public int getWordCount() {
        return this.stream().mapToInt(SPIRVOperand::getWordCount).sum();
    }

    @Override
    public SPIRVCapability[] getCapabilities() {
        return new SPIRVCapability[0];
    }

    @Override
    public void print(PrintStream output, SPIRVPrintingOptions options) {
        this.forEach(o -> {
            o.print(output, options);
            output.print(" ");
        });
    }
}
