package uk.ac.manchester.spirvproto.lib.instructions;

import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRV${name} extends SPIRVInstruction {
    <#if operands??>
    <#list operands as operand>
    private final SPIRV${operand.kind} <#if operand.name??>${operand.name?replace("'", "")?replace(" ", "")?uncap_first}<#else>${operand.kind?uncap_first}</#if>;
    </#list>
    </#if>

    public SPIRV${name}(<#if operands??><#list  operands as operand>SPIRV${operand.kind} <#if operand.name??>${operand.name?replace("'", "")?replace(" ", "")?uncap_first}<#else>${operand.kind?uncap_first}</#if><#sep>, </#list></#if>) {
        super(${opCode?string.computer}, <#if operands??><#list  operands as operand><#if operand.name??>${operand.name?replace("'", "")?replace(" ", "")?uncap_first}<#else>${operand.kind?uncap_first}</#if>.getWordCount()<#sep> + </#list></#if>);
        <#if operands??>
        <#list operands as operand>
        this.<#if operand.name??>${operand.name?replace("'", "")?replace(" ", "")?uncap_first}<#else>${operand.kind?uncap_first}</#if> = <#if operand.name??>${operand.name?replace("'", "")?replace(" ", "")?uncap_first}<#else>${operand.kind?uncap_first}</#if>;
        </#list>
        </#if>
    }

    @Override
    protected void writeOperands(ByteBuffer output) {
    <#if operands??>
    <#list operands as operand>
        <#if operand.name??>${operand.name?replace("'", "")?replace(" ", "")?uncap_first}<#else>${operand.kind?uncap_first}</#if>.write(output);
    </#list>
    </#if>
    }
}
