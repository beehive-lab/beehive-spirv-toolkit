package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.SPIRVInstruction;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVLabelInst;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVTerminationInst;

import java.nio.ByteBuffer;
import java.util.List;

public class SPIRVBlock {
    private SPIRVLabelInst label;
    private List<SPIRVInstruction> instructions;
    private SPIRVTerminationInst end;

    public void write(ByteBuffer output) {
        label.write(output);
        instructions.forEach(i -> i.write(output));
        end.write(output);
    }
}
