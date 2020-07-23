package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVLinkageType extends SPIRVEnum {
    public static SPIRVLinkageType Export = new SPIRVLinkageType(0);
    public static SPIRVLinkageType Import = new SPIRVLinkageType(1);
    public static EmptySPIRVLinkageType Empty = new EmptySPIRVLinkageType();

    protected SPIRVLinkageType(int value) {
        super(value);
    }

    public static class EmptySPIRVLinkageType extends SPIRVLinkageType {
        protected EmptySPIRVLinkageType() {
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
