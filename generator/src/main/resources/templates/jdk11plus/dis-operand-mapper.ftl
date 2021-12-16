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

package uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler;

import uk.ac.manchester.spirvbeehivetoolkit.lib.SPIRVInstScope;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVInstruction;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpTypeFloat;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpTypeInt;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.*;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import javax.annotation.processing.Generated;

@Generated("beehive-lab.spirvbeehivetoolkit.generator")
public class SPIRVOperandMapper {
    public static SPIRVId mapId(SPIRVLine operands, SPIRVInstScope scope) {
        return scope.getOrAddId(operands.next());
    }

    public static SPIRVLiteralString mapLiteralString(SPIRVLine operands, SPIRVInstScope scope) {
        StringBuilder sb = new StringBuilder();
        byte[] word;
        boolean lastWord;
        do {
            word = operands.nextInBytes();
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

    public static SPIRVLiteralContextDependentNumber mapLiteralContextDependentNumber(SPIRVLine operands, SPIRVInstScope scope, SPIRVId typeId) {
        SPIRVInstruction type = scope.getInstruction(typeId);
        if (type instanceof SPIRVOpTypeInt) {
            int width = ((SPIRVOpTypeInt) type)._width.value;
            int signedness = ((SPIRVOpTypeInt) type)._signedness.value;

            int widthInWords = width / 32;
            if (widthInWords <= 0) widthInWords = 1;
            byte[][] words = new byte[widthInWords][4];
            for (int i = 0; i < widthInWords; i++) {
                byte[] word = operands.nextInBytes();
                words[i][0] = word[0];
                words[i][1] = word[1];
                words[i][2] = word[2];
                words[i][3] = word[3];
            }

            byte[] numberInBytes = new byte[widthInWords * 4];
            for (int i = widthInWords - 1; i >= 0; i--) {
                int arrayIndex = i * 4;
                int wordIndex = widthInWords - i - 1;
                numberInBytes[arrayIndex + 0] = words[wordIndex][3];
                numberInBytes[arrayIndex + 1] = words[wordIndex][2];
                numberInBytes[arrayIndex + 2] = words[wordIndex][1];
                numberInBytes[arrayIndex + 3] = words[wordIndex][0];
            }

            if (width <= 32) return new SPIRVContextDependentInt(new BigInteger(1 - signedness, numberInBytes));
            if (width == 64) return new SPIRVContextDependentLong(new BigInteger(1 - signedness, numberInBytes));

            throw new RuntimeException("OpTypeInt cannot have width of " + width);
        }

        if (type instanceof SPIRVOpTypeFloat) {
            int width = ((SPIRVOpTypeFloat) type)._width.value;

            byte[][] words = new byte[width / 32][4];
            for (int i = 0; i < width / 32; i++) {
                byte[] word = operands.nextInBytes();
                words[i][0] = word[0];
                words[i][1] = word[1];
                words[i][2] = word[2];
                words[i][3] = word[3];
            }

            byte[] numberInBytes = new byte[width / 8];
            for (int i = width / 32 - 1; i >= 0; i--) {
                int arrayIndex = ((width / 32 - 1) - i) * 4;
                numberInBytes[arrayIndex + 0] = words[i][3];
                numberInBytes[arrayIndex + 1] = words[i][2];
                numberInBytes[arrayIndex + 2] = words[i][1];
                numberInBytes[arrayIndex + 3] = words[i][0];
            }

            if (width == 32) return new SPIRVContextDependentFloat(Float.intBitsToFloat(ByteBuffer.wrap(numberInBytes).getInt()));
            if (width == 64) return new SPIRVContextDependentDouble(Double.longBitsToDouble(ByteBuffer.wrap(numberInBytes).getLong()));

            throw new RuntimeException("OpTypeInt cannot have width of " + width);
        }

        throw new RuntimeException("Unknown type for ContextDependentLiteral: " + type.getClass().getName());
    }

    public static SPIRVLiteralExtInstInteger mapLiteralExtInstInteger(SPIRVLine operands, SPIRVInstScope scope) {
        int opCode = operands.next();
        return new SPIRVLiteralExtInstInteger(opCode, SPIRVExtInstMapper.get(opCode));
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
            default: throw new InvalidSPIRVEnumerantException("${operand.kind}", Integer.toString(value));
        }
</#macro>

<#macro bitenum operand>
        int value = operands.next();
        SPIRV${operand.kind} retVal = SPIRV${operand.kind}.Init();
        <#list operand.enumerants as enum>
        if ((value & ${enum.value}) != 0) {
            <#if enum.parameters ??><#list enum.parameters as param>
            SPIRV${param.kind} operand${param?counter} = map${param.kind}(operands, scope);
            </#list></#if>
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