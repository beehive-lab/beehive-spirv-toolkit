package uk.ac.manchester.spirvproto.lib.disassembler;

public class CLIHighlighter implements SPIRVSyntaxHighlighter {
    String IDFormat = "\033[1;33m%s\033[0m";
    String stringFormat = "\033[0;32m%s\033[0m";
    String intFormat = "\033[0;34m%s\033[0m";

    public CLIHighlighter() {}

    private String highlightID(String ID) {
        return String.format(IDFormat, ID);
    }

    private String highlightString(String string) {
        return String.format(stringFormat, string);
    }

    private String highlightInteger(String integer) {
        return String.format(intFormat, integer);
    }

    @Override
    public String highlight(SPIRVDecodedOperand op) {
        switch (op.category) {
            case Result:
            case ID: return highlightID(op.operand);

            case LiteralString: return highlightString(op.operand);
            case LiteralInteger: return highlightInteger(op.operand);
            case Enum:
            default: return op.operand;
        }
    }
}
