package uk.ac.manchester.spirvproto.lib.instructions;

import uk.ac.manchester.spirvproto.lib.disassembler.SPIRVPrintingOptions;
import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import javax.annotation.Generated;
import java.io.PrintStream;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
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
        super(${opCode?string.computer}, <#if operands??><#list  operands as operand>${operand.name}.getWordCount() + </#list></#if>1, "${name}");
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
