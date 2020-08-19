# SPIR-V Prototype

This is a prototype written in Java to disassemble and assemble SPIR-V binary modules.
More information on the format of SPIR-V can be found [here](docs/SPIRV.md).

## Build

Dependencies:

- Java
- Maven

Clone the repository:

```bash
$ git clone https://github.com/beehive-lab/spirv-proto.git
```

Go to the root folder:
```bash
$ cd spirv-proto
```

Run maven build:
```bash
$ mvn clean package
```
 
This should create 3 Jar files: the first one is located in `spirv-proto/lib/dist` and contains all the classes necessary to assemble and disassemble SPIR-V modules.  The second jar-file is located in `spirv-proto/runner/dist` and it contains a standalone application that can use the aforementioned library. The last one is in `spirv-proto/dist`, and it contains both jars.

## Usage

```bash
spirv-proto [OPTIONS] <filepath>
 -c,--no-color       Do not use coloured output
 -d,--debug          Print debug information
 -e,--no-header      Do not print the header
 -g,--no-grouping    Do not group composites together
 -h,--help           Prints this message
 -i,--no-indent      Turn off indentation
 -n,--inline-names   Inline names of nodes where possible
 -o,--out <arg>      Specify an output file/directory
 -t,--tool <arg>     Specify the tool to be used: Disassembler: dis [default] | Assembler asm
```

To run the disassembler:

```bash
$ ./spirv-proto examples/vector_add.spv
```

Alternatively, you can run the dissablembler using the provided jar-file:

```bash
$ java -jar dist/spirv-proto.jar examples/vector_add.spv
```

## Creating SPIR-V modules for testing

This is a compiled version of what the Khronos Group recommends. The original can be found [here](https://www.khronos.org/blog/offline-compilation-of-opencl-kernels-into-spir-v-using-open-source-tooling).
 There are some example kernels provided in the examples directory.

Tools needed:

- clang (version 10.0.0 or higher) (To install: `$ sudo apt-get install clang`)
- llvm-spirv (See build instructions [here](https://github.com/KhronosGroup/SPIRV-LLVM) )

Go into the examples folder:

```bash
$ cd examples
```

First, the kernel (.cl file) needs to be compiled to LLVM IR:
```bash 
$ clang -cc1 -triple spir vector_add.cl -O0 -finclude-default-header -emit-llvm-bc -o vector_add.bc
```

Then the LLVM IR can be compiled into SPIR-V:`
```bash
$ llvm-spirv vector_add.bc -o vector_add.spv
```

Now there is at least one SPIR-V module available.

An OpenCL program can then read this module using `clCreateProgramWithIL();`
Such an application is provided in the examples folder and can be compiled with make (this should also create a SPIR-V module):

```bash
$ make build
```

And can be run with: 
```bash
$ make run
```
OR
```bash
$ ./vector_add_il.bin ./vector_add.spv
```

This requires at least one device with a driver that supports OpenCL 2.1 or higher (Intel Graphics or the experimental Intel CPU driver) and an OpenCL ICD Loader that supports OpenCL 2.1 or higher. 

The application tries to run the program on the first device of the last OCL Platform (check `clinfo` to see, which one that is). Your setup might be different and you might have to change that [here](https://github.com/beehive-lab/spirv-proto/blob/665a19e9527f2bf5121ecc23c19e17656bfbf0a2/examples/vector_add_il.c#L72)

In a lot of cases vendors include their own ICD Loader (libOpenCL.so) in their driver package, which means that an outdated ICD Loader might be in use on your system and you won't be able to run the example as `OPENCL_2_1 cannot be found`. To see more information on this visit the [ArchWiki page](https://wiki.archlinux.org/index.php/GPGPU)