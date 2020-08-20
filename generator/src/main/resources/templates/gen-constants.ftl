package uk.ac.manchester.spirvproto.lib;

import javax.annotation.Generated;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVGeneratorConstants {
    public static int SPIRVMajorVersion = ${majorVersion};
    public static int SPIRVMinorVersion = ${minorVersion};
    public static int SPIRVGenMagicNumber = ${genNumber?string.computer};
}
