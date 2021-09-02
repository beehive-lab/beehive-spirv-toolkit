package uk.ac.manchester.spirvbeehivetoolkit.generator.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVExternalImport {
    @JsonProperty("instructions")
    public SPIRVExternalInstruction[] instructions;

    public SPIRVExternalInstruction[] getInstructions() {
        return instructions;
    }

    public static SPIRVExternalImport importExternal(String name) throws IOException, SPIRVUnsupportedExternalImport {

        String pathToSpecs = String.format("resources/versions/unified/%s.grammar.json", name.replace("\"", "").toLowerCase());
        URL resource = SPIRVExternalImport.class.getClassLoader().getResource(pathToSpecs);
        if (resource == null) throw new SPIRVUnsupportedExternalImport(name);
        return new ObjectMapper().readValue(resource, SPIRVExternalImport.class);
    }
}
