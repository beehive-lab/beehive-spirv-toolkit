package uk.ac.manchester.spirvtool.runner;

import uk.ac.manchester.spirvtool.lib.Disassembler;

public class SPIRVTool {
	public static void main(String[] args) {

		if (args.length != 1) {
			//TODO: print usage
			System.err.println("Wrong number of arguments!");
			System.exit(1);
		}

		Disassembler disasm = new Disassembler(args[0]);

		System.out.println(disasm);
	}
}