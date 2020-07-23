package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVGroupOperation extends SPIRVEnum {
    public static SPIRVGroupOperation Reduce = new SPIRVGroupOperation(0);
    public static SPIRVGroupOperation InclusiveScan = new SPIRVGroupOperation(1);
    public static SPIRVGroupOperation ExclusiveScan = new SPIRVGroupOperation(2);
    public static EmptySPIRVGroupOperation Empty = new EmptySPIRVGroupOperation();

    protected SPIRVGroupOperation(int value) {
        super(value);
    }

    public static class EmptySPIRVGroupOperation extends SPIRVGroupOperation {
        protected EmptySPIRVGroupOperation() {
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
