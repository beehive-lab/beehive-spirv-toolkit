package uk.ac.manchester.spirvproto.generator.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVOperand {
    @JsonProperty("kind")
    public String kind;

    @JsonProperty("name")
    public String name;

    @JsonProperty("quantifier")
    public char quantifier;

    public String getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public char getQuantifier() {
        return quantifier;
    }
}
