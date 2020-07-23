package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVKernelEnqueueFlags extends SPIRVEnum {
    public static SPIRVKernelEnqueueFlags NoWait = new SPIRVKernelEnqueueFlags(0);
    public static SPIRVKernelEnqueueFlags WaitKernel = new SPIRVKernelEnqueueFlags(1);
    public static SPIRVKernelEnqueueFlags WaitWorkGroup = new SPIRVKernelEnqueueFlags(2);
    public static EmptySPIRVKernelEnqueueFlags Empty = new EmptySPIRVKernelEnqueueFlags();

    protected SPIRVKernelEnqueueFlags(int value) {
        super(value);
    }

    public static class EmptySPIRVKernelEnqueueFlags extends SPIRVKernelEnqueueFlags {
        protected EmptySPIRVKernelEnqueueFlags() {
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
