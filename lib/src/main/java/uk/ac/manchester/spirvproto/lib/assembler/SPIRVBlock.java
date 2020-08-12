package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.SPIRVInstruction;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVLabelInst;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVOpLabel;
import uk.ac.manchester.spirvproto.lib.instructions.SPIRVTerminationInst;
import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SPIRVBlock implements SPIRVInstScope {
    private final SPIRVLabelInst label;
    private final SPIRVIdGenerator idGen;
    private final SPIRVInstScope enclosingScope;
    private final List<SPIRVInstruction> instructions;
    private SPIRVTerminationInst end;

    public SPIRVBlock(SPIRVIdGenerator idGen) {
        label = new SPIRVOpLabel(idGen.getNextId());
        this.idGen = idGen;
        instructions = new ArrayList<>();
        enclosingScope = null;
    }

    public SPIRVBlock(SPIRVLabelInst instruction, SPIRVInstScope enclosingScope) {
        label = instruction;
        this.enclosingScope = enclosingScope;
        this.idGen = enclosingScope.getIdGen();
        instructions = new ArrayList<>();
    }

    public void addInstruction(SPIRVInstruction instruction) {
        if (instruction instanceof SPIRVTerminationInst) end = (SPIRVTerminationInst) instruction;
        else instructions.add(instruction);
    }

    public void write(ByteBuffer output) {
        label.write(output);
        instructions.forEach(i -> i.write(output));
        end.write(output);
    }

    public int getWordCount() {
        int wordCount = label.getWordCount() + end.getWordCount();
        wordCount += instructions.stream().mapToInt(SPIRVInstruction::getWordCount).sum();

        return wordCount;
    }

    @Override
    public SPIRVInstScope add(SPIRVInstruction instruction) {
        if (instruction instanceof SPIRVTerminationInst) {
            end = (SPIRVTerminationInst) instruction;
            return enclosingScope;
        }

        instructions.add(instruction);
        return this;
    }

    @Override
    public SPIRVId getOrCreateId(String name) {
        return idGen.getOrCreateId(name);
    }

    @Override
    public SPIRVIdGenerator getIdGen() {
        return idGen;
    }
}
