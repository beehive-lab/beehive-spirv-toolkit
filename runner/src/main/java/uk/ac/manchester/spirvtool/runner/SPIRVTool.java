package uk.ac.manchester.spirvtool.runner;

import uk.ac.manchester.spirvtool.lib.Disassembler;

import java.io.FileNotFoundException;

public class SPIRVTool {
	public static void main(String[] args) {

		if (args.length != 1) {
			//TODO: print usage
			System.err.println("Wrong number of arguments!");
			System.exit(1);
		}

		SPVFileReader wordStream = null;
		try {
			wordStream = new SPVFileReader(args[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Disassembler disasm = new Disassembler(wordStream);

		System.out.println(disasm);
	}
}