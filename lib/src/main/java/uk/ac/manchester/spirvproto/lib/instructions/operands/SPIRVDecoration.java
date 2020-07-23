package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVDecoration extends SPIRVEnum {
    public static SPIRVDecoration RelaxedPrecision = new SPIRVDecoration(0);
    public static SPIRVDecoration SpecId = new SPIRVDecoration(1);
    public static SPIRVDecoration Block = new SPIRVDecoration(2);
    public static SPIRVDecoration BufferBlock = new SPIRVDecoration(3);
    public static SPIRVDecoration RowMajor = new SPIRVDecoration(4);
    public static SPIRVDecoration ColMajor = new SPIRVDecoration(5);
    public static SPIRVDecoration ArrayStride = new SPIRVDecoration(6);
    public static SPIRVDecoration MatrixStride = new SPIRVDecoration(7);
    public static SPIRVDecoration GLSLShared = new SPIRVDecoration(8);
    public static SPIRVDecoration GLSLPacked = new SPIRVDecoration(9);
    public static SPIRVDecoration CPacked = new SPIRVDecoration(10);
    public static SPIRVDecoration BuiltIn = new SPIRVDecoration(11);
    public static SPIRVDecoration NoPerspective = new SPIRVDecoration(13);
    public static SPIRVDecoration Flat = new SPIRVDecoration(14);
    public static SPIRVDecoration Patch = new SPIRVDecoration(15);
    public static SPIRVDecoration Centroid = new SPIRVDecoration(16);
    public static SPIRVDecoration Sample = new SPIRVDecoration(17);
    public static SPIRVDecoration Invariant = new SPIRVDecoration(18);
    public static SPIRVDecoration Restrict = new SPIRVDecoration(19);
    public static SPIRVDecoration Aliased = new SPIRVDecoration(20);
    public static SPIRVDecoration Volatile = new SPIRVDecoration(21);
    public static SPIRVDecoration Constant = new SPIRVDecoration(22);
    public static SPIRVDecoration Coherent = new SPIRVDecoration(23);
    public static SPIRVDecoration NonWritable = new SPIRVDecoration(24);
    public static SPIRVDecoration NonReadable = new SPIRVDecoration(25);
    public static SPIRVDecoration Uniform = new SPIRVDecoration(26);
    public static SPIRVDecoration SaturatedConversion = new SPIRVDecoration(28);
    public static SPIRVDecoration Stream = new SPIRVDecoration(29);
    public static SPIRVDecoration Location = new SPIRVDecoration(30);
    public static SPIRVDecoration Component = new SPIRVDecoration(31);
    public static SPIRVDecoration Index = new SPIRVDecoration(32);
    public static SPIRVDecoration Binding = new SPIRVDecoration(33);
    public static SPIRVDecoration DescriptorSet = new SPIRVDecoration(34);
    public static SPIRVDecoration Offset = new SPIRVDecoration(35);
    public static SPIRVDecoration XfbBuffer = new SPIRVDecoration(36);
    public static SPIRVDecoration XfbStride = new SPIRVDecoration(37);
    public static SPIRVDecoration FuncParamAttr = new SPIRVDecoration(38);
    public static SPIRVDecoration FPRoundingMode = new SPIRVDecoration(39);
    public static SPIRVDecoration FPFastMathMode = new SPIRVDecoration(40);
    public static SPIRVDecoration LinkageAttributes = new SPIRVDecoration(41);
    public static SPIRVDecoration NoContraction = new SPIRVDecoration(42);
    public static SPIRVDecoration InputAttachmentIndex = new SPIRVDecoration(43);
    public static SPIRVDecoration Alignment = new SPIRVDecoration(44);
    public static SPIRVDecoration ExplicitInterpAMD = new SPIRVDecoration(4999);
    public static SPIRVDecoration OverrideCoverageNV = new SPIRVDecoration(5248);
    public static SPIRVDecoration PassthroughNV = new SPIRVDecoration(5250);
    public static SPIRVDecoration ViewportRelativeNV = new SPIRVDecoration(5252);
    public static SPIRVDecoration SecondaryViewportRelativeNV = new SPIRVDecoration(5256);
    public static SPIRVDecoration HlslCounterBufferGOOGLE = new SPIRVDecoration(5634);
    public static SPIRVDecoration HlslSemanticGOOGLE = new SPIRVDecoration(5635);
    public static EmptySPIRVDecoration Empty = new EmptySPIRVDecoration();

    protected SPIRVDecoration(int value) {
        super(value);
    }

    public static class EmptySPIRVDecoration extends SPIRVDecoration {
        protected EmptySPIRVDecoration() {
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
