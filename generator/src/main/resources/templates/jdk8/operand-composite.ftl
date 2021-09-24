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

import javax.annotation.Generated;
import java.io.PrintStream;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirvbeehivetoolkit.generator")
public class SPIRV${kind} implements SPIRVOperand {
    <#list bases as base>
    private final SPIRV${base} member${base?counter};
    </#list>

    public final SPIRVCapability[] capabilities;

    public SPIRV${kind}(<#list bases as base>SPIRV${base} member${base?counter}<#sep>, </#sep></#list>) {
        <#list bases as base>
        this.member${base?counter} = member${base?counter};
        </#list>

        capabilities = new SPIRVCapability[<#list bases as base>member${base?counter}.getCapabilities().length<#sep> + </#sep></#list>];
        int capPos = 0;
        <#list bases as base>
        for (SPIRVCapability capability : member${base?counter}.getCapabilities()) {
            capabilities[capPos++] = capability;
        }
        </#list>
    }

    @Override
    public void write(ByteBuffer output) {
        <#list bases as base>
        member${base?counter}.write(output);
        </#list>
    }

    @Override
    public int getWordCount() {
        return <#list bases as base>member${base?counter}.getWordCount()<#sep> + </#sep></#list>;
    }

    @Override
    public void print(PrintStream output, SPIRVPrintingOptions options) {
        if (options.shouldGroup) output.print("{");
        <#list bases as base>
        member${base?counter}.print(output, options);
        <#sep>
        output.print(" ");
        </#sep>
        </#list>
        if (options.shouldGroup) output.print("}");
    }

    @Override
    public SPIRVCapability[] getCapabilities() {
        return capabilities;
    }
}
