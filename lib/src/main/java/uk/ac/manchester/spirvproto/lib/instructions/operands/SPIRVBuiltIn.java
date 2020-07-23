package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVBuiltIn extends SPIRVEnum {
    public static SPIRVBuiltIn Position = new SPIRVBuiltIn(0);
    public static SPIRVBuiltIn PointSize = new SPIRVBuiltIn(1);
    public static SPIRVBuiltIn ClipDistance = new SPIRVBuiltIn(3);
    public static SPIRVBuiltIn CullDistance = new SPIRVBuiltIn(4);
    public static SPIRVBuiltIn VertexId = new SPIRVBuiltIn(5);
    public static SPIRVBuiltIn InstanceId = new SPIRVBuiltIn(6);
    public static SPIRVBuiltIn PrimitiveId = new SPIRVBuiltIn(7);
    public static SPIRVBuiltIn InvocationId = new SPIRVBuiltIn(8);
    public static SPIRVBuiltIn Layer = new SPIRVBuiltIn(9);
    public static SPIRVBuiltIn ViewportIndex = new SPIRVBuiltIn(10);
    public static SPIRVBuiltIn TessLevelOuter = new SPIRVBuiltIn(11);
    public static SPIRVBuiltIn TessLevelInner = new SPIRVBuiltIn(12);
    public static SPIRVBuiltIn TessCoord = new SPIRVBuiltIn(13);
    public static SPIRVBuiltIn PatchVertices = new SPIRVBuiltIn(14);
    public static SPIRVBuiltIn FragCoord = new SPIRVBuiltIn(15);
    public static SPIRVBuiltIn PointCoord = new SPIRVBuiltIn(16);
    public static SPIRVBuiltIn FrontFacing = new SPIRVBuiltIn(17);
    public static SPIRVBuiltIn SampleId = new SPIRVBuiltIn(18);
    public static SPIRVBuiltIn SamplePosition = new SPIRVBuiltIn(19);
    public static SPIRVBuiltIn SampleMask = new SPIRVBuiltIn(20);
    public static SPIRVBuiltIn FragDepth = new SPIRVBuiltIn(22);
    public static SPIRVBuiltIn HelperInvocation = new SPIRVBuiltIn(23);
    public static SPIRVBuiltIn NumWorkgroups = new SPIRVBuiltIn(24);
    public static SPIRVBuiltIn WorkgroupSize = new SPIRVBuiltIn(25);
    public static SPIRVBuiltIn WorkgroupId = new SPIRVBuiltIn(26);
    public static SPIRVBuiltIn LocalInvocationId = new SPIRVBuiltIn(27);
    public static SPIRVBuiltIn GlobalInvocationId = new SPIRVBuiltIn(28);
    public static SPIRVBuiltIn LocalInvocationIndex = new SPIRVBuiltIn(29);
    public static SPIRVBuiltIn WorkDim = new SPIRVBuiltIn(30);
    public static SPIRVBuiltIn GlobalSize = new SPIRVBuiltIn(31);
    public static SPIRVBuiltIn EnqueuedWorkgroupSize = new SPIRVBuiltIn(32);
    public static SPIRVBuiltIn GlobalOffset = new SPIRVBuiltIn(33);
    public static SPIRVBuiltIn GlobalLinearId = new SPIRVBuiltIn(34);
    public static SPIRVBuiltIn SubgroupSize = new SPIRVBuiltIn(36);
    public static SPIRVBuiltIn SubgroupMaxSize = new SPIRVBuiltIn(37);
    public static SPIRVBuiltIn NumSubgroups = new SPIRVBuiltIn(38);
    public static SPIRVBuiltIn NumEnqueuedSubgroups = new SPIRVBuiltIn(39);
    public static SPIRVBuiltIn SubgroupId = new SPIRVBuiltIn(40);
    public static SPIRVBuiltIn SubgroupLocalInvocationId = new SPIRVBuiltIn(41);
    public static SPIRVBuiltIn VertexIndex = new SPIRVBuiltIn(42);
    public static SPIRVBuiltIn InstanceIndex = new SPIRVBuiltIn(43);
    public static SPIRVBuiltIn SubgroupEqMaskKHR = new SPIRVBuiltIn(4416);
    public static SPIRVBuiltIn SubgroupGeMaskKHR = new SPIRVBuiltIn(4417);
    public static SPIRVBuiltIn SubgroupGtMaskKHR = new SPIRVBuiltIn(4418);
    public static SPIRVBuiltIn SubgroupLeMaskKHR = new SPIRVBuiltIn(4419);
    public static SPIRVBuiltIn SubgroupLtMaskKHR = new SPIRVBuiltIn(4420);
    public static SPIRVBuiltIn BaseVertex = new SPIRVBuiltIn(4424);
    public static SPIRVBuiltIn BaseInstance = new SPIRVBuiltIn(4425);
    public static SPIRVBuiltIn DrawIndex = new SPIRVBuiltIn(4426);
    public static SPIRVBuiltIn DeviceIndex = new SPIRVBuiltIn(4438);
    public static SPIRVBuiltIn ViewIndex = new SPIRVBuiltIn(4440);
    public static SPIRVBuiltIn BaryCoordNoPerspAMD = new SPIRVBuiltIn(4992);
    public static SPIRVBuiltIn BaryCoordNoPerspCentroidAMD = new SPIRVBuiltIn(4993);
    public static SPIRVBuiltIn BaryCoordNoPerspSampleAMD = new SPIRVBuiltIn(4994);
    public static SPIRVBuiltIn BaryCoordSmoothAMD = new SPIRVBuiltIn(4995);
    public static SPIRVBuiltIn BaryCoordSmoothCentroidAMD = new SPIRVBuiltIn(4996);
    public static SPIRVBuiltIn BaryCoordSmoothSampleAMD = new SPIRVBuiltIn(4997);
    public static SPIRVBuiltIn BaryCoordPullModelAMD = new SPIRVBuiltIn(4998);
    public static SPIRVBuiltIn FragStencilRefEXT = new SPIRVBuiltIn(5014);
    public static SPIRVBuiltIn ViewportMaskNV = new SPIRVBuiltIn(5253);
    public static SPIRVBuiltIn SecondaryPositionNV = new SPIRVBuiltIn(5257);
    public static SPIRVBuiltIn SecondaryViewportMaskNV = new SPIRVBuiltIn(5258);
    public static SPIRVBuiltIn PositionPerViewNV = new SPIRVBuiltIn(5261);
    public static SPIRVBuiltIn ViewportMaskPerViewNV = new SPIRVBuiltIn(5262);
    public static EmptySPIRVBuiltIn Empty = new EmptySPIRVBuiltIn();

    protected SPIRVBuiltIn(int value) {
        super(value);
    }

    public static class EmptySPIRVBuiltIn extends SPIRVBuiltIn {
        protected EmptySPIRVBuiltIn() {
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
