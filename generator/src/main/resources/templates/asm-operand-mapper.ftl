package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import java.util.Iterator;

public class SPIRVOperandMapper {
    public static SPIRVId mapId(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        return scope.getOrCreateId(tokens.next().value);
    }

    public static SPIRVLiteralString mapLiteralString(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        SPIRVToken token = tokens.next();
        return new SPIRVLiteralString(token.value.substring(1, token.value.length() - 1));
    }

    public static SPIRVLiteralInteger mapLiteralInteger(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        return new SPIRVLiteralInteger(Integer.parseInt(tokens.next().value));
    }

<#list operandKinds as operand>
    public static SPIRV${operand.kind} map${operand.kind}(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        <#if operand.category == "ValueEnum">
        SPIRVToken token = tokens.next();
        switch(token.value) {
            <#list operand.enumerants as enum>
            case "${enum.name}": {
                <#if enum.parameters ??><#list enum.parameters as param>
                SPIRV${param.kind} operand${param?counter} = SPIRVOperandMapper.map${param.kind}(tokens, scope);
                </#list></#if>
                return SPIRV${operand.kind}.${enum.name}(<#if enum.parameters ??><#list enum.parameters as param>operand${param?counter}<#sep>, </#sep></#list></#if>);
            }
            </#list>
            default: throw new IllegalArgumentException("${operand.kind}: " + token.value);
        }
        <#elseif operand.category == "BitEnum">
        String[] values = tokens.next().value.split("\\|");
        SPIRV${operand.kind} retVal = SPIRV${operand.kind}.None();
        for (String value : values) {
            switch (value) {
                <#list operand.enumerants as enum>
                case "${enum.name}": {
                    <#if enum.parameters ??><#list enum.parameters as param>
                    SPIRV${param.kind} operand${param?counter} = SPIRVOperandMapper.map${param.kind}(tokens, scope);
                    </#list></#if>
                    retVal.add(SPIRV${operand.kind}.${enum.name}(<#if enum.parameters ??><#list enum.parameters as param>operand${param?counter}<#sep>, </#sep></#list></#if>));
                    break;
                }
                </#list>
                default: throw new IllegalArgumentException("${operand.kind}: " + value);
            }
        }
        return retVal;
        <#else>
        return null;
        </#if>
    }

    </#list>
}
