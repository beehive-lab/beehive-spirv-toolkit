package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVKernelProfilingInfo extends SPIRVEnum {
    public static SPIRVKernelProfilingInfo None = new SPIRVKernelProfilingInfo(0x0000);
    public static SPIRVKernelProfilingInfo CmdExecTime = new SPIRVKernelProfilingInfo(0x0001);
    public static EmptySPIRVKernelProfilingInfo Empty = new EmptySPIRVKernelProfilingInfo();

    protected SPIRVKernelProfilingInfo(int value) {
        super(value);
    }

    public static class EmptySPIRVKernelProfilingInfo extends SPIRVKernelProfilingInfo {
        protected EmptySPIRVKernelProfilingInfo() {
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
