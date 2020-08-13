package uk.ac.manchester.spirvproto.lib.disassembler;

import uk.ac.manchester.spirvproto.lib.SPIRVHeader;
import uk.ac.manchester.spirvproto.lib.SPIRVTool;
import uk.ac.manchester.spirvproto.lib.assembler.SPIRVInstScope;
import uk.ac.manchester.spirvproto.lib.assembler.SPIRVModule;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteOrder;
import java.util.Arrays;

public class DisassemblerV2 implements SPIRVTool {
    private final BinaryWordStream wordStream;
    private final PrintStream output;
    private final SPIRVDisassemblerOptions options;

    private SPIRVHeader header;
    private final SPIRVSyntaxHighlighter highlighter;

    //private final Map<String, String> idToNameMap;
    //private final Map<String, SPIRVNumberFormat> idToTypeMap;
    //private final Map<String, SPIRVExternalImport> externalImports;

    public DisassemblerV2(BinaryWordStream wordStream, PrintStream output, SPIRVDisassemblerOptions options) {
        this.wordStream = wordStream;
        this.output = output;
        this.options = options;

        highlighter = new CLIHighlighter();
    }

    @Override
    public void run() throws Exception {
        int magicNumber = wordStream.getNextWord();
        if (magicNumber == 0x07230203) wordStream.setEndianness(ByteOrder.LITTLE_ENDIAN);
        else if (magicNumber == 0x03022307) wordStream.setEndianness(ByteOrder.BIG_ENDIAN);
        else throw new InvalidBinarySPIRVInputException(magicNumber);

        header = new SPIRVHeader(
                magicNumber,
                wordStream.getNextWord(),
                wordStream.getNextWord(),
                wordStream.getNextWord(),
                wordStream.getNextWord()
        );

        SPIRVModule module = new SPIRVModule();
        SPIRVInstScope currentScope = module;

        int currentWord;
        while ((currentWord = wordStream.getNextWord()) != -1) {
            currentScope = processLine(currentWord, currentScope);
        }

        print(module);
    }

    private SPIRVInstScope processLine(int firstWord, SPIRVInstScope scope) throws IOException {
        int opCode = firstWord & 0xFFFF;
        int wordcount = firstWord >> 16;

        int[] line = new int[wordcount];
        line[0] = opCode;

        for (int i = 1; i < wordcount; i++) {
            line[i] = wordStream.getNextWord();
        }

        return SPIRVInstMapper.addToScope(new SPIRVLine(Arrays.stream(line).iterator(), wordStream.getEndianness()), scope);
    }

    private void print(SPIRVModule module) {
        if (!options.noHeader) output.println(header);

        module.print(output);
    }
}
