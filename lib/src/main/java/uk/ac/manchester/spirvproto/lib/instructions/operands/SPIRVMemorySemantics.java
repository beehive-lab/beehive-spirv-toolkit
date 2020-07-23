package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVMemorySemantics extends SPIRVEnum {
    public static SPIRVMemorySemantics Relaxed = new SPIRVMemorySemantics(0x0000);
    public static SPIRVMemorySemantics None = new SPIRVMemorySemantics(0x0000);
    public static SPIRVMemorySemantics Acquire = new SPIRVMemorySemantics(0x0002);
    public static SPIRVMemorySemantics Release = new SPIRVMemorySemantics(0x0004);
    public static SPIRVMemorySemantics AcquireRelease = new SPIRVMemorySemantics(0x0008);
    public static SPIRVMemorySemantics SequentiallyConsistent = new SPIRVMemorySemantics(0x0010);
    public static SPIRVMemorySemantics UniformMemory = new SPIRVMemorySemantics(0x0040);
    public static SPIRVMemorySemantics SubgroupMemory = new SPIRVMemorySemantics(0x0080);
    public static SPIRVMemorySemantics WorkgroupMemory = new SPIRVMemorySemantics(0x0100);
    public static SPIRVMemorySemantics CrossWorkgroupMemory = new SPIRVMemorySemantics(0x0200);
    public static SPIRVMemorySemantics AtomicCounterMemory = new SPIRVMemorySemantics(0x0400);
    public static SPIRVMemorySemantics ImageMemory = new SPIRVMemorySemantics(0x0800);
    public static EmptySPIRVMemorySemantics Empty = new EmptySPIRVMemorySemantics();

    protected SPIRVMemorySemantics(int value) {
        super(value);
    }

    public static class EmptySPIRVMemorySemantics extends SPIRVMemorySemantics {
        protected EmptySPIRVMemorySemantics() {
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
