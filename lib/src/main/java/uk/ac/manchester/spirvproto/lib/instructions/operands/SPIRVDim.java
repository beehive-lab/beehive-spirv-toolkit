package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVDim extends SPIRVEnum {
    public static SPIRVDim oneD = new SPIRVDim(0);
    public static SPIRVDim twoD = new SPIRVDim(1);
    public static SPIRVDim threeD = new SPIRVDim(2);
    public static SPIRVDim Cube = new SPIRVDim(3);
    public static SPIRVDim Rect = new SPIRVDim(4);
    public static SPIRVDim Buffer = new SPIRVDim(5);
    public static SPIRVDim SubpassData = new SPIRVDim(6);
    public static EmptySPIRVDim Empty = new EmptySPIRVDim();

    protected SPIRVDim(int value) {
        super(value);
    }

    public static class EmptySPIRVDim extends SPIRVDim {
        protected EmptySPIRVDim() {
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
