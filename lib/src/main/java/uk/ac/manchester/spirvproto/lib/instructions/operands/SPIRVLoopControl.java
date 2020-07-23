package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVLoopControl extends SPIRVEnum {
    public static SPIRVLoopControl None = new SPIRVLoopControl(0x0000);
    public static SPIRVLoopControl Unroll = new SPIRVLoopControl(0x0001);
    public static SPIRVLoopControl DontUnroll = new SPIRVLoopControl(0x0002);
    public static EmptySPIRVLoopControl Empty = new EmptySPIRVLoopControl();

    protected SPIRVLoopControl(int value) {
        super(value);
    }

    public static class EmptySPIRVLoopControl extends SPIRVLoopControl {
        protected EmptySPIRVLoopControl() {
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
