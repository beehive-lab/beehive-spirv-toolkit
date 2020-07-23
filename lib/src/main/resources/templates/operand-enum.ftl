package uk.ac.manchester.spirvproto.lib.instructions.operands;

import javax.annotation.Generated;
import java.nio.ByteBuffer;

@Generated("beehive-lab.spirv-proto.generator")
public class SPIRV${kind} extends SPIRVEnum {
<#list enumerants as enum>
    public static SPIRV${kind} ${enum.name} = new SPIRV${kind}(${enum.value});
</#list>
    public static EmptySPIRV${kind} Empty = new EmptySPIRV${kind}();

    protected SPIRV${kind}(int value) {
        super(value);
    }

    public static class EmptySPIRV${kind} extends SPIRV${kind} {
        protected EmptySPIRV${kind}() {
            super(-1);
        }

        @Override
        public void write(ByteBuffer output) {
            // Do nothing as this is empty
        }

        @Override
        public int getWordCount() {
            return 0;
        }
    }
}
