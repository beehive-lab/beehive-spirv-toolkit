package uk.ac.manchester.spirvproto.lib.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVExternalImport {
    @JsonProperty("instructions")
    public SPIRVExternalInstruction[] instructions;

    public SPIRVExternalInstruction getInstruction(int opCode) throws InvalidSPIRVOpcodeException {
        SPIRVExternalInstruction dummy = new SPIRVExternalInstruction();
        dummy.opCode = opCode;
        int index = Arrays.binarySearch(instructions, dummy);
        if (index > 0 && index < instructions.length) return instructions[index];

        throw new InvalidSPIRVOpcodeException(opCode);
    }

    public static SPIRVExternalImport importExternal(String name) throws IOException, SPIRVUnsupportedExternalImport {

        String pathToSpecs = String.format("resources/versions/unified/%s.grammar.json", name.replace("\"", "").toLowerCase());
        URL resource = SPIRVExternalImport.class.getClassLoader().getResource(pathToSpecs);
        if (resource == null) throw new SPIRVUnsupportedExternalImport(name);
        return new ObjectMapper().readValue(resource, SPIRVExternalImport.class);
    }
}
