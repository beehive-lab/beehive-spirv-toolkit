package uk.ac.manchester.spirvproto.generator.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVOperandKind {
    @JsonProperty("category")
    public String category;

    @JsonProperty("kind")
    public String kind;

    @JsonProperty("bases")
    public String[] bases;

    @JsonProperty("enumerants")
    public SPIRVEnumerant[] enumerants;

    public String getCategory() {
        return category;
    }

    public String getKind() {
        return kind;
    }

    public SPIRVEnumerant[] getEnumerants() {
        return enumerants;
    }

    public String[] getBases() {
        return bases;
    }
}
