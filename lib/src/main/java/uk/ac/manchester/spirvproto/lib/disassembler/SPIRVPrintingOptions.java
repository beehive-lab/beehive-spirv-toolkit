package uk.ac.manchester.spirvproto.lib.disassembler;

public class SPIRVPrintingOptions {
    public final SPIRVSyntaxHighlighter highlighter;
    public final int indent;
    public final boolean shouldInlineNames;
    public final boolean turnOffGrouping;

    public SPIRVPrintingOptions(SPIRVSyntaxHighlighter highlighter, int indent, boolean shouldInlineNames, boolean turnOffGrouping) {
        this.highlighter = highlighter;
        this.indent = indent;
        this.shouldInlineNames = shouldInlineNames;
        this.turnOffGrouping = turnOffGrouping;
    }
}
