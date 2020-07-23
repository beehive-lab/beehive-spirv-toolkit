package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVIdResult extends SPIRVID {
    public static EmptySPIRVIdResult Empty = new EmptySPIRVIdResult();

    public SPIRVIdResult(int id) {
        super(id);
    }

    public static class EmptySPIRVIdResult extends SPIRVIdResult {
        protected EmptySPIRVIdResult() {
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