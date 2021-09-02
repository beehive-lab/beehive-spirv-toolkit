package uk.ac.manchester.spirvbeehivetoolkit.generator;

public class Constants {
    public final int majorVersion;
    public final int minorVersion;
    public final int genNumber;

    public Constants(int majorVersion, int minorVersion, int genNumber) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.genNumber = genNumber;
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public int getGenNumber() {
        return genNumber;
    }
}
