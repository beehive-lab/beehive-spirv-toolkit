package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVSourceLanguage extends SPIRVEnum {
    public static SPIRVSourceLanguage Unknown = new SPIRVSourceLanguage(0);
    public static SPIRVSourceLanguage ESSL = new SPIRVSourceLanguage(1);
    public static SPIRVSourceLanguage GLSL = new SPIRVSourceLanguage(2);
    public static SPIRVSourceLanguage OpenCL_C = new SPIRVSourceLanguage(3);
    public static SPIRVSourceLanguage OpenCL_CPP = new SPIRVSourceLanguage(4);
    public static SPIRVSourceLanguage HLSL = new SPIRVSourceLanguage(5);
    public static EmptySPIRVSourceLanguage Empty = new EmptySPIRVSourceLanguage();

    protected SPIRVSourceLanguage(int value) {
        super(value);
    }

    public static class EmptySPIRVSourceLanguage extends SPIRVSourceLanguage {
        protected EmptySPIRVSourceLanguage() {
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
