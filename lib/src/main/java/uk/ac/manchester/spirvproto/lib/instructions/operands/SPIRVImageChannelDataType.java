package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVImageChannelDataType extends SPIRVEnum {
    public static SPIRVImageChannelDataType SnormInt8 = new SPIRVImageChannelDataType(0);
    public static SPIRVImageChannelDataType SnormInt16 = new SPIRVImageChannelDataType(1);
    public static SPIRVImageChannelDataType UnormInt8 = new SPIRVImageChannelDataType(2);
    public static SPIRVImageChannelDataType UnormInt16 = new SPIRVImageChannelDataType(3);
    public static SPIRVImageChannelDataType UnormShort565 = new SPIRVImageChannelDataType(4);
    public static SPIRVImageChannelDataType UnormShort555 = new SPIRVImageChannelDataType(5);
    public static SPIRVImageChannelDataType UnormInt101010 = new SPIRVImageChannelDataType(6);
    public static SPIRVImageChannelDataType SignedInt8 = new SPIRVImageChannelDataType(7);
    public static SPIRVImageChannelDataType SignedInt16 = new SPIRVImageChannelDataType(8);
    public static SPIRVImageChannelDataType SignedInt32 = new SPIRVImageChannelDataType(9);
    public static SPIRVImageChannelDataType UnsignedInt8 = new SPIRVImageChannelDataType(10);
    public static SPIRVImageChannelDataType UnsignedInt16 = new SPIRVImageChannelDataType(11);
    public static SPIRVImageChannelDataType UnsignedInt32 = new SPIRVImageChannelDataType(12);
    public static SPIRVImageChannelDataType HalfFloat = new SPIRVImageChannelDataType(13);
    public static SPIRVImageChannelDataType Float = new SPIRVImageChannelDataType(14);
    public static SPIRVImageChannelDataType UnormInt24 = new SPIRVImageChannelDataType(15);
    public static SPIRVImageChannelDataType UnormInt101010_2 = new SPIRVImageChannelDataType(16);
    public static EmptySPIRVImageChannelDataType Empty = new EmptySPIRVImageChannelDataType();

    protected SPIRVImageChannelDataType(int value) {
        super(value);
    }

    public static class EmptySPIRVImageChannelDataType extends SPIRVImageChannelDataType {
        protected EmptySPIRVImageChannelDataType() {
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
