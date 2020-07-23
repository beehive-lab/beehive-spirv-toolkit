package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVFPFastMathMode extends SPIRVEnum {
    public static SPIRVFPFastMathMode None = new SPIRVFPFastMathMode(0x0000);
    public static SPIRVFPFastMathMode NotNaN = new SPIRVFPFastMathMode(0x0001);
    public static SPIRVFPFastMathMode NotInf = new SPIRVFPFastMathMode(0x0002);
    public static SPIRVFPFastMathMode NSZ = new SPIRVFPFastMathMode(0x0004);
    public static SPIRVFPFastMathMode AllowRecip = new SPIRVFPFastMathMode(0x0008);
    public static SPIRVFPFastMathMode Fast = new SPIRVFPFastMathMode(0x0010);
    public static EmptySPIRVFPFastMathMode Empty = new EmptySPIRVFPFastMathMode();

    protected SPIRVFPFastMathMode(int value) {
        super(value);
    }

    public static class EmptySPIRVFPFastMathMode extends SPIRVFPFastMathMode {
        protected EmptySPIRVFPFastMathMode() {
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
