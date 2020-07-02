package uk.ac.manchester.spirvtool.lib;

public class Disassembler {
	private String filename;

	public Disassembler(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return "Disassembler - " + filename;
	}
}