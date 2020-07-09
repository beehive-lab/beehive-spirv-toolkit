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

		grammar = SPIRVSpecification.buildSPIRVGrammar(header.majorVersion, header.minorVersion);
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
			    int operandCount = 1;
			    // If the quantifier is * that means this is the last operand and there could be 0 or more of it
				// It can be determined by the wordcount how many there is left
			    if (currentOperand.quantifier == '*') {
			    	operandCount = wordcount - currentWordCount;
				}

			    if (currentOperand.kind.equals("IdResult")) {
			    	result = wordStream.getNextWord(); currentWordCount++;
				}
			    else {
					for (int j = 0; j < operandCount; j++) {
						currentWordCount += decodeOperand(operands, grammar.getOperandKind(currentOperand.kind));
					}
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

	private int decodeOperand(List<String> decodedOperands, SPIRVOperandKind operandKind) throws IOException {
		int currentWordCount = 0;
		if (operandKind.kind.equals("LiteralString")) {
			StringBuilder sb = new StringBuilder(" \"");
			byte[] word;
			do {
				word = wordStream.getNextWordInBytes(true); currentWordCount++;
				sb.append(new String(word));
			} while (word[word.length - 1] != 0);
			sb.append("\" ");
			decodedOperands.add(sb.toString());
		}
		else if (operandKind.kind.startsWith("Id")) {
			decodedOperands.add(" %" + wordStream.getNextWord());
			currentWordCount++;
		}
		else if (operandKind.category.endsWith("Enum")) {
			String value;
			if (operandKind.category.startsWith("Value")) {
				value = Integer.toString(wordStream.getNextWord());
			}
			else {
				// For now it can only be a BitEnum
				value = String.format("0x%04x", wordStream.getNextWord());
			}
			currentWordCount++;
			SPIRVEnumerant enumerant = operandKind.getEnumerant(value);
			decodedOperands.add(" " + enumerant.name);
			if (enumerant.parameters != null) {
				for (int j = 0; j < enumerant.parameters.length; j++) {
					SPIRVOperandKind paramKind = grammar.getOperandKind(enumerant.parameters[j].kind);
					currentWordCount += decodeOperand(decodedOperands, paramKind);
				}
			}
		}
		else {
			// by now it can only a LiteralInteger or a Composite
			// TODO: Composite type category decoding
			decodedOperands.add(" " + wordStream.getNextWord()); currentWordCount++;
		}
		return currentWordCount;
	}

	@Override
	public String toString() {
		return "SPIR-V Disassembler";
	}
}