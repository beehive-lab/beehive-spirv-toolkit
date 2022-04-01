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
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.*;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.*;

import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.processing.Generated;

@Generated("beehive-lab.spirvbeehivetoolkit.generator")
public class SPIRVInstMapper {
    public static SPIRVInstruction createInst(SPIRVLine line, SPIRVInstScope scope) throws InvalidSPIRVOpcodeException {
        SPIRVInstruction instruction;
        int opCode = line.next();
        switch (opCode) {
<#assign newList = [] />
<#list instructions as instruction>
<#if ! newList?seq_contains(instruction.opCode)>
<#assign newList = newList + [instruction.opCode] />
            case ${instruction.opCode?string.computer}: instruction = create${instruction.name}(line, scope); break;
</#if>
</#list>

            default: throw new InvalidSPIRVOpcodeException(opCode);
        }

        return instruction;
    }

<#list instructions as instruction>
    private static SPIRVInstruction create${instruction.name}(SPIRVLine operands, SPIRVInstScope scope) {
        <#if instruction.operands ??><#list instruction.operands as operand>
        <#if operand.quantifier == '*'>
        SPIRVMultipleOperands<SPIRV${operand.kind}> operand${operand?counter} = new SPIRVMultipleOperands<>();
        while (operands.hasNext()) operand${operand?counter}.add(SPIRVOperandMapper.map${operand.kind}(<@mapperParams operand/>));
        <#elseif operand.quantifier == '?'>
        SPIRVOptionalOperand<SPIRV${operand.kind}> operand${operand?counter} = new SPIRVOptionalOperand<>();
        if (operands.hasNext()) operand${operand?counter}.setValue(SPIRVOperandMapper.map${operand.kind}(<@mapperParams operand/>));
        <#else>
        SPIRV${operand.kind} operand${operand?counter} = SPIRVOperandMapper.map${operand.kind}(<@mapperParams operand/>);
        </#if>
        </#list></#if>
        return new SPIRV${instruction.name}(<#if instruction.operands ??><#list instruction.operands as operand>operand${operand?counter}<#sep>, </#sep></#list></#if>);
    }

</#list>
}

<#macro mapperParams operand>operands, scope<#if operand.kind == "LiteralContextDependentNumber">, operand1</#if></#macro>