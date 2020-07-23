package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVIdResultType extends SPIRVID {
    public static EmptySPIRVIdResultType Empty = new EmptySPIRVIdResultType();

    public SPIRVIdResultType(int id) {
        super(id);
    }

    public static class EmptySPIRVIdResultType extends SPIRVIdResultType {
        protected EmptySPIRVIdResultType() {
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