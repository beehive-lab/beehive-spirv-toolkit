package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.grammar.SPIRVGrammar;

class SPIRVToken {
    String value;
    SPIRVTokenType type;

    public SPIRVToken(String token, SPIRVGrammar grammar) {
        value = token.replace("\\s", "");

        if (value.equals("=")) type = SPIRVTokenType.ASSIGN;
        else if (value.startsWith(";")) type = SPIRVTokenType.COMMENT;
        else if (value.startsWith("%")) type = SPIRVTokenType.ID;
        else if (grammar.isInstruction(token)) type = SPIRVTokenType.INSTRUCTION;
        else type = SPIRVTokenType.IMMEDIATE;
    }

    @Override
    public String toString() {
        return "(" + value + " " + type + ")";
    }

    public boolean isOperand() {
        return type == SPIRVTokenType.ID || type == SPIRVTokenType.IMMEDIATE;
    }
}
