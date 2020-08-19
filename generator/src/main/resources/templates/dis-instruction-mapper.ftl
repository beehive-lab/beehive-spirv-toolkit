package uk.ac.manchester.spirvproto.lib.disassembler;

import uk.ac.manchester.spirvproto.lib.assembler.SPIRVInstScope;
import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import java.util.Arrays;
import java.util.Iterator;

public class SPIRVInstMapper {
    public static SPIRVInstruction createInst(SPIRVLine line, SPIRVInstScope scope) {
        SPIRVInstruction instruction;
        int opCode = line.next();
        switch (opCode) {
<#list instructions as instruction>
            case ${instruction.opCode?string.computer}: instruction = create${instruction.name}(line, scope); break;
</#list>
            default: throw new IllegalArgumentException("No operation with opcode: " + opCode);
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