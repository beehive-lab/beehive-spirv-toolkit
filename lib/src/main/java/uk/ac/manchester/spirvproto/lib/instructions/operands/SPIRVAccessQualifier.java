package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVAccessQualifier extends SPIRVEnum {
    public static SPIRVAccessQualifier ReadOnly = new SPIRVAccessQualifier(0);
    public static SPIRVAccessQualifier WriteOnly = new SPIRVAccessQualifier(1);
    public static SPIRVAccessQualifier ReadWrite = new SPIRVAccessQualifier(2);
    public static EmptySPIRVAccessQualifier Empty = new EmptySPIRVAccessQualifier();

    protected SPIRVAccessQualifier(int value) {
        super(value);
    }

    public static class EmptySPIRVAccessQualifier extends SPIRVAccessQualifier {
        protected EmptySPIRVAccessQualifier() {
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
