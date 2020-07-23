package uk.ac.manchester.spirvproto.lib.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import uk.ac.manchester.spirvproto.lib.SPIRVTool;
import uk.ac.manchester.spirvproto.lib.grammar.SPIRVGrammar;
import uk.ac.manchester.spirvproto.lib.grammar.SPIRVOperandKind;
import uk.ac.manchester.spirvproto.lib.grammar.SPIRVSpecification;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

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

        instructionsDir = new File(System.getProperty("user.dir"), path);
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
        Template enumOperand = config.getTemplate("operand-enum.ftlh");
        Template idOperand = config.getTemplate("operand-id.ftlh");
        for (SPIRVOperandKind operandKind : grammar.operandKinds) {
            if (operandKind.category.equals("Literal") || operandKind.category.equals("Composite")) continue;

            String filename = "SPIRV" + operandKind.kind + ".java";
            File newClass = new File(operandsDir, filename);
            if (!newClass.exists() && !newClass.createNewFile()) throw new Exception("Could not create file: " + newClass);
            Writer out = new FileWriter(newClass);

            Template templateToUse = operandKind.category.equals("Id") ? idOperand : enumOperand;

            templateToUse.process(operandKind, out);
            out.flush();
            out.close();
        }
    }

    @Override
    public void run() throws Exception {
        generate();
    }
}
