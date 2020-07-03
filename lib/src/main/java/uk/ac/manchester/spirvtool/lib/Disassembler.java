package uk.ac.manchester.spirvtool.lib;

public class Disassembler {
	private final BinaryWordStream wordStream;
	private final SPIRVHeader header;

	public Disassembler(BinaryWordStream wordStream) throws InvalidBinarySPIRVInputException {
		this.wordStream = wordStream;

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