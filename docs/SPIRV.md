# The SPIR-V binary module
SPIR-V is a low level IR for representing GPU programs (shaders and kernels). It represents a Control Flow Graph in SSA format with all necessary information for a compiler backend to be able to compile into GPU programs.

## Format
A SPIR-V module consists of a stream of 32 bit integers, from here on called slots. A module has two parts:

- A header
- A list of instructions

### Header
The header occupies the first 5 slots of a SPIR-V binary module. The data contained in the header is:

1. Magic number : Identifies a SPIR-V module and helps determining endianness.
2. SPIR-V Version: The version of the SPIR-V specification this module adheres to.
3. Generator ID: Magic number for the tool that generated this SPIR-V module. Is allowed to be 0, or alternatively can be registered with the Khronos Group [here](https://github.com/KhronosGroup/SPIRV-Headers)
4. Bound: All IDs for nodes are below this number (should be as small as possible)
5. Schema: Instruction schema (if needed). Usually 0.

### Instructions

Here instruction means a node that consists of one operation and zero or more operands, each having zero or more parameters.

#### Operations

Each instruction starts with a slot that encodes the opcode and the length of the instruction in number of slots. The lower 16 bits contains the opcode, while the remaining higher 16 bits contains the length. This is needed in case of quantified operands and helps traversing a module without decoding all instructions.

#### Operands

Some instructions have operands. They can be:

- Literals (string or integer)
- Enums (Bit and Value)
- IDs (target, result or ref)

Some operands require certain capabilities or extensions to be present. These need to be declared at the front of the module. Using these the backend can determine if further compilation is possible (or the target format).

Some operands can be quantified with either '?' (0 or one) or '*' (0 or more). These can be determined by examining the word count of an instruction.

#### Parameters

Some operands have parameters. These behave the same as operands except that parameters are never quantified.


## Layout

The specification of SPIR-V describes a strict order of operations: 

1.  [Required] Capabilities: All `OpCapability` instructions.
2.  [Optional] Extensions: All `OpExtensions`
3.  [Optional] External imports: All `OpExtInstImport` (for example opencl.std for built-ins)
4.  [Required] Memory Model: One `OpMemoryModel` instruction 
5.  [Required] Entry points: One or more `OpEntryPoint` instructions (unless the Linkage capability is used)
6.  [Optional] Execution Mode: All `OpExecutionMode` or `OpExecutionModeId` instructions
7.  [Optional] Debug instructions: These instructions grouped together as follows:
     1. `OpString`, `OpSourceExtension`, `OpSource`, `OpSourceContinued`
     2. `OpName` and `OpMemberName`
     3. `OpModuleProcessed`
8.  [Optional] Annotations: ALl decorations instructions (`OpDecorate`, `OpMemberDecorate`, `OpGroupDecorate`, `OpGroupMemberDecorate`, `OpGroupDecoration`)
9.  [Optional] Type and global variable declarations, constants, : All `OpType{...}`, `OpVariable` with storage class other than Function and `OpConstant` instructions
10. [Optional] Function declarations (no body): In a function declaration the following is required:
     1. Function declaration `OpFunction`
     2. All parameters using `OpFunctionParameter`
     3. Function end using `OpFunctionEnd`
 11. [Optional] Function definitions (with body): The only difference to a function declaration is that there is a list of blocks after the function parameters

#### Blocks
- Blocks always exist in a function
- Blocks start with an `OpLabel` instruction
- Blocks end with a termination instruction (Branch instructions(`OpBranch`, `OpBranchConditional`, `OpSwitch`, `OpReturn`, `OpReturnValue`), `OpKill`, `OpUnreachable`)
- `OpVariable` instructions must have Function as storage class
- All `OpVariable` instructions in a function must be the first instructions in the first block of that function (except for `OpPhi`, which cannot be in the first block)