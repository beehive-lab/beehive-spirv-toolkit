package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVFPRoundingMode extends SPIRVEnum {
    public static SPIRVFPRoundingMode RTE = new SPIRVFPRoundingMode(0);
    public static SPIRVFPRoundingMode RTZ = new SPIRVFPRoundingMode(1);
    public static SPIRVFPRoundingMode RTP = new SPIRVFPRoundingMode(2);
    public static SPIRVFPRoundingMode RTN = new SPIRVFPRoundingMode(3);
    public static EmptySPIRVFPRoundingMode Empty = new EmptySPIRVFPRoundingMode();

    protected SPIRVFPRoundingMode(int value) {
        super(value);
    }

    public static class EmptySPIRVFPRoundingMode extends SPIRVFPRoundingMode {
        protected EmptySPIRVFPRoundingMode() {
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
