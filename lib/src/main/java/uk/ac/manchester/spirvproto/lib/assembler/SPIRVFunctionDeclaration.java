package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.*;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVFunctionControl;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class SPIRVFunctionDeclaration {
    protected SPIRVFunctionInst functionDeclaration;
    protected List<SPIRVFunctionParameterInst> parameters;
    protected SPIRVFunctionEndInst end;

    public SPIRVFunctionDeclaration(SPIRVId resultType,
                                    SPIRVId funcType,
                                    SPIRVId result,
                                    SPIRVFunctionControl control,
                                    SPIRVFunctionParameterInst... params) {
        functionDeclaration = new SPIRVOpFunction(resultType, result, control, funcType);
        parameters = Arrays.asList(params);
        end = new SPIRVOpFunctionEnd();
    }

    public SPIRVFunctionDeclaration(SPIRVFunctionInst instruction) {
        functionDeclaration = instruction;
        parameters = new ArrayList<>();
    }

    public void forEachInstruction(Consumer<SPIRVInstruction> instructionConsumer) {
        instructionConsumer.accept(functionDeclaration);
        parameters.forEach(instructionConsumer);
        instructionConsumer.accept(end);
    }
}
