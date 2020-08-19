package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.util.List;
import java.util.ArrayList;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRV${kind} extends SPIRVEnum {

    protected SPIRV${kind}(int value, String name, List<SPIRVOperand> parameters) {
        super(value, name, parameters);
    }

    @Override
    public void write(ByteBuffer output) {
        super.write(output);
        parameters.forEach(param -> param.write(output));
    }

    public void add(SPIRV${kind} other) {
        if (this.value == 0) this.name = other.name;
        else if (other.value != 0) this.name += "|" + other.name;

        this.value |= other.value;
        this.parameters.addAll(other.parameters);
    }

    <#list enumerants as enum>
    public static SPIRV${kind} ${enum.name}(<#if enum.parameters??><#list enum.parameters as param>SPIRV${param.kind} ${param.name}<#sep>, </#sep></#list></#if>) {
        List<SPIRVOperand> params = new ArrayList<>(<#if enum.parameters??>${enum.parameters?size}<#else>0</#if>);
        <#if enum.parameters??><#list enum.parameters as param>
        params.add(${param.name});
        </#list></#if>
        return new SPIRV${kind}(${enum.value}, "${enum.name}", params);
    }
    </#list>
}
