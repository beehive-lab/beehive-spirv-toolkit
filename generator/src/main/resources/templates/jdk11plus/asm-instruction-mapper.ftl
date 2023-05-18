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

package uk.ac.manchester.beehivespirvtoolkit.lib.assembler;

import uk.ac.manchester.beehivespirvtoolkit.lib.SPIRVInstScope;
import uk.ac.manchester.beehivespirvtoolkit.lib.instructions.*;
import uk.ac.manchester.beehivespirvtoolkit.lib.instructions.operands.*;

import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.processing.Generated;

@Generated("beehive-lab.beehivespirvtoolkit.generator")
class SPIRVInstMapper {
    public static SPIRVInstruction createInst(SPIRVToken instruction, SPIRVToken[] tokens, SPIRVInstScope scope) {
        Iterator<SPIRVToken> tokenIterator = Arrays.stream(tokens).iterator();
        SPIRVInstruction decoded;
        switch (instruction.value) {
<#list instructions as instruction>
            case "${instruction.name}": decoded = create${instruction.name}(tokenIterator, scope); break;
            </#list>
            default: throw new IllegalArgumentException(instruction.value);
        }

        return decoded;
    }

    <#list instructions as instruction>
    private static SPIRVInstruction create${instruction.name}(Iterator<SPIRVToken> tokens, SPIRVInstScope scope) {
        <#if instruction.operands??><#list instruction.operands as operand>
        <#if operand.quantifier == '*'>
        SPIRVMultipleOperands<SPIRV${operand.kind}> operand${operand?counter} = new SPIRVMultipleOperands<>();
        while (tokens.hasNext()) operand${operand?counter}.add(SPIRVOperandMapper.map${operand.kind}(<@mapperParams operand operand?counter/>));
        <#elseif operand.quantifier == '?'>
        SPIRVOptionalOperand<SPIRV${operand.kind}> operand${operand?counter} = new SPIRVOptionalOperand<>();
        if (tokens.hasNext()) operand${operand?counter}.setValue(SPIRVOperandMapper.map${operand.kind}(<@mapperParams operand operand?counter/>));
        <#else>
        SPIRV${operand.kind} operand${operand?counter} = SPIRVOperandMapper.map${operand.kind}(<@mapperParams operand operand?counter/>);
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

<#macro mapperParams operand counter>tokens, scope<#if operand.kind == "LiteralContextDependentNumber">, operand${counter - 1}</#if></#macro>