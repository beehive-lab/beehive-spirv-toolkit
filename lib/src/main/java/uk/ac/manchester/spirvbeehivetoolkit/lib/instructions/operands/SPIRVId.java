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
package uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands;

import uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler.SPIRVPrintingOptions;

import java.io.PrintStream;
import java.nio.ByteBuffer;

public class SPIRVId implements SPIRVOperand {
    public final int id;
    public SPIRVCapability[] capabilities;
    private String name;

    public SPIRVId(int id) {
        this.id = id;
        name = null;
        capabilities = new SPIRVCapability[0];
    }

    @Override
    public void write(ByteBuffer output) {
        output.putInt(id);
    }

    @Override
    public int getWordCount() {
        return 1;
    }

    @Override
    public SPIRVCapability[] getCapabilities() {
        return capabilities;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public void print(PrintStream output, SPIRVPrintingOptions options) {
        checkName(options.shouldInlineNames);
        output.print(options.highlighter.highlightId("%" + name));
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SPIRVId) return this.id == ((SPIRVId) other).id;
        return super.equals(other);
    }

    public int nameSize(boolean inlineNames) {
        checkName(inlineNames);
        return name.length() + 1;
    }

    private void checkName(boolean inlineNames) {
        if (!inlineNames || name == null) name = Integer.toString(id);
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        if (name == null) return "";
        return name;
    }
}
