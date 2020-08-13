package uk.ac.manchester.spirvproto.lib.disassembler;

import uk.ac.manchester.spirvproto.lib.SPIRVHeader;
import uk.ac.manchester.spirvproto.lib.SPIRVTool;
import uk.ac.manchester.spirvproto.lib.grammar.*;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.stream.Collectors;

public class Disassembler implements SPIRVTool {
	private final BinaryWordStream wordStream;
	private final PrintStream output;
	private final SPIRVHeader header;
	private final SPIRVGrammar grammar;
	private final SPIRVSyntaxHighlighter highlighter;

	private final Map<String, String> idToNameMap;
	private final Map<String, SPIRVNumberFormat> idToTypeMap;
	private final SPIRVDisassemblerOptions options;
	private final Map<String, SPIRVExternalImport> externalImports;

	public Disassembler(BinaryWordStream wordStream,
						PrintStream output,
						SPIRVDisassemblerOptions options) throws InvalidBinarySPIRVInputException, IOException {

		this.wordStream = wordStream;
		this.output = output;
		this.options = options;
		highlighter = new CLIHighlighter();
		externalImports = new HashMap<>(1);

		int magicNumber = wordStream.getNextWord();
		if (magicNumber == 0x07230203) wordStream.setEndianness(ByteOrder.LITTLE_ENDIAN);
		else if (magicNumber == 0x03022307) wordStream.setEndianness(ByteOrder.BIG_ENDIAN);
		else throw new InvalidBinarySPIRVInputException(magicNumber);

		header = new SPIRVHeader(magicNumber,
				wordStream.getNextWord(),
				wordStream.getNextWord(),
				wordStream.getNextWord(),
				wordStream.getNextWord());

		idToNameMap = new HashMap<>(header.bound);
		idToTypeMap = new HashMap<>(header.bound);
		grammar = SPIRVSpecification
				.buildSPIRVGrammar(header.majorVersion, header.minorVersion);
	}

