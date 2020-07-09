package uk.ac.manchester.spirvproto.lib.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVOperandKind {
    @JsonProperty("category")
    public String category;

    @JsonProperty("kind")
    public String kind;

    @JsonProperty("enumerants")
    public SPIRVEnumerant[] enumerants;

    public SPIRVEnumerant getEnumerant(String value) {
        for (SPIRVEnumerant enumerant: enumerants) {
            if (enumerant.value.equals(value)) {
                return enumerant;
            }
        }
        return null;
    }
}
