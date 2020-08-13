package uk.ac.manchester.spirvproto.lib.disassembler;

import java.nio.ByteOrder;
import java.util.Iterator;

public class SPIRVLine {
    private final Iterator<Integer> operands;
    private final ByteOrder byteOrder;

    public SPIRVLine(Iterator<Integer> operands, ByteOrder byteOrder) {
        this.operands = operands;
        this.byteOrder = byteOrder;
    }

    public int next() {
        return operands.next();
    }

    public boolean hasNext() {
        return operands.hasNext();
    }

    public ByteOrder getByteOrder() {
        return byteOrder;
    }
}
