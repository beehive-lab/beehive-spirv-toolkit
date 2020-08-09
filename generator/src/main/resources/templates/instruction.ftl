package uk.ac.manchester.spirvproto.lib.instructions;

import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRV${name} extends ${superClass} {
    <#if operands??>
    <#list operands as operand>
    <#if operand.quantifier == '*'>
    private final SPIRVMultipleOperands<SPIRV${operand.kind}> ${operand.name};
    <#elseif operand.quantifier == '?'>
    private final SPIRVOptionalOperand<SPIRV${operand.kind}> ${operand.name};
    <#else>
    private final SPIRV${operand.kind} ${operand.name};
    </#if>
    </#list>
    </#if>

    public SPIRV${name}(<#if operands??><#list  operands as operand><#if operand.quantifier == '*'>SPIRVMultipleOperands<<#elseif operand.quantifier == '?'>SPIRVOptionalOperand<</#if>SPIRV${operand.kind}<#if operand.quantifier == '*' || operand.quantifier == '?'>></#if> ${operand.name}<#sep>, </#list></#if>) {
        super(${opCode?string.computer}, <#if operands??><#list  operands as operand>${operand.name}.getWordCount() + </#list></#if>1);
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
}
