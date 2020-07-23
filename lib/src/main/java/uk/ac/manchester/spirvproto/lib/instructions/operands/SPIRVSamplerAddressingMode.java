package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVSamplerAddressingMode extends SPIRVEnum {
    public static SPIRVSamplerAddressingMode None = new SPIRVSamplerAddressingMode(0);
    public static SPIRVSamplerAddressingMode ClampToEdge = new SPIRVSamplerAddressingMode(1);
    public static SPIRVSamplerAddressingMode Clamp = new SPIRVSamplerAddressingMode(2);
    public static SPIRVSamplerAddressingMode Repeat = new SPIRVSamplerAddressingMode(3);
    public static SPIRVSamplerAddressingMode RepeatMirrored = new SPIRVSamplerAddressingMode(4);
    public static EmptySPIRVSamplerAddressingMode Empty = new EmptySPIRVSamplerAddressingMode();

    protected SPIRVSamplerAddressingMode(int value) {
        super(value);
    }

    public static class EmptySPIRVSamplerAddressingMode extends SPIRVSamplerAddressingMode {
        protected EmptySPIRVSamplerAddressingMode() {
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
