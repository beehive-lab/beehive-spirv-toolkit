package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVFunctionControl extends SPIRVEnum {
    public static SPIRVFunctionControl None = new SPIRVFunctionControl(0x0000);
    public static SPIRVFunctionControl Inline = new SPIRVFunctionControl(0x0001);
    public static SPIRVFunctionControl DontInline = new SPIRVFunctionControl(0x0002);
    public static SPIRVFunctionControl Pure = new SPIRVFunctionControl(0x0004);
    public static SPIRVFunctionControl Const = new SPIRVFunctionControl(0x0008);
    public static EmptySPIRVFunctionControl Empty = new EmptySPIRVFunctionControl();

    protected SPIRVFunctionControl(int value) {
        super(value);
    }

    public static class EmptySPIRVFunctionControl extends SPIRVFunctionControl {
        protected EmptySPIRVFunctionControl() {
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
