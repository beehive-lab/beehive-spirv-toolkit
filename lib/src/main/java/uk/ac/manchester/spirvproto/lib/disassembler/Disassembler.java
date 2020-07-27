package uk.ac.manchester.spirvproto.lib.disassembler;

import uk.ac.manchester.spirvproto.lib.SPIRVHeader;
import uk.ac.manchester.spirvproto.lib.SPIRVTool;
import uk.ac.manchester.spirvproto.lib.grammar.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Disassembler implements SPIRVTool {
	private final BinaryWordStream wordStream;
	private final PrintStream output;
	private final SPIRVHeader header;
	private final SPIRVGrammar grammar;
	private final SPIRVSyntaxHighlighter highlighter;
	private final boolean shouldHighlight;
	private final boolean shouldInlineNames;
	private final Map<String, String> idToNameMap;

	public Disassembler(BinaryWordStream wordStream, PrintStream output, boolean shouldHighlight, boolean shouldInlineNames) throws InvalidBinarySPIRVInputException, IOException {
		this.wordStream = wordStream;
		this.output = output;
		this.shouldHighlight = shouldHighlight;
		this.shouldInlineNames = shouldInlineNames;
		highlighter = new CLIHighlighter();

		int magicNumber = wordStream.getNextWord();
		if (magicNumber != 0x07230203) {
			if (magicNumber == 0x03022307) {
				wordStream.changeEndianness();
				magicNumber = 0x07230203;
			}
			else {
				throw new InvalidBinarySPIRVInputException(magicNumber);
			}
		}

		header = new SPIRVHeader(magicNumber,
				wordStream.getNextWord(),
				wordStream.getNextWord(),
				wordStream.getNextWord(),
				wordStream.getNextWord());

		idToNameMap = new HashMap<>(header.bound);
		grammar = SPIRVSpecification.buildSPIRVGrammar(header.majorVersion, header.minorVersion);
	}

	public void disassemble() throws IOException, InvalidSPIRVOpcodeException, InvalidSPIRVOperandKindException, InvalidSPIRVEnumerantException, InvalidSPIRVWordCountException {
		output.println(this.header);

		int currentWord;
		int opcode;
		int wordcount;
		int currentWordCount;
		int requiredOperandCount;
		SPIRVDecodedInstruction instruction;
		SPIRVInstruction currentInstruction;

		while ((currentWord = wordStream.getNextWord()) != -1) {
			opcode = currentWord & 0xFFFF;
			wordcount = currentWord >> 16;
			currentInstruction = grammar.getInstructionByOpCode(opcode);
			requiredOperandCount = currentInstruction.getRequiredOperandCount();
			instruction = new SPIRVDecodedInstruction(currentInstruction.toString(), requiredOperandCount);

			currentWordCount = 1;
			int decodedOperands = 0;
			for (; decodedOperands < currentInstruction.getOperandCount() && currentWordCount < wordcount; decodedOperands++) {
			    SPIRVOperand currentOperand = currentInstruction.getOperands()[decodedOperands];

			    // If the quantifier is * that means this is the last operand and there could be 0 or more of it
				// It can be determined by the wordcount how many there is left
				int operandCount = 1;
				if (currentOperand.getQuantifier() == '*') {
			    	operandCount = wordcount - currentWordCount;
				}

				for (int j = 0; j < operandCount; j++) {
					currentWordCount += decodeOperand(instruction, grammar.getOperandKind(currentOperand.getKind()));
				}
			}

			if (decodedOperands < requiredOperandCount) throw new InvalidSPIRVWordCountException(currentInstruction, requiredOperandCount, wordcount);

			//TODO better message and custom exception
            if (wordcount > currentWordCount) throw new RuntimeException("There are operands that were not decoded");

			processSpecialOps(instruction);
			printInstruction(instruction);
		}
	}

	private void printInstruction(SPIRVDecodedInstruction instruction) {
		int boundLength = (int) Math.log10(header.bound) + 1;
		int opStart = 4 + boundLength;

		SPIRVDecodedOperand result = instruction.result;
		if (result != null) {
			opStart -= result.operand.length() + 3;
			printOperand(result);
			output.print(" = ");
		}

		for (int i = 0; i < opStart; i++) {
			output.print(" ");
		}

		output.print(instruction.operationName);
		for (SPIRVDecodedOperand operand : instruction.operands) {
			output.print(" ");
			printOperand(operand);
		}
		output.println();
	}

	private void printOperand(SPIRVDecodedOperand op) {
		String toPrint;
		if (shouldHighlight) {
			toPrint = highlighter.highlight(op);
		}
		else {
			toPrint = op.operand;
		}
		output.print(toPrint);
	}

	private void processSpecialOps(SPIRVDecodedInstruction instruction) {
		if (instruction.operationName.equals("OpName")) {
			String name = instruction.operands.get(1).operand;
			name = "%" + name.substring(1, name.length() - 1);
			idToNameMap.put(instruction.operands.get(0).operand, name);
		}
	}

	private int decodeOperand(SPIRVDecodedInstruction instruction, SPIRVOperandKind operandKind) throws IOException, InvalidSPIRVEnumerantException, InvalidSPIRVOperandKindException {
		int currentWordCount = 0;
		if (operandKind.getKind().equals("LiteralString")) {
			StringBuilder sb = new StringBuilder("\"");
			byte[] word;
			do {
				word = wordStream.getNextWordInBytes(true); currentWordCount++;
				String operandShard = new String(word);
				if (word[word.length - 1] == 0) {
					// Remove trailing zeros
					int index = -1;
					for (int i = word.length - 1; i >= 0 && index < 0; i--) {
						if (word[i] != 0) {
							index = i;
						}
					}
					operandShard = operandShard.substring(0, index + 1);
				}
				sb.append(operandShard);
			} while (word[word.length - 1] != 0);
			sb.append("\"");
			String result = sb.toString();
			instruction.operands.add(new SPIRVDecodedOperand(result, SPIRVOperandCategory.LiteralString));
		}
		else if (operandKind.getKind().startsWith("Id")) {
			String result = "%" + wordStream.getNextWord(); currentWordCount++;
			if (shouldInlineNames) {
				if (idToNameMap.containsKey(result)) {
					result = idToNameMap.get(result);
				}
			}
			SPIRVDecodedOperand id = new SPIRVDecodedOperand(result, SPIRVOperandCategory.ID);
			if (operandKind.getKind().equals("IdResult")) {
				instruction.setResult(id);
			} else {
				instruction.operands.add(id);
			}
		}
		else if (operandKind.getCategory().endsWith("Enum")) {
			String value;
			if (operandKind.getCategory().startsWith("Value")) {
				value = Integer.toString(wordStream.getNextWord());
			}
			else {
				// For now it can only be a BitEnum
				value = String.format("0x%04x", wordStream.getNextWord());
			}
			currentWordCount++;
			SPIRVEnumerant enumerant = operandKind.getEnumerant(value);
			instruction.operands.add(new SPIRVDecodedOperand(enumerant.getName(), SPIRVOperandCategory.Enum));
			if (enumerant.getParameters() != null) {
				for (int j = 0; j < enumerant.getParameters().length; j++) {
					SPIRVOperandKind paramKind = grammar.getOperandKind(enumerant.getParameters()[j].getKind());
					currentWordCount += decodeOperand(instruction, paramKind);
				}
			}
		}
		else if (operandKind.getCategory().equals("Composite")) {
			throw new RuntimeException("Composite operand decoding is not implemented yet");
		}
		else {
			// By now it can only be a Literal(Integer)
			String result = Integer.toString(wordStream.getNextWord()); currentWordCount++;
			instruction.operands.add(new SPIRVDecodedOperand(result, SPIRVOperandCategory.LiteralInteger));
		}
		return currentWordCount;
	}

	@Override
	public String toString() {
		return "SPIR-V Disassembler";
	}

	@Override
	public void run() throws Exception {
		disassemble();
	}
}