package uk.ac.manchester.spirvbeehivetoolkit.lib.instructions.operands;

import javax.annotation.Generated;
import java.util.List;
import java.util.ArrayList;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirvbeehivetoolkit.generator")
public class SPIRV${kind} extends SPIRVEnum {

    protected SPIRV${kind}(int value, String name, List<SPIRVOperand> parameters, SPIRVCapability... capabilities) {
        super(value, name, parameters, capabilities);
    }

    @Override
    public void write(ByteBuffer output) {
        super.write(output);
        parameters.forEach(param -> param.write(output));
    }

    <#if category == "BitEnum">
    public void add(SPIRV${kind} other) {
        if (this.value == 0) this.name = other.name;
        else if (other.value != 0) this.name += "|" + other.name;

        this.value |= other.value;
        this.parameters.addAll(other.parameters);
        SPIRVCapability[] oldCapabilities = this.capabilities;
        this.capabilities = new SPIRVCapability[oldCapabilities.length + other.capabilities.length];
        int capPos = 0;
        for (SPIRVCapability capability : oldCapabilities) {
            this.capabilities[capPos++] = capability;
        }
        for (SPIRVCapability capability : other.capabilities) {
            this.capabilities[capPos++] = capability;
        }
    }
    </#if>

    <#list enumerants as enum>
    public static SPIRV${kind} ${enum.name}(<#if enum.parameters??><#list enum.parameters as param>SPIRV${param.kind} ${param.name}<#sep>, </#sep></#list></#if>) {
        List<SPIRVOperand> params = new ArrayList<>(<#if enum.parameters??>${enum.parameters?size}<#else>0</#if>);
        <#if enum.parameters??><#list enum.parameters as param>
        params.add(${param.name});
        </#list></#if>
        return new SPIRV${kind}(${enum.value}, "${enum.name}", params<#if enum.capabilities ??><#list enum.capabilities as capability>, SPIRVCapability.${capability}()</#list></#if>);
    }
    </#list>
}
