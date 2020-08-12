package uk.ac.manchester.spirvproto.lib;

import java.nio.ByteBuffer;

public class SPIRVHeader {
    public final int magicNumber;
    public final int majorVersion;
    public final int minorVersion;
    public final int genMagicNumber;
    public final int bound;
    public final int schema;


    public SPIRVHeader(int magicNumber, int version, int genMagicNumber, int bound, int schema) {
        this.magicNumber = magicNumber;
        this.genMagicNumber = genMagicNumber >> 16;
        this.bound = bound;
        this.schema = schema;

        minorVersion = (version >> 8)  & 0xFF;
        majorVersion = (version >> 16) & 0xFF;
    }

    @Override
    public String toString() {

        return String.format("; MagicNumber: 0x%x\n", magicNumber) +
                String.format("; Version: %d.%d\n", majorVersion, minorVersion) +
                String.format("; Generator ID: %d\n", genMagicNumber) +
                String.format("; Bound: %d\n", bound) +
                String.format("; Schema: %d\n", schema);
    }

    public void write(ByteBuffer output) {
        output.putInt(magicNumber);

        int version = 0;
        version |= (minorVersion << 8);
        version |= (majorVersion << 16);
        output.putInt(version);

        output.putInt(genMagicNumber);
        output.putInt(bound);
        output.putInt(schema);
    }
}
