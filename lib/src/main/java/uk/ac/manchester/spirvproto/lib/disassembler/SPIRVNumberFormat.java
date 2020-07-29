package uk.ac.manchester.spirvproto.lib.disassembler;

public class SPIRVNumberFormat {
    public final int width;
    public final boolean isFloating;

    public SPIRVNumberFormat(int width, boolean isFloating) {
        this.width = width;
        this.isFloating = isFloating;
    }
}
