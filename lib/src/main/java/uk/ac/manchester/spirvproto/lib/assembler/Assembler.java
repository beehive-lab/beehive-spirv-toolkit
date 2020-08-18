package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.SPIRVTool;
import uk.ac.manchester.spirvproto.lib.grammar.SPIRVGrammar;
import uk.ac.manchester.spirvproto.lib.grammar.SPIRVSpecification;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assembler implements SPIRVTool {
    private final BufferedReader reader;
    private final File out;
    private final SPIRVGrammar grammar;

    public Assembler(Reader reader) throws IOException {
        this(reader, null);
    }

    public Assembler(Reader reader, File out) throws IOException {
        this.reader = new BufferedReader(reader);
        this.out = out;
        grammar = SPIRVSpecification.buildSPIRVGrammar(0, 0);
    }

    public SPIRVModule assemble() {
        SPIRVModule module = new SPIRVModule(true);
        final SPIRVInstScope[] currentScope = new SPIRVInstScope[]{module};
        reader.lines().forEach(line -> currentScope[0] = processLine(line, currentScope[0]));

        return module;
    }

    private SPIRVInstScope processLine(String line, SPIRVInstScope scope) {
        SPIRVToken[] tokens = tokenize(line);
        if (tokens.length <= 0) return scope;

        SPIRVToken instruction = null;
        List<SPIRVToken> operands = new ArrayList<>();
        for (SPIRVToken token : tokens) {
            if (token.type == SPIRVTokenType.COMMENT) break;
            else if (token.type == SPIRVTokenType.INSTRUCTION) instruction = token;
            else if (token.isOperand()) operands.add(token);
        }

        if (instruction == null) return scope;

        return SPIRVInstMapper.addToScope(instruction, operands.toArray(new SPIRVToken[0]), scope);
    }

    private SPIRVToken[] tokenize(String line) {
        String[] tokens = line.split("\\s+");
        return Arrays.stream(tokens)
                .filter(s -> !s.isEmpty())
                .map(token -> new SPIRVToken(token, grammar))
                .toArray(SPIRVToken[]::new);
    }

    @Override
    public void run() throws InvalidSPIRVModuleException, IOException {
        SPIRVModule module = assemble();
        ByteBuffer buffer = ByteBuffer.allocate(module.getByteCount()).order(ByteOrder.LITTLE_ENDIAN);
        module.validate().write(buffer);

        if (out == null) return;

        buffer.flip();
        FileChannel channel = new FileOutputStream(out, false).getChannel();
        channel.write(buffer);
        channel.close();
    }
}