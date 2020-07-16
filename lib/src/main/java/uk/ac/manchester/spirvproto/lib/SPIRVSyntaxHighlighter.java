package uk.ac.manchester.spirvproto.lib;

interface SPIRVSyntaxHighlighter {
    String highlightID(String ID);
    String highlightString(String string);
    String highlightInteger(String integer);
}
