package uk.ac.manchester.spirvproto.lib.disassembler;

public class SPIRVNumberFormat {
    public final int width;
    public final boolean isFloating;
    public final boolean isSigned;

    public SPIRVNumberFormat(int width, boolean isFloating, boolean isSigned) {
        this.width = width;
        this.isFloating = isFloating;
        this.isSigned = isSigned;
    }
}
