package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVFunctionControl;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVIdRef;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVIdResultType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SPIRVFunctionDeclaration {
    protected SPIRVFunctionInst functionDeclaration;
    protected List<SPIRVFunctionParameterInst> parameters;
    protected SPIRVFunctionEndInst end;

    public SPIRVFunctionDeclaration(SPIRVIdResultType resultType, SPIRVIdRef funcType, SPIRVFunctionControl control, SPIRVFunctionParameterInst... params) {
        functionDeclaration = new SPIRVOpFunction(resultType, null, control, funcType);
        parameters = Arrays.asList(params);
        end = new SPIRVOpFunctionEnd();
    }

    public void write(ByteBuffer output) {
        functionDeclaration.write(output);
        parameters.forEach(p -> p.write(output));
        end.write(output);
    }
}
