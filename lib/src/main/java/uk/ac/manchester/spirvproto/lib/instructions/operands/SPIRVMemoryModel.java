package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVMemoryModel extends SPIRVEnum {
    public static SPIRVMemoryModel Simple = new SPIRVMemoryModel(0);
    public static SPIRVMemoryModel GLSL450 = new SPIRVMemoryModel(1);
    public static SPIRVMemoryModel OpenCL = new SPIRVMemoryModel(2);
    public static EmptySPIRVMemoryModel Empty = new EmptySPIRVMemoryModel();

    protected SPIRVMemoryModel(int value) {
        super(value);
    }

    public static class EmptySPIRVMemoryModel extends SPIRVMemoryModel {
        protected EmptySPIRVMemoryModel() {
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
