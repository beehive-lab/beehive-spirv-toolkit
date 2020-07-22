package uk.ac.manchester.spirvproto.lib.grammar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SPIRVEnumerant implements Comparable<SPIRVEnumerant>{
    @JsonProperty("enumerant")
    public String name;

    @JsonProperty("value")
    public String value;

    @JsonProperty("parameters")
    public SPIRVOperandParameter[] parameters;

    @Override
    public int compareTo(SPIRVEnumerant o) {
        return this.getValue().compareTo(o.getValue());
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public SPIRVOperandParameter[] getParameters() {
        return parameters;
    }
}
