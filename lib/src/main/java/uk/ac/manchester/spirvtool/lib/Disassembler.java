package uk.ac.manchester.spirvtool.lib;

import uk.ac.manchester.spirvtool.lib.grammar.SPIRVGrammar;
import uk.ac.manchester.spirvtool.lib.grammar.SPIRVSpecification;

import java.io.IOException;

public class Disassembler {
	private final BinaryWordStream wordStream;
	private final SPIRVHeader header;

	public Disassembler(BinaryWordStream wordStream) throws InvalidBinarySPIRVInputException, IOException {
		this.wordStream = wordStream;

		SPIRVGrammar grammar = SPIRVSpecification.buildSPIRVGrammar();
		System.out.println("grammar: " + grammar.magicNumber);

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

	@Override
	public String toString() {
		return "SPIR-V Disassembler";
	}
}