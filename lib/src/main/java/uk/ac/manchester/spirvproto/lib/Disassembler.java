package uk.ac.manchester.spirvproto.lib;

import uk.ac.manchester.spirvproto.lib.grammar.*;

import java.io.IOException;
import java.io.PrintStream;

public class Disassembler {
	private final BinaryWordStream wordStream;
	private final PrintStream output;
	private final SPIRVHeader header;
	private final SPIRVGrammar grammar;

	public Disassembler(BinaryWordStream wordStream, PrintStream output) throws InvalidBinarySPIRVInputException, IOException {
		this.wordStream = wordStream;
		this.output = output;

		grammar = SPIRVSpecification.buildSPIRVGrammar();

		int magicNumber = wordStream.getNextWord();
		if (magicNumber != 0x07230203) {
			if (magicNumber == 0x03022307) {
				wordStream.changeEndianness();
				magicNumber = 0x07230203;
			}
			else {
				throw new InvalidBinarySPIRVInputException();
			}
		}

		header = new SPIRVHeader(magicNumber,
				wordStream.getNextWord(),
				wordStream.getNextWord(),
				wordStream.getNextWord(),
				wordStream.getNextWord());
	}

	public SPIRVHeader getHeader() {
		return header;
	}

	public void disassemble() throws IOException {
		int currentWord;
		while ((currentWord = wordStream.getNextWord()) != -1) {
			int opcode = currentWord & 0xFFFF;
			int wordcount = currentWord >> 16;
			SPIRVInstruction currentInstruction = grammar.getInstructionByOpCode(opcode);
            int operandsLength = (currentInstruction.operands != null) ? currentInstruction.operands.length : 0;
            output.print(currentInstruction + "(" + wordcount + ")" + "(" + operandsLength + ")" + " -");

			int currentWordCount = 1;
			for (int i = 0; i < operandsLength && currentWordCount < wordcount; i++) {
			    SPIRVOperand currentOperand = currentInstruction.operands[i];

                if (currentOperand.kind.equals("LiteralString")) {
					output.print(" \"");
					byte[] word;
					do {
						word = wordStream.getNextWordInBytes(true); currentWordCount++;
						output.print(new String(word));
						//output.print(Arrays.toString(word) + " ");
					} while (word[word.length - 1] != 0);
					output.print("\" ");
				}
                else {
					output.print(" " + wordStream.getNextWord()); currentWordCount++;
				}
			}
            for (int i = 0; i < wordcount - currentWordCount; i++) {
                wordStream.getNextWord();
            }
			output.println();
		}
	}

	@Override
	public String toString() {
		return "SPIR-V Disassembler";
	}
}