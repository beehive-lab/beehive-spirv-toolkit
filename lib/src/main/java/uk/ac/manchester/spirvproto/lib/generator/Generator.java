package uk.ac.manchester.spirvproto.lib.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import uk.ac.manchester.spirvproto.lib.SPIRVTool;
import uk.ac.manchester.spirvproto.lib.grammar.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Generator implements SPIRVTool {
    private final Configuration config;
    private final SPIRVGrammar grammar;
    private final File operandsDir;
    private final File instructionsDir;

    public Generator(String path) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setClassForTemplateLoading(Generator.class, "/resources/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);

        config = cfg;

        grammar = SPIRVSpecification.buildSPIRVGrammar(1, 0);

        Path instructionsDirPath = Paths.get(path);
        if (!instructionsDirPath.isAbsolute()) instructionsDirPath = Paths.get(System.getProperty("user.dir"), path);
        instructionsDir = instructionsDirPath.toFile();
        if (!instructionsDir.exists()) {
            if (!instructionsDir.mkdir()) throw new Exception("Could not create " + instructionsDir);
        }
        else if (!instructionsDir.isDirectory()) throw new Exception(path + " is not a directory");

        operandsDir = new File(instructionsDir, "operands");
        if (!operandsDir.exists()) {
            if (!operandsDir.mkdir()) throw new Exception("Could not create: " + operandsDir);
        }
    }

    public void generate() throws Exception {
        Template enumOperand = config.getTemplate("operand-enum.ftl");
        Template idOperand = config.getTemplate("operand-id.ftl");
        Template compositeOperand = config.getTemplate("operand-composite.ftl");
        Writer out;
        for (SPIRVOperandKind operandKind : grammar.operandKinds) {
            if (operandKind.category.equals("Literal")) continue;

            out = createWriter(operandKind.kind, operandsDir);

            //Template templateToUse = operandKind.category.equals("Id") ? idOperand : enumOperand;
            Template templateToUse = enumOperand;
            if (operandKind.category.equals("Id")) templateToUse = idOperand;
            if (operandKind.category.equals("Composite")) templateToUse = compositeOperand;

            templateToUse.process(operandKind, out);
            out.flush();
            out.close();
        }

        Template instructionTemplate = config.getTemplate("instruction.ftl");
        for (SPIRVInstruction instruction : grammar.instructions) {
            out = createWriter(instruction.name, instructionsDir);

            // Clean up operands
            if (instruction.operands != null) {
                for (SPIRVOperand operand : instruction.operands) {
                    if (operand.name == null) {
                        operand.name = uncapFirst(operand.kind);
                    } else {
                        operand.name = uncapFirst(operand.name.replaceAll("[ '~.,+\n]", ""));
                    }
                }
            }
            instructionTemplate.process(instruction, out);

            out.flush();
            out.close();
        }
    }

    private FileWriter createWriter(String name, File directory) throws Exception {
        String filename = "SPIRV" + name + ".java";
        File newClass = new File(directory, filename);
        if (!newClass.exists() && !newClass.createNewFile()) throw new Exception("Could not create file: " + newClass);
        return new FileWriter(newClass);
    }

    private String uncapFirst(String value) {
        return value.substring(0, 1).toLowerCase() + value.substring(1);
    }

    @Override
    public void run() throws Exception {
        generate();
    }
}
