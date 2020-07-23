package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVCapability extends SPIRVEnum {
    public static SPIRVCapability Matrix = new SPIRVCapability(0);
    public static SPIRVCapability Shader = new SPIRVCapability(1);
    public static SPIRVCapability Geometry = new SPIRVCapability(2);
    public static SPIRVCapability Tessellation = new SPIRVCapability(3);
    public static SPIRVCapability Addresses = new SPIRVCapability(4);
    public static SPIRVCapability Linkage = new SPIRVCapability(5);
    public static SPIRVCapability Kernel = new SPIRVCapability(6);
    public static SPIRVCapability Vector16 = new SPIRVCapability(7);
    public static SPIRVCapability Float16Buffer = new SPIRVCapability(8);
    public static SPIRVCapability Float16 = new SPIRVCapability(9);
    public static SPIRVCapability Float64 = new SPIRVCapability(10);
    public static SPIRVCapability Int64 = new SPIRVCapability(11);
    public static SPIRVCapability Int64Atomics = new SPIRVCapability(12);
    public static SPIRVCapability ImageBasic = new SPIRVCapability(13);
    public static SPIRVCapability ImageReadWrite = new SPIRVCapability(14);
    public static SPIRVCapability ImageMipmap = new SPIRVCapability(15);
    public static SPIRVCapability Pipes = new SPIRVCapability(17);
    public static SPIRVCapability Groups = new SPIRVCapability(18);
    public static SPIRVCapability DeviceEnqueue = new SPIRVCapability(19);
    public static SPIRVCapability LiteralSampler = new SPIRVCapability(20);
    public static SPIRVCapability AtomicStorage = new SPIRVCapability(21);
    public static SPIRVCapability Int16 = new SPIRVCapability(22);
    public static SPIRVCapability TessellationPointSize = new SPIRVCapability(23);
    public static SPIRVCapability GeometryPointSize = new SPIRVCapability(24);
    public static SPIRVCapability ImageGatherExtended = new SPIRVCapability(25);
    public static SPIRVCapability StorageImageMultisample = new SPIRVCapability(27);
    public static SPIRVCapability UniformBufferArrayDynamicIndexing = new SPIRVCapability(28);
    public static SPIRVCapability SampledImageArrayDynamicIndexing = new SPIRVCapability(29);
    public static SPIRVCapability StorageBufferArrayDynamicIndexing = new SPIRVCapability(30);
    public static SPIRVCapability StorageImageArrayDynamicIndexing = new SPIRVCapability(31);
    public static SPIRVCapability ClipDistance = new SPIRVCapability(32);
    public static SPIRVCapability CullDistance = new SPIRVCapability(33);
    public static SPIRVCapability ImageCubeArray = new SPIRVCapability(34);
    public static SPIRVCapability SampleRateShading = new SPIRVCapability(35);
    public static SPIRVCapability ImageRect = new SPIRVCapability(36);
    public static SPIRVCapability SampledRect = new SPIRVCapability(37);
    public static SPIRVCapability GenericPointer = new SPIRVCapability(38);
    public static SPIRVCapability Int8 = new SPIRVCapability(39);
    public static SPIRVCapability InputAttachment = new SPIRVCapability(40);
    public static SPIRVCapability SparseResidency = new SPIRVCapability(41);
    public static SPIRVCapability MinLod = new SPIRVCapability(42);
    public static SPIRVCapability Sampled1D = new SPIRVCapability(43);
    public static SPIRVCapability Image1D = new SPIRVCapability(44);
    public static SPIRVCapability SampledCubeArray = new SPIRVCapability(45);
    public static SPIRVCapability SampledBuffer = new SPIRVCapability(46);
    public static SPIRVCapability ImageBuffer = new SPIRVCapability(47);
    public static SPIRVCapability ImageMSArray = new SPIRVCapability(48);
    public static SPIRVCapability StorageImageExtendedFormats = new SPIRVCapability(49);
    public static SPIRVCapability ImageQuery = new SPIRVCapability(50);
    public static SPIRVCapability DerivativeControl = new SPIRVCapability(51);
    public static SPIRVCapability InterpolationFunction = new SPIRVCapability(52);
    public static SPIRVCapability TransformFeedback = new SPIRVCapability(53);
    public static SPIRVCapability GeometryStreams = new SPIRVCapability(54);
    public static SPIRVCapability StorageImageReadWithoutFormat = new SPIRVCapability(55);
    public static SPIRVCapability StorageImageWriteWithoutFormat = new SPIRVCapability(56);
    public static SPIRVCapability MultiViewport = new SPIRVCapability(57);
    public static SPIRVCapability SubgroupBallotKHR = new SPIRVCapability(4423);
    public static SPIRVCapability DrawParameters = new SPIRVCapability(4427);
    public static SPIRVCapability SubgroupVoteKHR = new SPIRVCapability(4431);
    public static SPIRVCapability StorageBuffer16BitAccess = new SPIRVCapability(4433);
    public static SPIRVCapability StorageUniformBufferBlock16 = new SPIRVCapability(4433);
    public static SPIRVCapability UniformAndStorageBuffer16BitAccess = new SPIRVCapability(4434);
    public static SPIRVCapability StorageUniform16 = new SPIRVCapability(4434);
    public static SPIRVCapability StoragePushConstant16 = new SPIRVCapability(4435);
    public static SPIRVCapability StorageInputOutput16 = new SPIRVCapability(4436);
    public static SPIRVCapability DeviceGroup = new SPIRVCapability(4437);
    public static SPIRVCapability MultiView = new SPIRVCapability(4439);
    public static SPIRVCapability VariablePointersStorageBuffer = new SPIRVCapability(4441);
    public static SPIRVCapability VariablePointers = new SPIRVCapability(4442);
    public static SPIRVCapability AtomicStorageOps = new SPIRVCapability(4445);
    public static SPIRVCapability SampleMaskPostDepthCoverage = new SPIRVCapability(4447);
    public static SPIRVCapability ImageGatherBiasLodAMD = new SPIRVCapability(5009);
    public static SPIRVCapability FragmentMaskAMD = new SPIRVCapability(5010);
    public static SPIRVCapability StencilExportEXT = new SPIRVCapability(5013);
    public static SPIRVCapability ImageReadWriteLodAMD = new SPIRVCapability(5015);
    public static SPIRVCapability SampleMaskOverrideCoverageNV = new SPIRVCapability(5249);
    public static SPIRVCapability GeometryShaderPassthroughNV = new SPIRVCapability(5251);
    public static SPIRVCapability ShaderViewportIndexLayerEXT = new SPIRVCapability(5254);
    public static SPIRVCapability ShaderViewportIndexLayerNV = new SPIRVCapability(5254);
    public static SPIRVCapability ShaderViewportMaskNV = new SPIRVCapability(5255);
    public static SPIRVCapability ShaderStereoViewNV = new SPIRVCapability(5259);
    public static SPIRVCapability PerViewAttributesNV = new SPIRVCapability(5260);
    public static SPIRVCapability SubgroupShuffleINTEL = new SPIRVCapability(5568);
    public static SPIRVCapability SubgroupBufferBlockIOINTEL = new SPIRVCapability(5569);
    public static SPIRVCapability SubgroupImageBlockIOINTEL = new SPIRVCapability(5570);
    public static EmptySPIRVCapability Empty = new EmptySPIRVCapability();

    protected SPIRVCapability(int value) {
        super(value);
    }

    public static class EmptySPIRVCapability extends SPIRVCapability {
        protected EmptySPIRVCapability() {
            super(-1);
        }

        @Override
        public void write(ByteBuffer output) {
            // Do nothing as this is empty
        }

        @Override
        public int getWordCount() {
            return 0;
        }
    }
}
