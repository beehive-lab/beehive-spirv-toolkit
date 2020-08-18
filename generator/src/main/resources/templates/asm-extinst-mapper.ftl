package uk.ac.manchester.spirvproto.lib.assembler;

import java.util.HashMap;

class SPIRVExtInstMapper {

    private static HashMap<String, Integer> extInstNameMap;

    public static int get(String name) {
        return extInstNameMap.get(name);
    }

    public static void loadOpenCL() {
        extInstNameMap = new HashMap<>(${instructions?size?string.computer});
        <#list instructions as instruction>
        extInstNameMap.put("${instruction.opName}", ${instruction.opCode?string.computer});
        </#list>
    }
}