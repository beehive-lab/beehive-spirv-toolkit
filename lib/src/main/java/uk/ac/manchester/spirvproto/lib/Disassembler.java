package uk.ac.manchester.spirvproto.lib;

import uk.ac.manchester.spirvproto.lib.grammar.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

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
		int opcode;
		int wordcount;
		int operandsLength;
		int currentWordCount;

		int result;
		String op;
		List<String> operands;

		SPIRVInstruction currentInstruction;

		while ((currentWord = wordStream.getNextWord()) != -1) {
			opcode = currentWord & 0xFFFF;
			wordcount = currentWord >> 16;
			currentInstruction = grammar.getInstructionByOpCode(opcode);
            operandsLength = (currentInstruction.operands != null) ? currentInstruction.operands.length : 0;
            op = currentInstruction.toString();

            result = -1;
			currentWordCount = 1;
			operands = new ArrayList<>();
			for (int i = 0; i < operandsLength && currentWordCount < wordcount; i++) {
			    SPIRVOperand currentOperand = currentInstruction.operands[i];
			    SPIRVOperandKind currentOperandKind = grammar.getOperandKind(currentOperand.kind);
                String operandCategory = grammar.getOperandKind(currentOperand.kind).category;

				if (currentOperand.kind.equals("LiteralString")) {
					StringBuilder sb = new StringBuilder(" \"");
					byte[] word;
					do {
						word = wordStream.getNextWordInBytes(true); currentWordCount++;
						sb.append(new String(word));
					} while (word[word.length - 1] != 0);
					sb.append("\" ");
					operands.add(sb.toString());
				}
				else if (currentOperand.kind.startsWith("Id")) {
					if (currentOperand.kind.equals("IdResult")) {
						result = wordStream.getNextWord();
					}
					else {
						operands.add(" %" + wordStream.getNextWord());
					}
					currentWordCount++;
				}
				else if (operandCategory.equals("ValueEnum")) {
                    SPIRVEnumerant enumerant = currentOperandKind.getEnumerant(wordStream.getNextWord()); currentWordCount++;
                    operands.add(" " + enumerant.name);
                    if (enumerant.parameters != null) {
                        for (int j = 0; j < enumerant.parameters.length; j++) {
                            operands.add(" " + wordStream.getNextWord()); currentWordCount++;
                        }
                    }
                }
                else {
					operands.add(" " + wordStream.getNextWord()); currentWordCount++;
				}
			}
            for (int i = 0; i < wordcount - currentWordCount; i++) {
                operands.add(" 0x" + Integer.toHexString(wordStream.getNextWord()) + " ");
            }

			if (result >= 0) {
				output.print("%" + result + " = ");
			}
			output.print(op);
			for (String operand : operands) {
				output.print(operand);
			}
			output.println();
		}
	}

	@Override
	public String toString() {
		return "SPIR-V Disassembler";
	}
}