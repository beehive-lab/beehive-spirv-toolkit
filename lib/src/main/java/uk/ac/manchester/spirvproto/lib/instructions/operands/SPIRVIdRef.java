package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVIdRef extends SPIRVID {
    public static EmptySPIRVIdRef Empty = new EmptySPIRVIdRef();

    public SPIRVIdRef(int id) {
        super(id);
    }

    public static class EmptySPIRVIdRef extends SPIRVIdRef {
        protected EmptySPIRVIdRef() {
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