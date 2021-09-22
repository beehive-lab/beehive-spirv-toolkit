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

package uk.ac.manchester.spirvbeehivetoolkit.lib;

import org.junit.Test;
import uk.ac.manchester.spirvbeehivetoolkit.lib.assembler.Assembler;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpCapability;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpEntryPoint;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpFunction;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpFunctionEnd;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpFunctionParameter;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpIAdd;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpLabel;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpLoad;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpMemoryModel;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpReturn;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpStore;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpTypeFunction;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpTypeInt;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpTypePointer;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpTypeVector;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpTypeVoid;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.SPIRVOpVariable;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVAddressingModel;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVCapability;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVExecutionModel;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVFunctionControl;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVId;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVLiteralInteger;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVLiteralString;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVMemoryAccess;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVMemoryModel;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVMultipleOperands;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVOptionalOperand;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.SPIRVStorageClass;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.*;
import uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AssemblerTest {

    @Test
    public void testSPIRVModule() throws InvalidSPIRVModuleException {
        SPIRVModule module = new SPIRVModule(new SPIRVHeader(1, 2, 0, 0, 0));
        SPIRVInstScope functionScope;
        SPIRVInstScope blockScope;

        module.add(new SPIRVOpCapability(SPIRVCapability.Addresses()));
        module.add(new SPIRVOpCapability(SPIRVCapability.Linkage()));
        module.add(new SPIRVOpCapability(SPIRVCapability.Kernel()));

        module.add(new SPIRVOpMemoryModel(SPIRVAddressingModel.Physical64(), SPIRVMemoryModel.OpenCL()));

        SPIRVId opTypeInt = module.getNextId();
        module.add(new SPIRVOpTypeInt(opTypeInt, new SPIRVLiteralInteger(32), new SPIRVLiteralInteger(0)));

        SPIRVId opTypeVoid = module.getNextId();
        module.add(new SPIRVOpTypeVoid(opTypeVoid));

        SPIRVId intPointer = module.getNextId();
        module.add(new SPIRVOpTypePointer(intPointer, SPIRVStorageClass.CrossWorkgroup(), opTypeInt));

        SPIRVId functionType = module.getNextId();
        module.add(new SPIRVOpTypeFunction(
                functionType,
                opTypeVoid,
                new SPIRVMultipleOperands<>(intPointer, intPointer, intPointer)
        ));

        SPIRVId function = module.getNextId();
        SPIRVId vector = module.getNextId();
        module.add(new SPIRVOpTypeVector(vector, opTypeInt, new SPIRVLiteralInteger(3)));
        SPIRVId pointer = module.getNextId();
        module.add(new SPIRVOpTypePointer(pointer, SPIRVStorageClass.Input(), vector));
        SPIRVId input = module.getNextId();
        module.add(new SPIRVOpVariable(pointer, input, SPIRVStorageClass.Input(), new SPIRVOptionalOperand<>()));
        module.add(new SPIRVOpEntryPoint(
                SPIRVExecutionModel.Kernel(),
                function,
                new SPIRVLiteralString("vector_add"),
                new SPIRVMultipleOperands<>(input)
        ));

        SPIRVId functionDef = module.getNextId();
        functionScope = module.add(new SPIRVOpFunction(opTypeVoid, functionDef, SPIRVFunctionControl.DontInline(), functionType));
        SPIRVId defParam1 = module.getNextId();
        functionScope.add(new SPIRVOpFunctionParameter(opTypeInt, defParam1));
        SPIRVId defParam2= module.getNextId();
        functionScope.add(new SPIRVOpFunctionParameter(opTypeInt, defParam2));
        SPIRVId defParam3 = module.getNextId();
        functionScope.add(new SPIRVOpFunctionParameter(opTypeInt, defParam3));

        module.add(new SPIRVOpEntryPoint(
                SPIRVExecutionModel.Kernel(),
                functionDef,
                new SPIRVLiteralString("vector_add"),
                new SPIRVMultipleOperands<>(input)
        ));

        blockScope = functionScope.add(new SPIRVOpLabel(module.getNextId()));
        SPIRVId var1 = module.getNextId();
        blockScope.add(new SPIRVOpVariable(
                pointer,
                var1,
                SPIRVStorageClass.Function(),
                new SPIRVOptionalOperand<>()
        ));
        SPIRVId var4 = module.getNextId();
        blockScope.add(new SPIRVOpVariable(
                intPointer,
                var4,
                SPIRVStorageClass.Function(),
                new SPIRVOptionalOperand<>()
        ));

        SPIRVId load = module.getNextId();
        blockScope.add(new SPIRVOpLoad(
                vector,
                load,
                input,
                new SPIRVOptionalOperand<>(SPIRVMemoryAccess.Aligned(new SPIRVLiteralInteger(16)))
        ));

        SPIRVId add = module.getNextId();
        blockScope.add(new SPIRVOpIAdd(opTypeInt, add, var4, load));

        blockScope.add(new SPIRVOpStore(
                var1,
                add,
                new SPIRVOptionalOperand<>(SPIRVMemoryAccess.Aligned(new SPIRVLiteralInteger(4)))
        ));
        blockScope.add(new SPIRVOpReturn());
        functionScope.add(new SPIRVOpFunctionEnd());

        functionScope = module.add(new SPIRVOpFunction(opTypeVoid, function, SPIRVFunctionControl.DontInline(), functionType));
        SPIRVId param1 = module.getNextId();
        functionScope.add(new SPIRVOpFunctionParameter(opTypeInt, param1));
        SPIRVId param2 = module.getNextId();
        functionScope.add(new SPIRVOpFunctionParameter(opTypeInt, param2));
        SPIRVId param3 = module.getNextId();
        functionScope.add(new SPIRVOpFunctionParameter(opTypeInt, param3));
        functionScope.add(new SPIRVOpFunctionEnd());

        TestUtils.writeModuleToFile(module, "../test.spv");
    }

    @Test
    public void testAssembler() throws IOException, InvalidSPIRVModuleException {
        SPIRVModule module = new Assembler(new FileReader(new File("../examples/vector_add/vector_add.spvasm"))).assemble();
        TestUtils.writeModuleToFile(module, "../examples/asm-vector_add.spv");
    }

}