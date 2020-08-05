package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.SPIRVFunctionEndInst;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVFunctionInst;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVFunctionParameterInst;

import java.nio.ByteBuffer;
import java.util.List;

public class SPIRVFunctionDeclaration {
    protected SPIRVFunctionInst functionDeclaration;
    protected List<SPIRVFunctionParameterInst> parameters;
    protected SPIRVFunctionEndInst end;

    public void write(ByteBuffer output) {
        functionDeclaration.write(output);
        parameters.forEach(p -> p.write(output));
        end.write(output);
    }
}
