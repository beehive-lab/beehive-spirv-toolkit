package uk.ac.manchester.spirvtool.lib.grammar;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SPIRVSpecification {

    public static SPIRVGrammar buildSPIRVGrammar() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        URL resource = SPIRVSpecification.class.getClassLoader().getResource("resources/spirv.core.grammar.json");
        return mapper.readValue(resource, SPIRVGrammar.class);
    }
}
