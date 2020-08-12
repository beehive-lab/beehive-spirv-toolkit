package uk.ac.manchester.spirvproto.lib;

import org.junit.Test;
import uk.ac.manchester.spirvproto.lib.assembler.*;
import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AssemblerTest {

    @Test
    public void testSPIRVModule() throws InvalidSPIRVModuleException {
        SPIRVModule module = new SPIRVModule();

        module.add(new SPIRVOpMemoryModel(SPIRVAddressingModel.Physical32(), SPIRVMemoryModel.OpenCL()));

        module.add(new SPIRVOpCapability(SPIRVCapability.Addresses()));
        module.add(new SPIRVOpCapability(SPIRVCapability.Linkage()));
        module.add(new SPIRVOpCapability(SPIRVCapability.Kernel()));

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
        SPIRVId param1 = module.getNextId();
        SPIRVId param2 = module.getNextId();
        SPIRVId param3 = module.getNextId();
        module.createFunctionDeclaration(
                opTypeVoid,
                functionType,
                function,
                SPIRVFunctionControl.DontInline(),
                new SPIRVOpFunctionParameter(opTypeInt, param1),
                new SPIRVOpFunctionParameter(opTypeInt, param2),
                new SPIRVOpFunctionParameter(opTypeInt, param3)
        );

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
        SPIRVId defParam1 = module.getNextId();
        SPIRVId defParam2= module.getNextId();
        SPIRVId defParam3 = module.getNextId();
        SPIRVFunctionDefinition vecAdd = module.createFunctionDefinition(
                opTypeVoid,
                functionType,
                functionDef,
                SPIRVFunctionControl.DontInline(),
                new SPIRVOpFunctionParameter(opTypeInt, defParam1),
                new SPIRVOpFunctionParameter(opTypeInt, defParam2),
                new SPIRVOpFunctionParameter(opTypeInt, defParam3)
        );

        module.add(new SPIRVOpEntryPoint(
                SPIRVExecutionModel.Kernel(),
                functionDef,
                new SPIRVLiteralString("vector_add"),
                new SPIRVMultipleOperands<>(input)
        ));

        SPIRVBlock theBlock = vecAdd.addBlock();
        SPIRVId var1 = module.getNextId();
        theBlock.addInstruction(new SPIRVOpVariable(
                pointer,
                var1,
                SPIRVStorageClass.Function(),
                new SPIRVOptionalOperand<>()
        ));
        SPIRVId var4 = module.getNextId();
        theBlock.addInstruction(new SPIRVOpVariable(
                intPointer,
                var4,
                SPIRVStorageClass.Function(),
                new SPIRVOptionalOperand<>()
        ));

        SPIRVId load = module.getNextId();
        theBlock.addInstruction(new SPIRVOpLoad(
                vector,
                load,
                input,
                new SPIRVOptionalOperand<>(SPIRVMemoryAccess.Aligned(new SPIRVLiteralInteger(16)))
        ));

        SPIRVId add = module.getNextId();
        theBlock.addInstruction(new SPIRVOpIAdd(opTypeInt, add, var4, load));

        theBlock.addInstruction(new SPIRVOpStore(
                var1,
                add,
                new SPIRVOptionalOperand<>(SPIRVMemoryAccess.Aligned(new SPIRVLiteralInteger(4)))
        ));
        theBlock.addInstruction(new SPIRVOpReturn());

        TestUtils.writeModuleToFile(module, "/home/beehive-lab/Development/OpenCL-SPIRV/test.spv");
    }

    @Test
    public void testAssembler() throws IOException, InvalidSPIRVModuleException {
        SPIRVModule module = new Assembler(new FileReader(new File("/home/beehive-lab/Development/OpenCL-SPIRV/spirv-proto/examples/vector_add.spv.dis"))).assemble();
        TestUtils.writeModuleToFile(module, "/home/beehive-lab/Development/OpenCL-SPIRV/spirv-proto/examples/vector_add.spv");
    }

}