# OpenCL C to SPIR-V

Since OpenCL C compiles to SPIR-V it is beneficial to see how C maps to SPIR-V.
In this example a simple matrix multiplication kernel is compiled to SPIR-V and the disassembly is inspected.

The whole kernel is as follows:
```opencl
__kernel void matrix_mul(__global float A[], 
                         __global float B[], 
                         __global float C[], 
                         unsigned int size) {
	size_t idX = get_global_id(0);
	size_t idY = get_global_id(1);

	float sum = 0.0f;
	for (int k = 0; k < size; k++) {
		sum += A[(idX * size) + k] * B[(k * size) + idY];
	}
	C[(idX * size) + idY] = sum;
}
```

Looking at the whole disassembled module is quite complicated so here a line-by-line comparison will follow.

The first line declares the kernel as a C function and provides the signature.
In SPIR-V this would require nodes for the return type of the function and for the parameters.
The module also needs to provide the name of the kernel/function and the signature.

It looks like this:

```c
__kernel void matrix_mul(__global float A[], 
                         __global float B[], 
                         __global float C[], 
                         unsigned int size)
```

In SPIR-V it is:

```c
; Declare an entry point with it's name and interface. For more info see: https://www.khronos.org/registry/spir-v/specs/unified1/SPIRV.html#OpEntryPoint
                                     OpEntryPoint Kernel %10 "matrix_mul" %__spirv_BuiltInGlobalInvocationId
                                     OpDecorate %72 Alignment 4 ; Decorate %72 (which is a group) as having an alignemnt of 4 bytes
                               %72 = OpDecorationGroup 
                                     OpGroupDecorate %72 %A.addr %B.addr %C.addr %size.addr %idX %idY %sum %k ; Decorate the group 
                                     OpDecorate %__spirv_BuiltInGlobalInvocationId BuiltIn GlobalInvocationId ; Decorate variable as the built-in global id
                                     OpDecorate %__spirv_BuiltInGlobalInvocationId Constant
                                     OpDecorate %__spirv_BuiltInGlobalInvocationId LinkageAttributes "__spirv_BuiltInGlobalInvocationId" Import ; Import the built-in variable
                             %uint = OpTypeInt 32 0 ; Declare an unsigned 32 bit integer type
                             %void = OpTypeVoid ; Declare a void type
                            %float = OpTypeFloat 32 ; Declare a 32 bit floating point number type
                        %ptr_float = OpTypePointer CrossWorkgroup %float ; Declare a pointer type that points to floats in global memory
                         %v_uint_3 = OpTypeVector %uint 3 ; Declare a vector type that is comprised of 3 unsigned 32 bit integers
                     %ptr_v_uint_3 = OpTypePointer Input %v_uint_3 ; Declare a pointer type that points to vectors in the input from pipeline
%__spirv_BuiltInGlobalInvocationId = OpVariable %ptr_v_uint_3 Input ; Declare a global variable of type vector pointer residing in the input from pipeline
; Declare the type of the function, where the function returns void, and takes parameters of type: float pointer, float pointer, float pointer, unsigned integer
                                %9 = OpTypeFunction %void %ptr_float %ptr_float %ptr_float %uint
                               %10 = OpFunction %void DontInline %9 ; Declare a function, which returns void, and has type %9
                                %A = OpFunctionParameter %ptr_float ; Parameter 1 aka __global float A[]
                                %B = OpFunctionParameter %ptr_float ; Parameter 2 aka __global float B[]
                                %C = OpFunctionParameter %ptr_float ; Parameter 3 aka __global float C[]
                             %size = OpFunctionParameter %uint ; Parameter 4 aka unsigned int size
                                    ...
                                     OpFunctionEnd ; Signal the end of the current function
```

Next the kernel reads the global id on the first and second axes:
```c
size_t idX = get_global_id(0);
size_t idY = get_global_id(1);
```

Although in OpenCL C this looks like a function call in SPIR-V this is translated to a read from a special memory location given through `Input`:

```c
; Load a vector with 3 unsigned integer elements from the variable holding their location (declared as part of the interface of the function)
  %idX = OpVariable %ptr_uint Function ; Declare variable %idX residing in function local memory
  %idY = OpVariable %ptr_uint Function ; Declare variable %idY residing in function local memory
   %31 = OpLoad %v_uint_3 %__spirv_BuiltInGlobalInvocationId Aligned 16 ; Load the value pointed to by the built-in 
 %call = OpCompositeExtract %uint %31 0 ; Extract the first element of the vector loaded previously
         OpStore %idX %call Aligned 4 ; Save the first element extracted above into variable %idX
%call1 = OpCompositeExtract %uint %33 1 ; Extract the second element of the vector loaded above
         OpStore %idY %call1 Aligned 4 ; Save the second element to variable %idY
```

Next the kernel declares a variable with initial value of 0.0:
```c
float sum = 0.0f;
```

