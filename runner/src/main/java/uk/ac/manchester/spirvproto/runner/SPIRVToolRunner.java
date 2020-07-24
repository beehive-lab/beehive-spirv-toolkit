package uk.ac.manchester.spirvproto.runner;

import org.apache.commons.cli.*;
import uk.ac.manchester.spirvproto.generator.Generator;
import uk.ac.manchester.spirvproto.lib.Disassembler;
import uk.ac.manchester.spirvproto.lib.SPIRVTool;
import uk.ac.manchester.spirvproto.lib.SPVFileReader;

import java.io.PrintStream;

public class SPIRVToolRunner {

	public static Configuration state;

	public static void main(String[] args) {
		try {
			state = getArguments(args);
			state.tool.run();
		}
		catch (Exception e) {
			if (state == null || state.debug) {
				//System.err.println(e.getMessage());
				e.printStackTrace(System.err);
			}
			else {
				System.err.println("An error occurred, please run with the -d|--debug flag to see the exact error");
			}
			System.exit(1);
		}
	}

	private static Configuration getArguments(String[] args) throws Exception {
		Options options = new Options();
		options.addOption("h", "help", false,"Prints this message");
		options.addOption("d", "debug", false, "Print debug information");
		options.addOption("n", "name", false, "Inline names of nodes where possible");

		options.addOption("o", "out", true, "Specify an output file");
		options.addOption("t", "tool", true, "Select tool: gen | dis[default]");

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
		String inputFile = cmd.getArgs()[0];

		boolean debug = cmd.hasOption('d');
		boolean inlineNames = cmd.hasOption('n');

		PrintStream output;
		if (!cmd.hasOption('o')) {
			output = System.out;
		}
		else {
			output = new PrintStream(cmd.getOptionValue('o'));
		}

		String tool = cmd.getOptionValue('t', "dis");
		SPIRVTool spirvTool;
		if (tool.equals("gen")) {
			spirvTool = new Generator(inputFile);
		}
		else {
			SPVFileReader reader = new SPVFileReader(inputFile);
			spirvTool = new Disassembler(reader, output, output != null && output.equals(System.out), inlineNames);
		}

		return new Configuration(debug, spirvTool);
	}

	private static void handleError(Options options) {
		new HelpFormatter().printHelp("spirv-proto [OPTIONS] <filename>", options);
		System.exit(1);
	}
}