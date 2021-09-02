package uk.ac.manchester.spirvbeehivetoolkit.lib.assembler;

import java.util.HashMap;
import javax.annotation.Generated;

/**
 * SPIR-V handles built in functions by OpenCL or GLSL by importing their standard.
 * This standard contains all of the functions provided by them and
 * a mapping between the function names and corresponding numbers.
 */
@Generated("beehive-lab.spirvbeehivetoolkit.generator")
class SPIRVExtInstMapper {

    private static HashMap<String , Integer> extInstNameMap;

    /**
     * Get the number of the function mapped to the given name.
     * @param name - A string containing the name of the function.
     * @return The number of the external function mapped to this string.
     */
    public static int get(String name) {
        return extInstNameMap.get(name);
    }

    /**
     * Load all of the mapped values into memory (otherwise it is not available in order save space)
     */
    public static void loadOpenCL() {
        extInstNameMap = new HashMap<>(${instructions?size?string.computer});
        <#list instructions as instruction>
        extInstNameMap.put("${instruction.opName}", ${instruction.opCode?string.computer});
        </#list>
    }
}