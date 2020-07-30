package uk.ac.manchester.spirvproto.lib.disassembler;

public class SPIRVDisassemblerOptions {
    public final boolean shouldHighlight;
    public final boolean shouldInlineNames;
    public final boolean turnOffIndent;
    public final boolean turnOffGrouping;
    public final boolean noHeader;

    public SPIRVDisassemblerOptions(boolean shouldHighlight,
                                    boolean shouldInlineNames,
                                    boolean turnOffIndent,
                                    boolean turnOffGrouping,
                                    boolean noHeader) {
        this.shouldHighlight = shouldHighlight;
        this.shouldInlineNames = shouldInlineNames;
        this.turnOffIndent = turnOffIndent;
        this.turnOffGrouping = turnOffGrouping;
        this.noHeader = noHeader;
    }
}
