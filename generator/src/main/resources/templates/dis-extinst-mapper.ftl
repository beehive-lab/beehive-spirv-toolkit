package uk.ac.manchester.spirvbeehivetoolkit.lib.disassembler;

import java.util.HashMap;
import javax.annotation.Generated;

/**
 * SPIR-V handles built in functions by OpenCL or GLSL by importing their standard.
 * This standard contains all of the functions provided by them and
 * a mapping between the function names and corresponding numbers.
 */
@Generated("beehive-lab.spirvbeehivetoolkit.generator")
public class SPIRVExtInstMapper {
    private static HashMap<Integer, String> extInstNameMap;

    /**
     * Get the name of the function mapped to the given number.
     * @param opCode - The number of the external function
     * @return The name of the external function mapped to this number.
     */
    public static String get(int opCode) {
        return extInstNameMap.get(opCode);
    }

    /**
     * Load all of the mapped values into memory (otherwise it is not available in order save space)
     */
    public static void loadOpenCL() {
        extInstNameMap = new HashMap<>(${instructions?size?string.computer});
        <#list instructions as instruction>
        extInstNameMap.put(${instruction.opCode?string.computer}, "${instruction.opName}");
        </#list>
    }
}