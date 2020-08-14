package uk.ac.manchester.spirvproto.lib.disassembler;

public interface SPIRVSyntaxHighlighter {
    String highlight(SPIRVDecodedOperand op);

    String highlightId(String id);
    String highlightString(String string);
    String highlightInt(String value);
    String highlightComment(String comment);
}
