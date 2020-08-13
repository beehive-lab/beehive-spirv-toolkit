package uk.ac.manchester.spirvproto.lib.disassembler;

import uk.ac.manchester.spirvproto.lib.assembler.SPIRVInstScope;
import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import java.nio.ByteBuffer;
import java.util.Iterator;

public class SPIRVOperandMapper {
    public static SPIRVId mapId(SPIRVLine operands, SPIRVInstScope scope) {
        return new SPIRVId(operands.next());
    }

    public static SPIRVLiteralString mapLiteralString(SPIRVLine operands, SPIRVInstScope scope) {
        StringBuilder sb = new StringBuilder();
        byte[] word;
        boolean lastWord;
        do {
            word = ByteBuffer.allocate(4).order(operands.getByteOrder()).putInt(operands.next()).array();
            lastWord = word[3] == 0;
            String shard;
            if (lastWord) {
                int zeroIndex = word.length;
                for (int i = 3; i >= 0; i--) {
                    if (word[i] == 0) {
                        zeroIndex = i;
                    }
                }
                shard = new String(word, 0, zeroIndex);
            } else {
                shard = new String(word);
            }
            sb.append(shard);
        } while (!lastWord);

        return new SPIRVLiteralString(sb.toString());
    }

    public static SPIRVLiteralInteger mapLiteralInteger(SPIRVLine operands, SPIRVInstScope scope) {
        return new SPIRVLiteralInteger(operands.next());
    }

<#list operandKinds as operand>
    public static SPIRV${operand.kind} map${operand.kind}(SPIRVLine operands, SPIRVInstScope scope) {
        <#if operand.category == "ValueEnum">
        <@valueenum operand/>
        <#elseif operand.category == "BitEnum">
        <@bitenum operand/>
        <#elseif operand.category == "Literal">
        <@literal operand/>
        <#elseif operand.category == "Composite">
        <@composite operand/>
        </#if>
    }

    </#list>
}

<#macro valueenum operand>
        int value = operands.next();
        switch (value) {
            <#list operand.enumerants as enum>
            case ${enum.value}: {
                <#if enum.parameters ??><#list enum.parameters as param>
                SPIRV${param.kind} operand${param?counter} = map${param.kind}(operands, scope);
                </#list>

                </#if>
                return SPIRV${operand.kind}.${enum.name}(<#if enum.parameters ??><#list enum.parameters as param>operand${param?counter}<#sep>, </#sep></#list></#if>);
            }
            </#list>
            default: throw new IllegalArgumentException("There is no SPIRV${operand.kind} with value" + value);
        }
</#macro>

<#macro bitenum operand>
        int value = operands.next();
        SPIRV${operand.kind} retVal = SPIRV${operand.kind}.None();
        <#list operand.enumerants as enum>
        if ((value & ${enum.value}) != 0) {
            <#if enum.parameters ??><#list enum.parameters as param>
            SPIRV${param.kind} operand${param?counter} = map${param.kind}(operands, scope);
            </#list>

            </#if>
            retVal.add(SPIRV${operand.kind}.${enum.name}(<#if enum.parameters ??><#list enum.parameters as param>operand${param?counter}<#sep>, </#sep></#list></#if>));
        }
        </#list>
        return retVal;
</#macro>

<#macro literal operand>
        return new SPIRV${operand.kind}(operands.next());
</#macro>

<#macro composite operand>
        return new SPIRV${operand.kind}(<#list operand.bases as base>map${base}(operands, scope)<#sep>, </#sep></#list>);
</#macro>