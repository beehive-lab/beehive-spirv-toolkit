package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVSamplerFilterMode extends SPIRVEnum {
    public static SPIRVSamplerFilterMode Nearest = new SPIRVSamplerFilterMode(0);
    public static SPIRVSamplerFilterMode Linear = new SPIRVSamplerFilterMode(1);
    public static EmptySPIRVSamplerFilterMode Empty = new EmptySPIRVSamplerFilterMode();

    protected SPIRVSamplerFilterMode(int value) {
        super(value);
    }

    public static class EmptySPIRVSamplerFilterMode extends SPIRVSamplerFilterMode {
        protected EmptySPIRVSamplerFilterMode() {
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
