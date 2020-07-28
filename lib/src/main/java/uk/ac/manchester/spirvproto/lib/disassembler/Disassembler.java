package uk.ac.manchester.spirvproto.lib.disassembler;

import uk.ac.manchester.spirvproto.lib.SPIRVHeader;
import uk.ac.manchester.spirvproto.lib.SPIRVTool;
import uk.ac.manchester.spirvproto.lib.grammar.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private final Map<String, Integer> idToTypeMap;

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
		idToTypeMap = new HashMap<>(header.bound);
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

			    do {
					currentWordCount += decodeOperand(instruction, grammar.getOperandKind(currentOperand.getKind()));
				} while (currentOperand.getQuantifier() == '*' && currentWordCount < wordcount);
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
		else if (instruction.operationName.equals("OpTypeInt") | instruction.operationName.equals("OpTypeFloat")) {
			int widthInWords = Integer.parseInt(instruction.operands.get(0).operand) / 32;
			if (widthInWords <= 0) widthInWords = 1;
			idToTypeMap.put(instruction.result.operand, widthInWords);
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
		else if (operandKind.getKind().equals("LiteralContextDependentNumber")) {
			int width = idToTypeMap.get(instruction.operands.get(instruction.operands.size() - 1).operand);
			currentWordCount += width;
			for (int i = 0; i < width; i++) {
				instruction.operands.add(new SPIRVDecodedOperand(Integer.toString(wordStream.getNextWord()), SPIRVOperandCategory.LiteralInteger));
			}
		}
		else if (operandKind.getCategory().equals("Id")) {
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
			SPIRVEnumerant[] values;
			if (operandKind.getCategory().startsWith("Value")) {
				values = new SPIRVEnumerant[1];
				values[0] = operandKind.getEnumerant(Integer.toString(wordStream.getNextWord()));
			}
			else {
				// For now it can only be a BitEnum
				int value = wordStream.getNextWord();
				List<SPIRVEnumerant> flags = new ArrayList<>(1);
				for (SPIRVEnumerant enumerant : operandKind.getEnumerants()) {
				    int mask = Integer.decode(enumerant.getValue());
				    if ((mask & value) > 0) flags.add(enumerant);
				}
				//value = String.format("0x%04x", wordStream.getNextWord());
				values = flags.toArray(new SPIRVEnumerant[0]);
			}
			currentWordCount++;
			for (SPIRVEnumerant enumerant : values) {
				instruction.operands.add(new SPIRVDecodedOperand(enumerant.getName(), SPIRVOperandCategory.Enum));
				if (enumerant.getParameters() != null) {
					for (int j = 0; j < enumerant.getParameters().length; j++) {
						SPIRVOperandKind paramKind = grammar.getOperandKind(enumerant.getParameters()[j].getKind());
						currentWordCount += decodeOperand(instruction, paramKind);
					}
				}
			}
		}
		else if (operandKind.getCategory().equals("Composite")) {
			String[] bases = operandKind.getBases();
			instruction.operands.add(new SPIRVDecodedOperand("{", SPIRVOperandCategory.Token));
			for (String base : bases) {
				SPIRVOperandKind member = new SPIRVOperandKind();
				member.kind = base;
				if (base.startsWith("Literal")) member.category = "Literal";
				else if (base.startsWith("Id")) member.category = "Id";

				currentWordCount += decodeOperand(instruction, member);
			}
			instruction.operands.add(new SPIRVDecodedOperand("}", SPIRVOperandCategory.Token));
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