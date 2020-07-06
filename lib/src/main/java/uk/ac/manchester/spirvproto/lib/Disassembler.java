package uk.ac.manchester.spirvproto.lib;

import uk.ac.manchester.spirvproto.lib.grammar.SPIRVGrammar;
import uk.ac.manchester.spirvproto.lib.grammar.SPIRVInstruction;
import uk.ac.manchester.spirvproto.lib.grammar.SPIRVSpecification;

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

	public void disassemble() {
		output.println("Instructions: " + grammar.instructions.length);
		for (int i = 0; i <grammar.instructions.length; i++) {
			SPIRVInstruction current = grammar.instructions[i];
			output.println(current.name + ": " + current.opCode);
		}
	}

	@Override
	public String toString() {
		return "SPIR-V Disassembler";
	}
}