In SPIR-V this translates to:

```c
%35 = OpConstant %float 0
...
%sum = OpVariable %ptr_float Function 
       OpStore %sum %35 Aligned 4
```

After that a for loop is declared:
```c
for (int k = 0; k < size; k++) {
    ...
}
```

In SPIR-V this translates to:

```c
       %61 = OpConstant %uint 1 ; Declare a constant with value 1
             ...
 %for.cond = OpLabel ; Create a new block
       %37 = OpLoad %uint %k Aligned 4 ; Load a uint value from memory location pointed to by %k
       %38 = OpLoad %uint %size.addr Aligned 4 ; Load a uint value from memory location pointed to by %size.addr
      %cmp = OpULessThan %bool %37 %38 ; Check if the value pointed to by %k is smaller than the value pointed to by %size.addr
             OpBranchConditional %cmp %for.body %for.end ; If it is smaller branch to the body of the loop, otherwise branch to the end
 %for.body = OpLabel ; Create a new block
             ... ; The loop body, this will be inspected later
  %for.inc = OpLabel ; Create a new block
       %60 = OpLoad %uint %k Aligned 4 ; Load the value pointed to by %k
      %inc = OpIAdd %uint %60 %61 ; Add the value of %61 to the value loaded previously
             OpStore %k %inc Aligned 4 ; Store the new value back
             OpBranch %for.cond ; Branch to the start of the loop
  %for.end = OpLabel ; Create a new block
```

The loop body:
```c
sum += A[(idX * size) + k] * B[(k * size) + idY];
```

The loop body in SPIR-V;

```c
 %for.body = OpLabel  ; Create a new block
       %41 = OpLoad %ptr_float %A.addr Aligned 4 ; Load the value pointed to by %A.addr
       %42 = OpLoad %uint %idX Aligned 4 ; Load the value pointed to by %idX
       %43 = OpLoad %uint %size.addr Aligned 4 ; Load the value pointed to by %size.addr
      %mul = OpIMul %uint %42 %43 ; Multiply the values of the two loads from above
       %45 = OpLoad %uint %k Aligned 4 ; Load the value pointed to by %k
      %add = OpIAdd %uint %mul %45 ; Add the values from the multiplication and the previous load
;Get the pointer which has a base pointed to by %A.addr (aka %A, which is itself a pointer), and an offset given by %add 
 %arrayidx = OpInBoundsPtrAccessChain %ptr_float %41 %add 
       %48 = OpLoad %float %arrayidx Aligned 4 ; Load the value from the address calculated previously
      
       %49 = OpLoad %ptr_float %B.addr Aligned 4 ; Load the value pointed to by %B.addr
       %50 = OpLoad %uint %k Aligned 4 ; Load the value pointed to by %k
       %51 = OpLoad %uint %size.addr Aligned 4 ; Load the value pointed to by %size.addr
     %mul2 = OpIMul %uint %50 %51 ; Multiply the values of the two loads from above
       %53 = OpLoad %uint %idY Aligned 4 ; Load the value pointed to by %idY
     %add3 = OpIAdd %uint %mul2 %53 ; ; Add the values from the multiplication and the previous load
Get the pointer which has a base pointed to by %B.addr (aka %B, which is itself a pointer), and an offset given by the previous addition
%arrayidx4 = OpInBoundsPtrAccessChain %ptr_float %49 %add3 
       %56 = OpLoad %float %arrayidx4 Aligned 4 ; Load the value from the address calculated previously
       %57 = OpLoad %float %sum Aligned 4 ; Load the value pointed to by %sum
       %58 = OpFMul %float %48 %56 ; Multiply together the two values retrieved aka (A[...] * B[...])
       %59 = OpFAdd %float %58 %57 ; Add the result of the multiplication to sum loaded above
             OpStore %sum %59 Aligned 4 ; Store the value back to sum
             OpBranch %for.inc ; Branch to the incrememnt block of the for loop
  %for.inc = OpLabel
```

Last the result is stored:

```c
C[(idX * size) + idY] = sum;
```

In SPIR-V:

```c
       %63 = OpLoad %float %sum Aligned 4       ; Load the result
       %64 = OpLoad %ptr_float %C.addr Aligned 4 ; Load the pointer
       %65 = OpLoad %uint %idX Aligned 4 ; Load idX
       %66 = OpLoad %uint %size.addr Aligned 4 ; Load the size
     %mul6 = OpIMul %uint %65 %66 ; Multiply size and idX
       %68 = OpLoad %uint %idY Aligned 4 ; Load idY
     %add7 = OpIAdd %uint %mul6 %68 ; Add idY and the result of the multiplication
%arrayidx8 = OpInBoundsPtrAccessChain %ptr_float %64 %add7  ; Get the pointer pointing to the "add7"th element of C
             OpStore %arrayidx8 %63 Aligned 4 ; Store the result to the above calculated address
```