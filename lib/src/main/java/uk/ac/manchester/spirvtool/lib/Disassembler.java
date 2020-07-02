package uk.ac.manchester.spirvtool.lib;

public class Disassembler {
	private BinaryWordStream wordStream;

	public Disassembler(BinaryWordStream wordStream) {
		this.wordStream = wordStream;
	}

	@Override
	public String toString() {
		return "SPIR-V Disassembler";
	}
}