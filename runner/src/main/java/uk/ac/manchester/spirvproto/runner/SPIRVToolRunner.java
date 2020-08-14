package uk.ac.manchester.spirvproto.runner;

import org.apache.commons.cli.*;
import uk.ac.manchester.spirvproto.lib.SPIRVTool;
import uk.ac.manchester.spirvproto.lib.assembler.Assembler;
import uk.ac.manchester.spirvproto.lib.assembler.InvalidSPIRVModuleException;
import uk.ac.manchester.spirvproto.lib.disassembler.Disassembler;
import uk.ac.manchester.spirvproto.lib.disassembler.SPIRVDisassemblerOptions;
import uk.ac.manchester.spirvproto.lib.disassembler.SPVFileReader;

import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

public class SPIRVToolRunner {

	public static Configuration state;

	public static void main(String[] args) {
		try {
			state = getArguments(args);
			state.tool.run();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			if (state == null || state.debug) {
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
		options.addOption("n", "inline-names", false, "Inline names of nodes where possible");
		options.addOption("i", "no-indent", false, "Turn off indentation");
		options.addOption("g", "no-grouping", false, "Do not group composites together");
		options.addOption("e", "no-header", false, "Do not print the header");
		options.addOption("c", "no-color", false, "Do not use coloured output");

		options.addOption("o", "out", true, "Specify an output file/directory");
		options.addOption("t", "tool", true, "Specify the tool to be used: Disassembler: dis [default] | Assembler asm");

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
		File inputFile = new File(cmd.getArgs()[0]);

		String tool = cmd.getOptionValue('t', "dis");
		SPIRVTool spirvTool = null;
		if (tool.equals("dis")) {
			PrintStream output;
			if (!cmd.hasOption('o')) {
				output = System.out;
			}
			else {
				File outputFile = new File(cmd.getOptionValue('o'));
				if (outputFile.isDirectory()) {
					outputFile = new File(outputFile, inputFile.getName() + ".dis");
				}
				output = new PrintStream(outputFile);
			}
			SPVFileReader reader = new SPVFileReader(inputFile);
			SPIRVDisassemblerOptions disassemblerOptions = new SPIRVDisassemblerOptions(
					(output != null && output.equals(System.out)) && (!cmd.hasOption('c')),
					cmd.hasOption('n'),
					cmd.hasOption('i'),
					cmd.hasOption('g'),
					cmd.hasOption('e')
			);
			spirvTool = new Disassembler(reader, output, disassemblerOptions);
		}
		else if (tool.equals("asm")) {
			File output;
			if (!cmd.hasOption('o')) {
				String inputFileName = inputFile.getAbsolutePath();
				int indexOfExtension = inputFileName.lastIndexOf(".");
				output = new File(inputFileName.substring(0, indexOfExtension) + ".spv");
			}
			else {
				output = new File(cmd.getOptionValue('o'));
			}
			spirvTool = new Assembler(new FileReader(inputFile), output);
		}
		else {
			System.err.println("Unrecognized tool: " + tool);
			handleError(options);
		}

		return new Configuration(cmd.hasOption('d'), spirvTool);
	}

	private static void handleError(Options options) {
		new HelpFormatter().printHelp("spirv-proto [OPTIONS] <filepath>", options);
		System.exit(1);
	}
}