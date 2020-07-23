package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVImageOperands extends SPIRVEnum {
    public static SPIRVImageOperands None = new SPIRVImageOperands(0x0000);
    public static SPIRVImageOperands Bias = new SPIRVImageOperands(0x0001);
    public static SPIRVImageOperands Lod = new SPIRVImageOperands(0x0002);
    public static SPIRVImageOperands Grad = new SPIRVImageOperands(0x0004);
    public static SPIRVImageOperands ConstOffset = new SPIRVImageOperands(0x0008);
    public static SPIRVImageOperands Offset = new SPIRVImageOperands(0x0010);
    public static SPIRVImageOperands ConstOffsets = new SPIRVImageOperands(0x0020);
    public static SPIRVImageOperands Sample = new SPIRVImageOperands(0x0040);
    public static SPIRVImageOperands MinLod = new SPIRVImageOperands(0x0080);
    public static EmptySPIRVImageOperands Empty = new EmptySPIRVImageOperands();

    protected SPIRVImageOperands(int value) {
        super(value);
    }

    public static class EmptySPIRVImageOperands extends SPIRVImageOperands {
        protected EmptySPIRVImageOperands() {
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
