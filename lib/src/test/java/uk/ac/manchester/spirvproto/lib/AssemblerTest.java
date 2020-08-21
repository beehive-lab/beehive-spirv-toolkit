package uk.ac.manchester.spirvproto.lib;

import org.junit.Test;
import uk.ac.manchester.spirvproto.lib.assembler.Assembler;
import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

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

        module.add(new SPIRVOpMemoryModel(SPIRVAddressingModel.Physical32(), SPIRVMemoryModel.OpenCL()));

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
        SPIRVModule module = new Assembler(new FileReader(new File("../examples/vector_add.spv.dis"))).assemble();
        TestUtils.writeModuleToFile(module, "../examples/asm-vector_add.spv");
    }

}