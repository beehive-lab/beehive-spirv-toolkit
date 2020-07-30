package uk.ac.manchester.spirvproto.generator;

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

    public Generator(File path) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setClassForTemplateLoading(Generator.class, "/resources/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        config = cfg;

        grammar = SPIRVSpecification.buildSPIRVGrammar(1, 0);

        instructionsDir = path;
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
        Template literalOperand = config.getTemplate("operand-literal.ftl");
        Writer out;
        for (SPIRVOperandKind operandKind : grammar.operandKinds) {
            if (operandKind.kind.equals("LiteralInteger") || operandKind.kind.equals("LiteralString")) continue;

            // Clean up parameter names
            if (operandKind.enumerants != null) {
                for (SPIRVEnumerant enumerant : operandKind.enumerants) {
                    if (enumerant.parameters != null) {
                        for (int i = 0; i < enumerant.parameters.length; i++) {
                            SPIRVOperandParameter param = enumerant.parameters[i];
                            if (param.name == null || param.name.isEmpty()) {
                                param.name = "parameter" + i;
                            }
                            else {
                                param.name = uncapFirst(sanitize(param.name));
                            }
                        }
                    }
                }
            }

            out = createWriter(operandKind.kind, operandsDir);

            //Template templateToUse = operandKind.category.equals("Id") ? idOperand : enumOperand;
            Template templateToUse = enumOperand;
            if (operandKind.category.equals("Id")) templateToUse = idOperand;
            if (operandKind.category.equals("Composite")) templateToUse = compositeOperand;
            if (operandKind.category.equals("Literal")) templateToUse = literalOperand;

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
                        operand.name = uncapFirst(sanitize(operand.name));
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

    private String sanitize(String value) {
        String[] words = value.split("[- '~.,+<>\n]+");
        StringBuilder sb = new StringBuilder(value.length());
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(capFirst(word));
            }
        }

        return sb.toString();
    }

    private String uncapFirst(String value) {
        return value.substring(0, 1).toLowerCase() + value.substring(1);
    }

    private String capFirst(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    @Override
    public void run() throws Exception {
        generate();
    }
}
