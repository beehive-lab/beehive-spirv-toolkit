package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import java.util.Arrays;
import java.util.Iterator;

public class SPIRVInstMapper {
    public static SPIRVInstScope addToScope(SPIRVToken instruction, SPIRVToken[] tokens, SPIRVInstScope scope) {
        Iterator<SPIRVToken> tokenIterator = Arrays.stream(tokens).iterator();
        SPIRVInstruction decoded;
        switch (instruction.value) {
<#list instructions as instruction>
            case "${instruction.name}": decoded = create${instruction.name}(tokenIterator, scope); break;
            </#list>
            default: throw new IllegalArgumentException(instruction.value);
        }

        return scope.add(decoded);
    }

    <#list instructions as instruction>
    private static SPIRVInstruction create${instruction.name}(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        <#if instruction.operands??><#list instruction.operands as operand>
        <#if operand.quantifier == '*'>
        SPIRVMultipleOperands<SPIRV${operand.kind}> operand${operand?counter} = new SPIRVMultipleOperands<>();
        while (tokens.hasNext()) operand${operand?counter}.add(SPIRVOperandMapper.map${operand.kind}(tokens, scope));
        <#elseif operand.quantifier == '?'>
        SPIRVOptionalOperand<SPIRV${operand.kind}> operand${operand?counter} = new SPIRVOptionalOperand<>();
        if (tokens.hasNext()) operand${operand?counter}.setValue(SPIRVOperandMapper.map${operand.kind}(tokens, scope));
        <#else>
        SPIRV${operand.kind} operand${operand?counter} = SPIRVOperandMapper.map${operand.kind}(tokens, scope);
        </#if>
        </#list></#if>

        <#if instruction.hasReturnType>
        return new SPIRV${instruction.name}(operand2, operand1<#list instruction.operands as operand><#if operand?counter gt 2>, operand${operand?counter}</#if></#list>);
        <#else>
        return new SPIRV${instruction.name}(<#if instruction.operands??><#list instruction.operands as operand>operand${operand?counter}<#sep>, </#sep></#list></#if>);
        </#if>
    }

    </#list>
}
