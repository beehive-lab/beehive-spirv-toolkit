package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVIdMemorySemantics extends SPIRVID {
    public static EmptySPIRVIdMemorySemantics Empty = new EmptySPIRVIdMemorySemantics();

    public SPIRVIdMemorySemantics(int id) {
        super(id);
    }

    public static class EmptySPIRVIdMemorySemantics extends SPIRVIdMemorySemantics {
        protected EmptySPIRVIdMemorySemantics() {
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