package uk.ac.manchester.spirvproto.lib.instructions.operands;

public class ${kind} extends SPIRV${category} {
<#list enumerants as enum>
    public static ${kind} ${enum.name} = new ${kind}(${enum.value});
</#list>

    protected ${kind}(int value) {
        super(value);
    }
}
