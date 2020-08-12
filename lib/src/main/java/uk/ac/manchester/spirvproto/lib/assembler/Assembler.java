package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.grammar.SPIRVGrammar;
import uk.ac.manchester.spirvproto.lib.grammar.SPIRVSpecification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Assembler {
    private final BufferedReader reader;
    private final SPIRVGrammar grammar;

    public Assembler(Reader reader) throws IOException {
        this.reader = new BufferedReader(reader);
        grammar = SPIRVSpecification.buildSPIRVGrammar(0, 0);
    }

    public SPIRVModule assemble() {
        SPIRVModule module = new SPIRVModule();
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
}