package uk.ac.manchester.spirvtool.runner;

import org.apache.commons.cli.*;
import uk.ac.manchester.spirvtool.lib.Disassembler;

import java.io.FileNotFoundException;

public class SPIRVTool {
	public static void main(String[] args) {
		Configuration config = getArguments(args);

		SPVFileReader wordStream = null;
		try {
			wordStream = new SPVFileReader(config.fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Disassembler disasm = new Disassembler(wordStream);

		System.out.println(disasm);
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

		if (cmd == null || cmd.getArgs().length != 1 || cmd.hasOption('h')) {
			handleError(options);
		}

		return new Configuration(cmd.hasOption('d'), cmd.getArgs()[0]);
	}

	private static void handleError(Options options) {
		new HelpFormatter().printHelp("spirv-tools [OPTIONS] <filename>", options);
		System.exit(1);
	}
}