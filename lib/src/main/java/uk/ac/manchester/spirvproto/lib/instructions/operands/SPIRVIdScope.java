package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVIdScope extends SPIRVID {
    public static EmptySPIRVIdScope Empty = new EmptySPIRVIdScope();

    public SPIRVIdScope(int id) {
        super(id);
    }

    public static class EmptySPIRVIdScope extends SPIRVIdScope {
        protected EmptySPIRVIdScope() {
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