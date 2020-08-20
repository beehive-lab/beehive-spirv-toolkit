package uk.ac.manchester.spirvproto.lib.assembler;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;

@Generated("beehive-lab.spirv-proto.generator")
class SPIRVInstRecognizer {
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