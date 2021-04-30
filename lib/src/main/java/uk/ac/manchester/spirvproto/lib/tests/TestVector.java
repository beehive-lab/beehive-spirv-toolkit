package uk.ac.manchester.spirvproto.lib.tests;

import uk.ac.manchester.spirvproto.lib.InvalidSPIRVModuleException;
import uk.ac.manchester.spirvproto.lib.SPIRVHeader;
import uk.ac.manchester.spirvproto.lib.SPIRVInstScope;
import uk.ac.manchester.spirvproto.lib.SPIRVModule;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpCapability;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpCompositeExtract;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpConstant;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpDecorate;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpEntryPoint;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpExtInstImport;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpFunction;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpFunctionEnd;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpFunctionParameter;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpInBoundsPtrAccessChain;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpLabel;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpLoad;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpMemoryModel;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpName;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpReturn;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpSource;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpStore;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpTypeFunction;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpTypeInt;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpTypePointer;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpTypeVector;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpTypeVoid;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpUConvert;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpVariable;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVAddressingModel;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVBuiltIn;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVCapability;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVContextDependentInt;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVDecoration;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVExecutionModel;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVFunctionControl;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVLinkageType;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVLiteralInteger;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVLiteralString;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVMemoryAccess;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVMemoryModel;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVMultipleOperands;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVOptionalOperand;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVSourceLanguage;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVStorageClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class TestVector {

    private static void writeBufferToFile(ByteBuffer buffer, String filepath) {
        buffer.flip();
        File out = new File(filepath);
        try {
            FileChannel channel = new FileOutputStream(out, false).getChannel();
            channel.write(buffer);
            channel.close();
        } catch (IOException e) {
            System.err.println("IO exception: " + e.getMessage());
        }

    }

    public static void writeModuleToFile(SPIRVModule module, String filepath) {
        ByteBuffer out = ByteBuffer.allocate(module.getByteCount());
        out.order(ByteOrder.LITTLE_ENDIAN);
        module.close().write(out);
        writeBufferToFile(out, filepath);
    }

    /**
     * SPIR-V CODE for the following OpenCL kernel:
     *
     * <code>
     *     __kernel void testVectorInit(__global int* a) {
     *          int idx = get_global_id(0);
     * 	        a[idx] = 50;
     *     }
     * </code>
     *
     */
    public static void testVectorInit()  {

        // SPIRV Header
        SPIRVModule module = new SPIRVModule(
                new SPIRVHeader(
                    1,
                    2,
                    29,
                    0,
                    0));
        SPIRVInstScope functionScope;
        SPIRVInstScope blockScope;

        module.add(new SPIRVOpCapability(SPIRVCapability.Addresses()));     // Uses physical addressing, non-logical addressing modes.
        module.add(new SPIRVOpCapability(SPIRVCapability.Linkage()));       // Uses partially linked modules and libraries. (e.g., OpenCL)
        module.add(new SPIRVOpCapability(SPIRVCapability.Kernel()));        // Uses the Kernel Execution Model.
        module.add(new SPIRVOpCapability(SPIRVCapability.Int64()));         // Uses OpTypeInt to declare 64-bit integer types

        // For Double support: Float64 capability
        // module.add(new SPIRVOpCapability(SPIRVCapability.Float64()));

        // Extension for Import "OpenCL.std"
        SPIRVId idExtension = module.getNextId();
        module.add(new SPIRVOpExtInstImport(idExtension, new SPIRVLiteralString("OpenCL.std")));

        // OpenCL Version Set
        module.add(new SPIRVOpSource(SPIRVSourceLanguage.OpenCL_C(), new SPIRVLiteralInteger(100000), new SPIRVOptionalOperand<>(), new SPIRVOptionalOperand<>()));

        // Indicates a 64-bit module, where the address width is equal to 64 bits.
        module.add(new SPIRVOpMemoryModel(SPIRVAddressingModel.Physical64(), SPIRVMemoryModel.OpenCL()));

        // OpName
        SPIRVId idName = module.getNextId();
        module.add(new SPIRVOpName(idName, new SPIRVLiteralString("__spirv_BuiltInGlobalInvocationId")));

        SPIRVId aName = module.getNextId();
        module.add(new SPIRVOpName(aName, new SPIRVLiteralString("a")));

        SPIRVId aAddrName = module.getNextId();
        module.add(new SPIRVOpName(aAddrName, new SPIRVLiteralString("a.addr")));


        // Decorates
        // A) Global ID invocation
        module.add(new SPIRVOpDecorate(idName, SPIRVDecoration.BuiltIn(SPIRVBuiltIn.GlobalInvocationId())));

        // B) Constant
        module.add(new SPIRVOpDecorate(idName, SPIRVDecoration.Constant()));

        // C) LinkageAttributes
        SPIRVLiteralString literalGlobalID = new SPIRVLiteralString("__spirv_BuiltInGlobalInvocationId");
        module.add(new SPIRVOpDecorate(idName, SPIRVDecoration.LinkageAttributes(literalGlobalID, SPIRVLinkageType.Import())));

        // Int 32
        SPIRVId uint = module.getNextId();
        module.add(new SPIRVOpTypeInt(uint, new SPIRVLiteralInteger(32), new SPIRVLiteralInteger(0)));

        // Int 64
        SPIRVId ulong = module.getNextId();
        module.add(new SPIRVOpTypeInt(ulong, new SPIRVLiteralInteger(64), new SPIRVLiteralInteger(0)));

        // Type Vector: 3 elements of type long
        SPIRVId v3ulong = module.getNextId();
        module.add(new SPIRVOpTypeVector(v3ulong, ulong, new SPIRVLiteralInteger(3)));

        // OpConstants
        // Type, result, value
        SPIRVId constant50 = module.getNextId();
        module.add(new SPIRVOpConstant(uint, constant50, new SPIRVContextDependentInt(BigInteger.valueOf(50))));

        // Type pointer
        SPIRVId pointerResult = module.getNextId();
        // STORAGE CLASS <Input>: from pipeline. Visible across all functions in the current invocation. Variables declared with this
        // storage class are read-only, and must not have initializers.
        module.add(new SPIRVOpTypePointer(pointerResult, SPIRVStorageClass.Input(), v3ulong));

        // Type Void
        SPIRVId voidType = module.getNextId();
        module.add(new SPIRVOpTypeVoid(voidType));

        SPIRVId ptrCrossWorkGroupUInt = module.getNextId();
        // STORAGE CLASS <CrossWorkGroup> Visible across all functions of all invocations of all work groups. OpenCL global memory.
        module.add(new SPIRVOpTypePointer(ptrCrossWorkGroupUInt, SPIRVStorageClass.CrossWorkgroup(), uint));

        // Function declaration
        SPIRVId mainFunction = module.getNextId();
        module.add(new SPIRVOpTypeFunction(mainFunction, voidType, new SPIRVMultipleOperands<>(ptrCrossWorkGroupUInt)));

        SPIRVId ptrFunctionPtrCrossWorkGroup = module.getNextId();
        module.add(new SPIRVOpTypePointer(ptrFunctionPtrCrossWorkGroup, SPIRVStorageClass.Function(), uint));

        SPIRVId functionTypePtr = module.getNextId();
        module.add(new SPIRVOpTypePointer(functionTypePtr, SPIRVStorageClass.Function(), ptrCrossWorkGroupUInt));

        SPIRVId pointer = module.getNextId();
        module.add(new SPIRVOpTypePointer(pointer, SPIRVStorageClass.Input(), v3ulong));
        module.add(new SPIRVOpVariable(pointer, idName, SPIRVStorageClass.Input(), new SPIRVOptionalOperand<>()));

        SPIRVId functionDef = module.getNextId();
        functionScope = module.add(new SPIRVOpFunction(voidType, functionDef, SPIRVFunctionControl.DontInline(), mainFunction));
        functionScope.add(new SPIRVOpFunctionParameter(ptrCrossWorkGroupUInt, aName));

        module.add(new SPIRVOpEntryPoint(
                SPIRVExecutionModel.Kernel(),
                functionDef,
                new SPIRVLiteralString("testVectorInit"),
                new SPIRVMultipleOperands<>(idName)
        ));

        SPIRVOpLabel entryLabel = new SPIRVOpLabel(module.getNextId());
        blockScope = functionScope.add(entryLabel);

        blockScope.add(new SPIRVOpVariable(
                functionTypePtr,
                aAddrName,
                SPIRVStorageClass.Function(),
                new SPIRVOptionalOperand<>()
        ));
        SPIRVId idx = module.getNextId();
        blockScope.add(new SPIRVOpVariable(
                ptrFunctionPtrCrossWorkGroup,
                idx,
                SPIRVStorageClass.Function(),
                new SPIRVOptionalOperand<>()
        ));

        blockScope.add(new SPIRVOpStore(
                aAddrName,
                aName,
                new SPIRVOptionalOperand<>(SPIRVMemoryAccess.Aligned(new SPIRVLiteralInteger(8)))
        ));

        SPIRVId load17 = module.getNextId();
        blockScope.add(new SPIRVOpLoad(
                v3ulong,
                load17,
                idName,
                new SPIRVOptionalOperand<>(SPIRVMemoryAccess.Aligned(new SPIRVLiteralInteger(32)))
        ));

        SPIRVId call = module.getNextId();
        blockScope.add(new SPIRVOpCompositeExtract(
                ulong,
                call,
                load17,
                new SPIRVMultipleOperands<>(new SPIRVLiteralInteger(0))
        ));

        SPIRVId conv = module.getNextId();
        blockScope.add(new SPIRVOpUConvert(uint, conv, call));

        blockScope.add(new SPIRVOpStore(
                idx,
                conv,
                new SPIRVOptionalOperand<>(SPIRVMemoryAccess.Aligned(new SPIRVLiteralInteger(4)))
        ));

        SPIRVId load20 = module.getNextId();
        blockScope.add(new SPIRVOpLoad(
                ptrCrossWorkGroupUInt,
                load20,
                aAddrName,
                new SPIRVOptionalOperand<>(SPIRVMemoryAccess.Aligned(new SPIRVLiteralInteger(8)))
        ));

        SPIRVId load21 = module.getNextId();
        blockScope.add(new SPIRVOpLoad(
                uint,
                load21,
                idx,
                new SPIRVOptionalOperand<>(SPIRVMemoryAccess.Aligned(new SPIRVLiteralInteger(4)))
        ));

        SPIRVId idxprom = module.getNextId();
        blockScope.add(new SPIRVOpUConvert(ulong, idxprom, load21));

        SPIRVId ptridx = module.getNextId();
        blockScope.add(new SPIRVOpInBoundsPtrAccessChain(
                ptrCrossWorkGroupUInt,
                ptridx,
                load20,
                idxprom,
                new SPIRVMultipleOperands()
        ));

        blockScope.add(new SPIRVOpStore(
                ptridx,
                constant50,
                new SPIRVOptionalOperand<>(SPIRVMemoryAccess.Aligned(new SPIRVLiteralInteger(4)))
        ));

        blockScope.add(new SPIRVOpReturn());
        functionScope.add(new SPIRVOpFunctionEnd());

        writeModuleToFile(module,"/tmp/testSPIRV.spv");
    }

    public static void main(String[] args) throws InvalidSPIRVModuleException {
        testVectorInit();
    }

}
