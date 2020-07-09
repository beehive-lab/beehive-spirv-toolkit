package uk.ac.manchester.spirvproto.lib.grammar;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class SPIRVSpecification {

    public static SPIRVGrammar buildSPIRVGrammar(int majorVersion, int minorVersion) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String pathToSpecs = String.format("resources/versions/%d.%d/spirv.core.grammar.json", majorVersion, minorVersion);
        URL resource = SPIRVSpecification.class.getClassLoader().getResource(pathToSpecs);
        return mapper.readValue(resource, SPIRVGrammar.class);
    }
}
