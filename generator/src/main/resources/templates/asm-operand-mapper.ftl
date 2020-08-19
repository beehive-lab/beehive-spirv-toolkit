package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.SPIRVInstScope;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVInstruction;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpTypeFloat;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpTypeInt;
import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import java.math.BigInteger;
import java.util.Iterator;

class SPIRVOperandMapper {
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

    public static SPIRVLiteralContextDependentNumber mapLiteralContextDependentNumber(Iterator<SPIRVToken> tokens, SPIRVInstScope scope, SPIRVId typeId) {
        SPIRVInstruction type = scope.getInstruction(typeId);
        String number = tokens.next().value;
        if (type instanceof SPIRVOpTypeInt) {
            int width = ((SPIRVOpTypeInt) type)._width.value;

            if (width == 32) return new SPIRVContextDependentInt(new BigInteger(number));
            if (width == 64) return new SPIRVContextDependentLong(new BigInteger(number));

            throw new RuntimeException("OpTypeInt cannot have width of " + width);
        }

        if (type instanceof SPIRVOpTypeFloat) {
            int width = ((SPIRVOpTypeFloat) type)._width.value;

            if (width == 32) return new SPIRVContextDependentFloat(Float.parseFloat(number));
            if (width == 64) return new SPIRVContextDependentDouble(Double.parseDouble(number));

            throw new RuntimeException("OpTypeFloat cannot have width of " + width);
        }

        throw new RuntimeException("Unknown type instruction for ContextDependentNumber: " + type.getClass().getName());
    }

    public static SPIRVLiteralExtInstInteger mapLiteralExtInstInteger(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        String name = tokens.next().value;
        return new SPIRVLiteralExtInstInteger(SPIRVExtInstMapper.get(name), name);
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
        <#elseif operand.category == "Composite">
        <#list operand.bases as base>
        SPIRV${base} member${base?counter} = SPIRVOperandMapper.map${base}(tokens, scope);
        </#list>
        return new SPIRV${operand.kind}(<#list operand.bases as base>member${base?counter}<#sep>, </#sep></#list>);
        <#elseif operand.category == "Literal">
        return new SPIRV${operand.kind}(Integer.decode(tokens.next().value));
        <#else>
        throw new RuntimeException("Unsupported operand kind: ${operand.kind}");
        </#if>
    }

    </#list>
}
