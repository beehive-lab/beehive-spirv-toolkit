package uk.ac.manchester.spirvtool.lib.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVGrammar {
    @JsonProperty("magic_number")
    public String magicNumber;
}