	public void disassemble() throws IOException, InvalidSPIRVOpcodeException, InvalidSPIRVOperandKindException, InvalidSPIRVEnumerantException, InvalidSPIRVWordCountException, SPIRVUnsupportedExternalImport {
		if (!options.noHeader) output.println(this.header);

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
			instruction = new SPIRVDecodedInstruction(
					currentInstruction.toString(), requiredOperandCount);

			currentWordCount = 1;
			int decodedOperands = 0;
			for (; (decodedOperands < currentInstruction.getOperandCount()) && (currentWordCount < wordcount); decodedOperands++) {
			    SPIRVOperand currentOperand = currentInstruction.getOperands()[decodedOperands];

			    do {
					currentWordCount += decodeOperand(instruction,
							grammar.getOperandKind(currentOperand.getKind()));

				} while (currentOperand.getQuantifier() == '*' && currentWordCount < wordcount);
			}

			if (decodedOperands < requiredOperandCount) {
				throw new InvalidSPIRVWordCountException(
						currentInstruction,
						requiredOperandCount,
						wordcount);
			}

			processSpecialOps(instruction);
			printInstruction(instruction);
			//TODO better message and custom exception

			if (wordcount > currentWordCount) {
				throw new RuntimeException("There are operands that were not decoded");
			}
		}
	}

	private void printInstruction(SPIRVDecodedInstruction instruction) {
		int boundLength = (int) Math.log10(header.bound) + 1;
		int opStart = options.turnOffIndent ? 0 : 4 + boundLength;

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
		if (options.shouldHighlight) {
			toPrint = highlighter.highlight(op);
		}
		else {
			toPrint = op.operand;
		}
		output.print(toPrint);
	}

	private void processSpecialOps(SPIRVDecodedInstruction instruction) throws IOException, SPIRVUnsupportedExternalImport {
		boolean isFloating;
		if (instruction.operationName.equals("OpName")) {
			String name = instruction.operands.get(1).operand;
			name = "%" + name.substring(1, name.length() - 1);
			idToNameMap.put(instruction.operands.get(0).operand, name);
		}
		else if (instruction.operationName.equals("OpTypeInt")
				| (isFloating = instruction.operationName.equals("OpTypeFloat"))) {

			int widthInWords = Integer
					.parseInt(instruction.operands.get(0).operand) / 32;

			if (widthInWords <= 0) widthInWords = 1;

			boolean isSigned = true;
			if (instruction.operationName.equals("OpTypeInt")) {
				isSigned = Boolean.parseBoolean(instruction.operands.get(instruction.operands.size() - 1).operand);
			}

			idToTypeMap.put(instruction.result.operand,
					new SPIRVNumberFormat(widthInWords, isFloating, isSigned));
		}
		else if (instruction.operationName.equals("OpExtInstImport")) {
			externalImports.put(instruction.result.operand, SPIRVExternalImport.importExternal(instruction.operands.get(0).operand));
		}
	}

	private int decodeOperand(SPIRVDecodedInstruction instruction, SPIRVOperandKind operandKind) throws IOException, InvalidSPIRVEnumerantException, InvalidSPIRVOperandKindException, InvalidSPIRVOpcodeException {
		List<SPIRVDecodedOperand> operands = instruction.operands;
		int currentWordCount = 0;
		if (operandKind.getKind().equals("LiteralString")) {
			StringBuilder sb = new StringBuilder("\"");
			byte[] word;
			do {
				word = wordStream.getNextWordInBytes(); currentWordCount++;
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
			instruction.addOperand(result, SPIRVOperandCategory.LiteralString);
		}
		else if (operandKind.getKind().equals("LiteralContextDependentNumber")) {
			String lastID = operands.get(operands.size() - 1).operand;
			SPIRVNumberFormat format = idToTypeMap.get(lastID);
			currentWordCount += format.width;
			byte[] bytes = new byte[format.width * 4];
			for (int i = format.width - 1; i >= 0; i--) {
				System.arraycopy(
						wordStream.getNextWordInBytes(),
						0,
						bytes,
						i*4,
						4);
			}
			String number;
			if (format.isFloating){
				switch (format.width) {
					case 1: number = decodeFloat(bytes); break;
					case 2: number = decodeDouble(bytes); break;
					default:
						throw new RuntimeException("Floating point numbers are not supported with width: " + format.width * 4);
				}
			}
			else {
				number = new BigInteger(format.isSigned ? 0 : 1, bytes).toString();
			}
			instruction.addOperand(number, SPIRVOperandCategory.LiteralNumber);
		}
		else if (operandKind.getKind().equals("LiteralExtInstInteger")) {
			int externalOpCode = wordStream.getNextWord(); currentWordCount++;
			SPIRVExternalInstruction extInst = externalImports
												.get(instruction.getLastOperand().operand)
												.getInstruction(externalOpCode);

			instruction.addOperand(extInst.opName, SPIRVOperandCategory.Enum);
		}
		else if (operandKind.getCategory().equals("Id")) {
			String result = "%" + wordStream.getNextWord(); currentWordCount++;
			if (options.shouldInlineNames) {
				if (idToNameMap.containsKey(result)) {
					result = idToNameMap.get(result);
				}
			}
			SPIRVDecodedOperand id = new SPIRVDecodedOperand(
											result,
											SPIRVOperandCategory.ID);

			if (operandKind.getKind().equals("IdResult")) {
				instruction.setResult(id);
			} else {
				operands.add(id);
			}
		}
		else if (operandKind.getCategory().endsWith("Enum")) {
			SPIRVEnumerant[] values;
			if (operandKind.getCategory().startsWith("Value")) {
				values = new SPIRVEnumerant[1];
				values[0] = operandKind
						.getEnumerant(Integer.toString(wordStream.getNextWord()));
			}
			else {
				// For now it can only be a BitEnum
				int value = wordStream.getNextWord();
				List<SPIRVEnumerant> flags = new ArrayList<>(1);
				for (SPIRVEnumerant enumerant : operandKind.getEnumerants()) {
				    int mask = Integer.decode(enumerant.getValue());
				    if ((mask & value) > 0 || mask == value) flags.add(enumerant);
				}
				//value = String.format("0x%04x", wordStream.getNextWord());
				values = flags.toArray(new SPIRVEnumerant[0]);
			}
			currentWordCount++;
			String operand = Arrays.stream(values).map(spirvEnumerant -> spirvEnumerant.name).collect(Collectors.joining("|"));

			instruction.addOperand(operand, SPIRVOperandCategory.Enum);
			for (SPIRVEnumerant enumerant : values) {
				if (enumerant.getParameters() != null) {
					for (int j = 0; j < enumerant.getParameters().length; j++) {
						SPIRVOperandKind paramKind = grammar.getOperandKind(
								enumerant.getParameters()[j].getKind());

						currentWordCount += decodeOperand(instruction, paramKind);
					}
				}
			}
		}
		else if (operandKind.getCategory().equals("Composite")) {
			String[] bases = operandKind.getBases();
			if (!options.turnOffGrouping) {
				instruction.addOperand("{", SPIRVOperandCategory.Token);
			}
			for (String base : bases) {
				SPIRVOperandKind member = new SPIRVOperandKind();
				member.kind = base;
				if (base.startsWith("Literal")) member.category = "Literal";
				else if (base.startsWith("Id")) member.category = "Id";

				currentWordCount += decodeOperand(instruction, member);
			}
			if (!options.turnOffGrouping) {
				instruction.addOperand("}", SPIRVOperandCategory.Token);
			}
		}
		else {
			// By now it can only be a Literal(Integer)
			String result = Integer.toString(wordStream.getNextWord());
			currentWordCount++;
			instruction.addOperand(result, SPIRVOperandCategory.LiteralNumber);
		}
		return currentWordCount;
	}

	private String decodeFloat(byte[] word) {
		float value = Float.intBitsToFloat(ByteBuffer.wrap(word).getInt());
		return (value == (int) value) ? Integer.toString((int)value) : Float.toString(value);
	}

	private String decodeDouble(byte[] words) {
		double value = Double.longBitsToDouble(ByteBuffer.wrap(words).getLong());
		return (value == (long) value) ? Long.toString((long)value) : Double.toString(value);
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