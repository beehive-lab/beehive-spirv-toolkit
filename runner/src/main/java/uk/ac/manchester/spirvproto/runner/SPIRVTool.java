package uk.ac.manchester.spirvproto.runner;

import org.apache.commons.cli.*;
import uk.ac.manchester.spirvproto.lib.Disassembler;
import uk.ac.manchester.spirvproto.lib.InvalidBinarySPIRVInputException;
import uk.ac.manchester.spirvproto.lib.SPVFileReader;

import java.io.*;

public class SPIRVTool {

	public static Configuration state;

	public static void main(String[] args) {
		state = getArguments(args);

		SPVFileReader wordStream = null;
		try {
			wordStream = new SPVFileReader(state.inputFile);
		} catch (FileNotFoundException e) {
			if (state.debug) {
			    e.printStackTrace();
            }
			else {
			    System.err.println("Could not find file: " + state.inputFile);
            }
			System.exit(1);
		}
		Disassembler disasm = null;
		try {
			disasm = new Disassembler(wordStream, state.output);
		} catch (InvalidBinarySPIRVInputException e) {
			if (state.debug) {
				e.printStackTrace();
			}
			else {
				System.err.println("File " + state.inputFile.getName() + " is not a valid SPIR-V binary module");
			}
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			disasm.disassemble();
		} catch (IOException e) {
			if (state.debug) {
				e.printStackTrace();
			}
			else {
				System.err.println("Error reading file: " + state.inputFile.getName());
			}
		}
	}

	private static Configuration getArguments(String[] args) {
		Options options = new Options();
		options.addOption("h", "Prints this message");
		options.addOption("d", false, "Print debug information");
		options.addOption("o", true, "Specify and output file");

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
		new HelpFormatter().printHelp("spirv-tools [OPTIONS] <filename>", options);
		System.exit(1);
	}
}