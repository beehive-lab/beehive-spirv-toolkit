package uk.ac.manchester.spirvproto.lib.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVOperandKind {
    @JsonProperty("category")
    public String category;

    @JsonProperty("kind")
    public String kind;

    @JsonProperty("enumerants")
    public SPIRVEnumerant[] enumerants;

    public SPIRVEnumerant getEnumerant(String value) throws InvalidSPIRVEnumerantException {
        for (SPIRVEnumerant enumerant: enumerants) {
            if (enumerant.value.equals(value)) {
                return enumerant;
            }
        }

        throw new InvalidSPIRVEnumerantException(kind, value);
    }
}
