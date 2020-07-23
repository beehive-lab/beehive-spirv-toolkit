package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVImageChannelOrder extends SPIRVEnum {
    public static SPIRVImageChannelOrder R = new SPIRVImageChannelOrder(0);
    public static SPIRVImageChannelOrder A = new SPIRVImageChannelOrder(1);
    public static SPIRVImageChannelOrder RG = new SPIRVImageChannelOrder(2);
    public static SPIRVImageChannelOrder RA = new SPIRVImageChannelOrder(3);
    public static SPIRVImageChannelOrder RGB = new SPIRVImageChannelOrder(4);
    public static SPIRVImageChannelOrder RGBA = new SPIRVImageChannelOrder(5);
    public static SPIRVImageChannelOrder BGRA = new SPIRVImageChannelOrder(6);
    public static SPIRVImageChannelOrder ARGB = new SPIRVImageChannelOrder(7);
    public static SPIRVImageChannelOrder Intensity = new SPIRVImageChannelOrder(8);
    public static SPIRVImageChannelOrder Luminance = new SPIRVImageChannelOrder(9);
    public static SPIRVImageChannelOrder Rx = new SPIRVImageChannelOrder(10);
    public static SPIRVImageChannelOrder RGx = new SPIRVImageChannelOrder(11);
    public static SPIRVImageChannelOrder RGBx = new SPIRVImageChannelOrder(12);
    public static SPIRVImageChannelOrder Depth = new SPIRVImageChannelOrder(13);
    public static SPIRVImageChannelOrder DepthStencil = new SPIRVImageChannelOrder(14);
    public static SPIRVImageChannelOrder sRGB = new SPIRVImageChannelOrder(15);
    public static SPIRVImageChannelOrder sRGBx = new SPIRVImageChannelOrder(16);
    public static SPIRVImageChannelOrder sRGBA = new SPIRVImageChannelOrder(17);
    public static SPIRVImageChannelOrder sBGRA = new SPIRVImageChannelOrder(18);
    public static SPIRVImageChannelOrder ABGR = new SPIRVImageChannelOrder(19);
    public static EmptySPIRVImageChannelOrder Empty = new EmptySPIRVImageChannelOrder();

    protected SPIRVImageChannelOrder(int value) {
        super(value);
    }

    public static class EmptySPIRVImageChannelOrder extends SPIRVImageChannelOrder {
        protected EmptySPIRVImageChannelOrder() {
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
