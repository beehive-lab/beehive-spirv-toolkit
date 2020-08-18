package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRVLiteralExtInstInteger extends SPIRVLiteralInteger {
    private final String name;

    public SPIRVLiteralExtInstInteger(int value, String name) {
        super(value);
        this.name = name;
    }
}
