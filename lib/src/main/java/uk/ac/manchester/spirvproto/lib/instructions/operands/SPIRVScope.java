package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVScope extends SPIRVEnum {
    public static SPIRVScope CrossDevice = new SPIRVScope(0);
    public static SPIRVScope Device = new SPIRVScope(1);
    public static SPIRVScope Workgroup = new SPIRVScope(2);
    public static SPIRVScope Subgroup = new SPIRVScope(3);
    public static SPIRVScope Invocation = new SPIRVScope(4);
    public static EmptySPIRVScope Empty = new EmptySPIRVScope();

    protected SPIRVScope(int value) {
        super(value);
    }

    public static class EmptySPIRVScope extends SPIRVScope {
        protected EmptySPIRVScope() {
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
