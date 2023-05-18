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

package uk.ac.manchester.beehivespirvtoolkit.lib.instructions;

import uk.ac.manchester.beehivespirvtoolkit.lib.disassembler.SPIRVPrintingOptions;
import uk.ac.manchester.beehivespirvtoolkit.lib.instructions.operands.*;

import javax.annotation.Generated;
import java.io.PrintStream;
import java.nio.ByteBuffer;

@Generated("beehive-lab.beehivespirvtoolkit.generator")
public class SPIRV${name} extends ${superClass} {
    <#if operands??>
    <#list operands as operand>
    <#if operand.quantifier == '*'>
    public final SPIRVMultipleOperands<SPIRV${operand.kind}> ${operand.name};
    <#elseif operand.quantifier == '?'>
    public final SPIRVOptionalOperand<SPIRV${operand.kind}> ${operand.name};
    <#else>
    public final SPIRV${operand.kind} ${operand.name};
    </#if>
    </#list>
    </#if>

    public SPIRV${name}(<#if operands??><#list  operands as operand><#if operand.quantifier == '*'>SPIRVMultipleOperands<<#elseif operand.quantifier == '?'>SPIRVOptionalOperand<</#if>SPIRV${operand.kind}<#if operand.quantifier == '*' || operand.quantifier == '?'>></#if> ${operand.name}<#sep>, </#list></#if>) {
        super(${opCode?string.computer}, <#if operands??><#list  operands as operand>${operand.name}.getWordCount() + </#list></#if>1, "${name}"<#if capabilities ??><#list capabilities as capability>, SPIRVCapability.${capability}()</#list></#if>);
        <#if operands??>
        <#list operands as operand>
        this.${operand.name} = ${operand.name};
        </#list>
        </#if>
    }

    @Override
    protected void writeOperands(ByteBuffer output) {
        <#if operands??>
        <#list operands as operand>
        ${operand.name}.write(output);
        </#list>
        </#if>
    }

    @Override
    protected void printOperands(PrintStream output, SPIRVPrintingOptions options) {
        <#if operands??>
        <#list operands as operand> <#if operand.name != "_idResult">
        ${operand.name}.print(output, options);<#sep>
        output.print(" ");</#sep>
        </#if></#list>
        </#if>
    }

    @Override
    public SPIRVId getResultId() {
        <#if hasResult>
        return _idResult;
        <#else>
        return null;
        </#if>
    }

    @Override
    public SPIRVCapability[] getAllCapabilities() {
        SPIRVCapability[] allCapabilities = new SPIRVCapability[this.capabilities.length<#if operands ??><#list operands as operand> + ${operand.name}.getCapabilities().length</#list></#if>];
        int capPos = 0;
        for (SPIRVCapability capability : this.capabilities) {
            allCapabilities[capPos++] = capability;
        }
        <#if operands ??><#list operands as operand>
        for (SPIRVCapability capability : ${operand.name}.getCapabilities()) {
            allCapabilities[capPos++] = capability;
        }
        </#list></#if>

        return allCapabilities;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SPIRV${name}) {
            <#if operands??>
            SPIRV${name} otherInst = (SPIRV${name}) other;
            <#list operands as operand><#if operand.name != "_idResult">
            if (!this.${operand.name}.equals(otherInst.${operand.name})) return false;
            </#if></#list></#if>
            return true;
        }
        else return super.equals(other);
    }
}
