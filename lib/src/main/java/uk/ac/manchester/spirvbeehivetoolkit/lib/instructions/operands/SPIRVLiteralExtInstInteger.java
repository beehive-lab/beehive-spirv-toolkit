package uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands;

import uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler.SPIRVPrintingOptions;

import java.io.PrintStream;

public class SPIRVLiteralExtInstInteger extends SPIRVLiteralInteger {
    private final String name;

    public SPIRVLiteralExtInstInteger(int value, String name) {
        super(value);
        this.name = name;
    }

    @Override
    public void print(PrintStream output, SPIRVPrintingOptions options) {
        output.print(name);
    }
}
