package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRV${kind} implements SPIRVOperand {
    <#list bases as base>
    private final SPIRV${base} member${base?index};
    </#list>

    public SPIRV${kind}(<#list bases as base>SPIRV${base} member${base?index}<#sep> ,</#sep></#list>) {
        <#list bases as base>
        this.member${base?index} = member${base?index};
        </#list>
    }

    @Override
    public void write(ByteBuffer output) {
        <#list bases as base>
        member${base?index}.write(output);
        </#list>
    }

    @Override
    public int getWordCount() {
        return <#list bases as base>member${base?index}.getWordCount()<#sep> + </#sep></#list>;
    }
}
