package uk.ac.manchester.spirvproto.generator.grammar;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class SPIRVSpecification {

    public static SPIRVGrammar buildSPIRVGrammar(int majorVersion, int minorVersion) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String pathToSpecs = String.format("resources/versions/%d.%d/spirv.core.grammar.json", majorVersion, minorVersion);
        URL resource = SPIRVSpecification.class.getClassLoader().getResource(pathToSpecs);
        if (resource == null) {
            // Fall back to unified
            resource = SPIRVSpecification.class.getClassLoader().getResource("resources/versions/unified/spirv.core.grammar.json");
        }
        return mapper.readValue(resource, SPIRVGrammar.class);
    }
}
