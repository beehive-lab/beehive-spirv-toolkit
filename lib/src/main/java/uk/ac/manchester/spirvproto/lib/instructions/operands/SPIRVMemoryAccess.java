package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVMemoryAccess extends SPIRVEnum {
    public static SPIRVMemoryAccess None = new SPIRVMemoryAccess(0x0000);
    public static SPIRVMemoryAccess Volatile = new SPIRVMemoryAccess(0x0001);
    public static SPIRVMemoryAccess Aligned = new SPIRVMemoryAccess(0x0002);
    public static SPIRVMemoryAccess Nontemporal = new SPIRVMemoryAccess(0x0004);
    public static EmptySPIRVMemoryAccess Empty = new EmptySPIRVMemoryAccess();

    protected SPIRVMemoryAccess(int value) {
        super(value);
    }

    public static class EmptySPIRVMemoryAccess extends SPIRVMemoryAccess {
        protected EmptySPIRVMemoryAccess() {
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
