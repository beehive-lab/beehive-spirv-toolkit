package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVFunctionParameterAttribute extends SPIRVEnum {
    public static SPIRVFunctionParameterAttribute Zext = new SPIRVFunctionParameterAttribute(0);
    public static SPIRVFunctionParameterAttribute Sext = new SPIRVFunctionParameterAttribute(1);
    public static SPIRVFunctionParameterAttribute ByVal = new SPIRVFunctionParameterAttribute(2);
    public static SPIRVFunctionParameterAttribute Sret = new SPIRVFunctionParameterAttribute(3);
    public static SPIRVFunctionParameterAttribute NoAlias = new SPIRVFunctionParameterAttribute(4);
    public static SPIRVFunctionParameterAttribute NoCapture = new SPIRVFunctionParameterAttribute(5);
    public static SPIRVFunctionParameterAttribute NoWrite = new SPIRVFunctionParameterAttribute(6);
    public static SPIRVFunctionParameterAttribute NoReadWrite = new SPIRVFunctionParameterAttribute(7);
    public static EmptySPIRVFunctionParameterAttribute Empty = new EmptySPIRVFunctionParameterAttribute();

    protected SPIRVFunctionParameterAttribute(int value) {
        super(value);
    }

    public static class EmptySPIRVFunctionParameterAttribute extends SPIRVFunctionParameterAttribute {
        protected EmptySPIRVFunctionParameterAttribute() {
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
