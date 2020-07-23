package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVAddressingModel extends SPIRVEnum {
    public static SPIRVAddressingModel Logical = new SPIRVAddressingModel(0);
    public static SPIRVAddressingModel Physical32 = new SPIRVAddressingModel(1);
    public static SPIRVAddressingModel Physical64 = new SPIRVAddressingModel(2);
    public static EmptySPIRVAddressingModel Empty = new EmptySPIRVAddressingModel();

    protected SPIRVAddressingModel(int value) {
        super(value);
    }

    public static class EmptySPIRVAddressingModel extends SPIRVAddressingModel {
        protected EmptySPIRVAddressingModel() {
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
