package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVSelectionControl extends SPIRVEnum {
    public static SPIRVSelectionControl None = new SPIRVSelectionControl(0x0000);
    public static SPIRVSelectionControl Flatten = new SPIRVSelectionControl(0x0001);
    public static SPIRVSelectionControl DontFlatten = new SPIRVSelectionControl(0x0002);
    public static EmptySPIRVSelectionControl Empty = new EmptySPIRVSelectionControl();

    protected SPIRVSelectionControl(int value) {
        super(value);
    }

    public static class EmptySPIRVSelectionControl extends SPIRVSelectionControl {
        protected EmptySPIRVSelectionControl() {
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
