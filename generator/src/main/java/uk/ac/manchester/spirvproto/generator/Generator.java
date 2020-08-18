package uk.ac.manchester.spirvproto.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import uk.ac.manchester.spirvproto.generator.grammar.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Generator {
    private final Configuration config;
    private final SPIRVGrammar grammar;
    private final SPIRVExternalImport openclImport;
    private final File operandsDir;
    private final File instructionsDir;
    private final File asmMapperDir;
    private final File disMapperDir;
    private final SPIRVInstructionSuperClassMapping superClasses;
    private final Set<String> ignoredOperandKinds;

    public Generator(File path) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setClassForTemplateLoading(Generator.class, "/resources/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        config = cfg;

        grammar = SPIRVSpecification.buildSPIRVGrammar(1, 2);
        openclImport = SPIRVExternalImport.importExternal("opencl.std");

        ensureDirExists(path);

        instructionsDir = new File(path, "instructions");
        ensureDirExists(instructionsDir);

        operandsDir = new File(instructionsDir, "operands");
        ensureDirExists(operandsDir);

        asmMapperDir = new File(path, "assembler");
        ensureDirExists(asmMapperDir);

        disMapperDir = new File(path, "disassembler");
        ensureDirExists(disMapperDir);

        superClasses = new SPIRVInstructionSuperClassMapping();

        ignoredOperandKinds = new HashSet<>();
        ignoredOperandKinds.add("LiteralInteger");
        ignoredOperandKinds.add("LiteralString");
        ignoredOperandKinds.add("LiteralContextDependentNumber");
        ignoredOperandKinds.add("LiteralExtInstInteger");
        ignoredOperandKinds.add("Id");
    }

    private void ensureDirExists(File directory) throws IOException {
        if (!directory.exists()) {
            if (!directory.mkdir()) throw new IOException("Could not create directory: " + directory);
        }
        else if (!directory.isDirectory()) throw new IOException(directory + " is not a directory");
    }

    public void generate() throws Exception {
        cleanUpOperands();
        cleanUpInstructions(grammar.getInstructions());

        generateOperandClasses();
        generateInstructionClasses();
        generateAsmInstructionMapper();
        generateAsmOperandMapper();
        generateDisInstructionMapper();
        generateDisOperandMapper();
        generateAsmExtInstMapper();
    }

    private void generateDisOperandMapper() throws Exception {
        Template mapperTemplate = config.getTemplate("dis-operand-mapper.ftl");
        Writer out = createWriter("OperandMapper", disMapperDir);
        mapperTemplate.process(grammar, out);
        out.flush();
        out.close();
    }

    private void generateDisInstructionMapper() throws Exception {
        Template mapperTemplate = config.getTemplate("dis-instruction-mapper.ftl");
        Writer out = createWriter("InstMapper", disMapperDir);
        mapperTemplate.process(grammar, out);
        out.flush();
        out.close();

    }

    private void generateAsmExtInstMapper() throws Exception {
        Template mapperTemplate = config.getTemplate("asm-extinst-mapper.ftl");
        Writer out = createWriter("ExtInstMapper", asmMapperDir);
        mapperTemplate.process(openclImport, out);
        out.flush();
        out.close();
    }

    private void generateAsmOperandMapper() throws Exception {
        Template mapperTemplate = config.getTemplate("asm-operand-mapper.ftl");
        Writer out = createWriter("OperandMapper", asmMapperDir);
        mapperTemplate.process(grammar, out);
        out.flush();
        out.close();
    }

    private void generateAsmInstructionMapper() throws Exception {
        Template mapperTemplate = config.getTemplate("asm-instruction-mapper.ftl");
        Writer out = createWriter("InstMapper", asmMapperDir);
        mapperTemplate.process(grammar, out);
        out.flush();
        out.close();
    }

    private void generateInstructionClasses() throws Exception {
        Writer out;
        Template instructionTemplate = config.getTemplate("instruction.ftl");
        for (SPIRVInstruction instruction : grammar.getInstructions()) {
            out = createWriter(instruction.name, instructionsDir);

            instructionTemplate.process(instruction, out);

            out.flush();
            out.close();
        }
    }

    private void generateOperandClasses() throws Exception {
        Writer out;
        Template enumOperand = config.getTemplate("operand-enum.ftl");
        Template compositeOperand = config.getTemplate("operand-composite.ftl");
        Template literalOperand = config.getTemplate("operand-literal.ftl");

        for (SPIRVOperandKind operandKind : grammar.getOperandKinds()) {

            out = createWriter(operandKind.kind, operandsDir);

            Template templateToUse = enumOperand;
            if (operandKind.category.equals("Composite")) templateToUse = compositeOperand;
            else if (operandKind.category.equals("Literal")) templateToUse = literalOperand;

            templateToUse.process(operandKind, out);
            out.flush();
            out.close();
        }
    }

    private void cleanUpInstructions(SPIRVInstruction[] instructions) {
        for (SPIRVInstruction instruction : instructions) {
            if (instruction.operands != null) {
                Map<String, MutableInt> nameCount = new HashMap<>();
                for (SPIRVOperand operand : instruction.operands) {
                    if (operand.kind.equals("IdResultType")) instruction.hasReturnType = true;
                    else if (operand.kind.equals("IdResult")) instruction.hasResult = true;
                    if (operand.name == null) {
                        operand.name = uncapFirst(operand.kind);
                        if (!nameCount.containsKey(operand.name)) {
                            nameCount.put(operand.name, new MutableInt(0));
                        } else {
                            MutableInt count = nameCount.get(operand.name);
                            int index = count.increment() + 1;
                            operand.name += Integer.toString(index);
                        }
                    } else {
                        operand.name = uncapFirst(sanitize(operand.name));
                    }
                    operand.name = "_" + operand.name;
                    if (operand.kind.startsWith("Id")) operand.kind = "Id";
                }
            }
            String superClass;
            if ((superClass = superClasses.get(instruction.name)) == null) {
                superClass = "SPIRVInstruction";
            }
            instruction.superClass = superClass;
        }
    }

    private void cleanUpOperands() {
        for (SPIRVOperandKind kind : grammar.operandKinds) {
            if (kind.bases != null) {
                for (int i = 0; i < kind.bases.length; i++) {
                    if (kind.bases[i].startsWith("Id")) kind.bases[i] = "Id";
                }
            }
            else if (kind.enumerants != null) {
                for (SPIRVEnumerant enumerant : kind.enumerants) {
                    if (enumerant.parameters != null) {
                        for (int i = 0; i < enumerant.parameters.length; i++) {
                            SPIRVOperandParameter param = enumerant.parameters[i];
                            if (param.name == null || param.name.isEmpty()) {
                                param.name = "parameter" + i;
                            }
                            else {
                                param.name = uncapFirst(sanitize(param.name));
                            }

                            if (param.kind.startsWith("Id")) param.kind = "Id";
                        }
                    }
                }
            }
            else if (kind.kind.startsWith("Id")) kind.kind = "Id";
        }

        grammar.operandKinds = Arrays
                .stream(grammar.operandKinds)
                .filter(o -> !ignoredOperandKinds.contains(o.kind))
                .toArray(SPIRVOperandKind[]::new);
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
}
