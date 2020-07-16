package uk.ac.manchester.spirvproto.runner;

import org.apache.commons.cli.*;
import uk.ac.manchester.spirvproto.lib.Disassembler;
import uk.ac.manchester.spirvproto.lib.InvalidBinarySPIRVInputException;
import uk.ac.manchester.spirvproto.lib.SPVFileReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class SPIRVTool {

	public static Configuration state;

	public static void main(String[] args) {
		state = getArguments(args);

		try {
			SPVFileReader wordStream = new SPVFileReader(state.inputFile);
			Disassembler disasm = new Disassembler(wordStream, state.output, state.output.equals(System.out));
			disasm.disassemble();
		} catch (Exception e) {
			if (state.debug) {
				e.printStackTrace();
			}
			else if (e instanceof FileNotFoundException) {
				System.err.println("Could not find file: " + state.inputFile);
			}
			else if (e instanceof InvalidBinarySPIRVInputException) {
				System.err.println("File " + state.inputFile.getName() + " is not a valid SPIR-V binary module");
			}
			else if (e instanceof IOException) {
				System.err.println("Error reading file: " + state.inputFile.getName());
			}
			System.exit(1);
		}
	}

	private static Configuration getArguments(String[] args) {
		Options options = new Options();
		options.addOption("h", "Prints this message");
		options.addOption("d", false, "Print debug information");
		options.addOption("o", true, "Specify an output file");

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

		PrintStream output = null;
		if (!cmd.hasOption('o')) {
			output = System.out;
		}
		else {
			try {
				output = new PrintStream(cmd.getOptionValue('o'));
			} catch (FileNotFoundException e) {
				System.err.println("Could not find: " + cmd.getOptionValue('o'));
				handleError(options);
			}
		}

		return new Configuration(cmd.hasOption('d'), cmd.getArgs()[0], output);
	}

	private static void handleError(Options options) {
		new HelpFormatter().printHelp("spirv-dis [OPTIONS] <filename>", options);
		System.exit(1);
	}
}