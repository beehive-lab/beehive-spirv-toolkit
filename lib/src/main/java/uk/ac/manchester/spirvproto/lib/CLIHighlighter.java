package uk.ac.manchester.spirvproto.lib;

public class CLIHighlighter implements SPIRVSyntaxHighlighter {
    String IDFormat = "\033[1;33m%s\033[0m";
    String stringFormat = "\033[0;32m%s\033[0m";
    String intFormat = "\033[0;34m%s\033[0m";

    public CLIHighlighter() {}

    @Override
    public String highlightID(String ID) {
        return String.format(IDFormat, ID);
    }

    @Override
    public String highlightString(String string) {
        return String.format(stringFormat, string);
    }

    @Override
    public String highlightInteger(String integer) {
        return String.format(intFormat, integer);
    }
}
