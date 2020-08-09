package uk.ac.manchester.spirvproto.lib;

import org.junit.Test;
import uk.ac.manchester.spirvproto.lib.assembler.InvalidSPIRVModuleException;
import uk.ac.manchester.spirvproto.lib.assembler.SPIRVModule;
import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.*;

import java.nio.ByteBuffer;

public class AssemblerTest {

    @Test
    public void testSPIRVModule() throws InvalidSPIRVModuleException {
        SPIRVModule module = new SPIRVModule(new SPIRVOpMemoryModel(SPIRVAddressingModel.Physical32(), SPIRVMemoryModel.OpenCL()));

        module.add(new SPIRVOpCapability(SPIRVCapability.Addresses()));
        module.add(new SPIRVOpCapability(SPIRVCapability.Linkage()));
        module.add(new SPIRVOpCapability(SPIRVCapability.Kernel()));

        SPIRVId opTypeInt = new SPIRVId(1);
        module.add(new SPIRVOpTypeInt(opTypeInt, new SPIRVLiteralInteger(32), new SPIRVLiteralInteger(0)));

        SPIRVId opTypeVoid = new SPIRVId(2);
        module.add(new SPIRVOpTypeVoid(opTypeVoid));

        SPIRVId intPointer = new SPIRVId(3);
        module.add(new SPIRVOpTypePointer(intPointer, SPIRVStorageClass.CrossWorkgroup(), opTypeInt));

        SPIRVId functionType = new SPIRVId(4);
        module.add(new SPIRVOpTypeFunction(functionType, opTypeVoid, new SPIRVMultipleOperands<>(intPointer, intPointer, intPointer)));

        SPIRVId function = new SPIRVId(5);
        SPIRVId param1 = new SPIRVId(6);
        SPIRVId param2 = new SPIRVId(7);
        SPIRVId param3 = new SPIRVId(8);
        module.createFunctionDeclaration(
                opTypeVoid,
                functionType,
                function,
                SPIRVFunctionControl.DontInline(),
                new SPIRVOpFunctionParameter(opTypeInt, param1),
                new SPIRVOpFunctionParameter(opTypeInt, param2),
                new SPIRVOpFunctionParameter(opTypeInt, param3)
        );

        SPIRVId vector = new SPIRVId(11);
        module.add(new SPIRVOpTypeVector(vector, opTypeInt, new SPIRVLiteralInteger(3)));
        SPIRVId pointer = new SPIRVId(10);
        module.add(new SPIRVOpTypePointer(pointer, SPIRVStorageClass.Input(), vector));
        SPIRVId input = new SPIRVId(9);
        module.add(new SPIRVOpVariable(pointer, input, SPIRVStorageClass.Input(), new SPIRVOptionalOperand<>()));
        module.add(new SPIRVOpEntryPoint(
                SPIRVExecutionModel.Kernel(),
                function,
                new SPIRVLiteralString("vector_add"),
                new SPIRVMultipleOperands<>(input)
        ));

        ByteBuffer out = ByteBuffer.allocate(module.getByteCount());
        module.validate().write(out);
        TestUtils.writeBufferToFile(out, "/home/beehive-lab/Development/OpenCL-SPIRV/test.spv");
    }


}