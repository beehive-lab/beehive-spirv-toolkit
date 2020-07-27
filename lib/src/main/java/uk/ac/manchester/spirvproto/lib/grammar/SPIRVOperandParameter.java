package uk.ac.manchester.spirvproto.lib.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVOperandParameter {
    @JsonProperty("kind")
    public String kind;

    @JsonProperty("name")
    public String name;

    public String getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }
}
