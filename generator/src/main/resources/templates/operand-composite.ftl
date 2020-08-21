package uk.ac.manchester.spirvproto.lib.instructions.operands;

import uk.ac.manchester.spirvproto.lib.disassembler.SPIRVPrintingOptions;

import javax.annotation.Generated;
import java.io.PrintStream;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRV${kind} implements SPIRVOperand {
    <#list bases as base>
    private final SPIRV${base} member${base?counter};
    </#list>

    public final SPIRVCapability[] capabilities;

    public SPIRV${kind}(<#list bases as base>SPIRV${base} member${base?counter}<#sep>, </#sep></#list>) {
        <#list bases as base>
        this.member${base?counter} = member${base?counter};
        </#list>

        capabilities = new SPIRVCapability[<#list bases as base>member${base?counter}.getCapabilities().length<#sep> + </#sep></#list>];
        int capPos = 0;
        <#list bases as base>
        for (SPIRVCapability capability : member${base?counter}.getCapabilities()) {
            capabilities[capPos++] = capability;
        }
        </#list>
    }

    @Override
    public void write(ByteBuffer output) {
        <#list bases as base>
        member${base?counter}.write(output);
        </#list>
    }

    @Override
    public int getWordCount() {
        return <#list bases as base>member${base?counter}.getWordCount()<#sep> + </#sep></#list>;
    }

    @Override
    public void print(PrintStream output, SPIRVPrintingOptions options) {
        if (!options.turnOffGrouping) output.print("{");
        <#list bases as base>
        member${base?counter}.print(output, options);
        <#sep>
        output.print(" ");
        </#sep>
        </#list>
        if (!options.turnOffGrouping) output.print("}");
    }

    @Override
    public SPIRVCapability[] getCapabilities() {
        return capabilities;
    }
}
