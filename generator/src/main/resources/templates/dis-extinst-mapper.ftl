package uk.ac.manchester.spirvproto.lib.disassembler;

import java.util.HashMap;

public class SPIRVExtInstMapper {
    private static HashMap<Integer, String> extInstNameMap;

    public static String get(int opCode) {
        return extInstNameMap.get(opCode);
    }

    public static void loadOpenCL() {
        extInstNameMap = new HashMap<>(${instructions?size?string.computer});
        <#list instructions as instruction>
        extInstNameMap.put(${instruction.opCode?string.computer}, "${instruction.opName}");
        </#list>
    }
}