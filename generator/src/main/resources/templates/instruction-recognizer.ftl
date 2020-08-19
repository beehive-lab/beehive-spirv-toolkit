package uk.ac.manchester.spirvproto.lib.assembler;

import java.util.HashSet;
import java.util.Set;

public class SPIRVInstRecognizer {
    private final Set<String> instructions;

    public SPIRVInstRecognizer() {
        instructions = new HashSet<>(${instructions?size?string.computer});
        <#list instructions as instruction>
        instructions.add("${instruction.name}");
        </#list>
    }

    public boolean isInstruction(String token) {
        return instructions.contains(token);
    }
}