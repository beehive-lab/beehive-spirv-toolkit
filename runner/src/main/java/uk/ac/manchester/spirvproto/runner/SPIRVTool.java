package uk.ac.manchester.spirvproto.runner;

import org.apache.commons.cli.*;
import uk.ac.manchester.spirvproto.lib.Disassembler;
import uk.ac.manchester.spirvproto.lib.InvalidBinarySPIRVInputException;
import uk.ac.manchester.spirvproto.lib.SPVFileReader;

import java.io.File;
import java.io.FileNotFoundException;

public class SPIRVTool {

	public static Configuration state;

	public static void main(String[] args) {
		state = getArguments(args);

		SPVFileReader wordStream = null;
		try {
			wordStream = new SPVFileReader(state.fileName);
		} catch (FileNotFoundException e) {
			if (state.debug) {
			    e.printStackTrace();
            }
			else {
			    System.err.println("Could not find file: " + state.fileName);
            }
			System.exit(1);
		}
		Disassembler disasm = null;
		try {
			disasm = new Disassembler(wordStream, System.out);
		} catch (InvalidBinarySPIRVInputException e) {
			if (state.debug) {
				e.printStackTrace();
			}
			else {
				System.err.println("File " + new File(state.fileName).getName() + " is not a valid SPIR-V binary module");
			}
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(disasm);
		System.out.println(disasm.getHeader());
		disasm.disassemble();
	}

	private static Configuration getArguments(String[] args) {
		Options options = new Options();
		options.addOption("h", "Prints this message");
		options.addOption("d", false, "Print debug information");

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			handleError(options);
		}

		assert cmd != null;
		if (cmd.getArgs().length != 1 || cmd.hasOption('h')) {
			handleError(options);
		}

		return new Configuration(cmd.hasOption('d'), cmd.getArgs()[0]);
	}

	private static void handleError(Options options) {
		new HelpFormatter().printHelp("spirv-tools [OPTIONS] <filename>", options);
		System.exit(1);
	}
}