package uk.ac.manchester.spirvproto.lib.instructions.operands;

public abstract class SPIRVEnum {
    protected final int value;

    protected SPIRVEnum(int value) {
        this.value = value;
    }
}
