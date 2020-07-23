package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVImageFormat extends SPIRVEnum {
    public static SPIRVImageFormat Unknown = new SPIRVImageFormat(0);
    public static SPIRVImageFormat Rgba32f = new SPIRVImageFormat(1);
    public static SPIRVImageFormat Rgba16f = new SPIRVImageFormat(2);
    public static SPIRVImageFormat R32f = new SPIRVImageFormat(3);
    public static SPIRVImageFormat Rgba8 = new SPIRVImageFormat(4);
    public static SPIRVImageFormat Rgba8Snorm = new SPIRVImageFormat(5);
    public static SPIRVImageFormat Rg32f = new SPIRVImageFormat(6);
    public static SPIRVImageFormat Rg16f = new SPIRVImageFormat(7);
    public static SPIRVImageFormat R11fG11fB10f = new SPIRVImageFormat(8);
    public static SPIRVImageFormat R16f = new SPIRVImageFormat(9);
    public static SPIRVImageFormat Rgba16 = new SPIRVImageFormat(10);
    public static SPIRVImageFormat Rgb10A2 = new SPIRVImageFormat(11);
    public static SPIRVImageFormat Rg16 = new SPIRVImageFormat(12);
    public static SPIRVImageFormat Rg8 = new SPIRVImageFormat(13);
    public static SPIRVImageFormat R16 = new SPIRVImageFormat(14);
    public static SPIRVImageFormat R8 = new SPIRVImageFormat(15);
    public static SPIRVImageFormat Rgba16Snorm = new SPIRVImageFormat(16);
    public static SPIRVImageFormat Rg16Snorm = new SPIRVImageFormat(17);
    public static SPIRVImageFormat Rg8Snorm = new SPIRVImageFormat(18);
    public static SPIRVImageFormat R16Snorm = new SPIRVImageFormat(19);
    public static SPIRVImageFormat R8Snorm = new SPIRVImageFormat(20);
    public static SPIRVImageFormat Rgba32i = new SPIRVImageFormat(21);
    public static SPIRVImageFormat Rgba16i = new SPIRVImageFormat(22);
    public static SPIRVImageFormat Rgba8i = new SPIRVImageFormat(23);
    public static SPIRVImageFormat R32i = new SPIRVImageFormat(24);
    public static SPIRVImageFormat Rg32i = new SPIRVImageFormat(25);
    public static SPIRVImageFormat Rg16i = new SPIRVImageFormat(26);
    public static SPIRVImageFormat Rg8i = new SPIRVImageFormat(27);
    public static SPIRVImageFormat R16i = new SPIRVImageFormat(28);
    public static SPIRVImageFormat R8i = new SPIRVImageFormat(29);
    public static SPIRVImageFormat Rgba32ui = new SPIRVImageFormat(30);
    public static SPIRVImageFormat Rgba16ui = new SPIRVImageFormat(31);
    public static SPIRVImageFormat Rgba8ui = new SPIRVImageFormat(32);
    public static SPIRVImageFormat R32ui = new SPIRVImageFormat(33);
    public static SPIRVImageFormat Rgb10a2ui = new SPIRVImageFormat(34);
    public static SPIRVImageFormat Rg32ui = new SPIRVImageFormat(35);
    public static SPIRVImageFormat Rg16ui = new SPIRVImageFormat(36);
    public static SPIRVImageFormat Rg8ui = new SPIRVImageFormat(37);
    public static SPIRVImageFormat R16ui = new SPIRVImageFormat(38);
    public static SPIRVImageFormat R8ui = new SPIRVImageFormat(39);
    public static EmptySPIRVImageFormat Empty = new EmptySPIRVImageFormat();

    protected SPIRVImageFormat(int value) {
        super(value);
    }

    public static class EmptySPIRVImageFormat extends SPIRVImageFormat {
        protected EmptySPIRVImageFormat() {
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
