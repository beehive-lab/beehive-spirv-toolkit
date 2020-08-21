package uk.ac.manchester.spirvproto.generator.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVEnumerant {
    @JsonProperty("enumerant")
    public String name;

    @JsonProperty("value")
    public String value;

    @JsonProperty("parameters")
    public SPIRVOperandParameter[] parameters;

    @JsonProperty("capabilities")
    public String[] capabilities;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public SPIRVOperandParameter[] getParameters() {
        return parameters;
    }

    public String[] getCapabilities() {
        return capabilities;
    }
}
