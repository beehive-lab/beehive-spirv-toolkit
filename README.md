# SPIR-V Prototype

This is a prototype written in Java to disassemble and assemble SPIR-V binary modules.
More information on the format of SPIR-V can be found [here](docs/SPIRV.md).
To see an example kernel in SPIR-V go [here](docs/EXAMPLE.md).

## Build

Dependencies:

- Java
- Maven

Clone the repository:

```bash
$ git clone https://github.com/beehive-lab/spirv-proto.git
```

Go to the root directory:
```bash
$ cd spirv-proto
```

Run maven build:
```bash
$ mvn clean package
```

#### Structure
There are 3 modules: `generator`, `lib`, `runner`.

The `generator` module is a standalone program that reads the included SPIR-V grammar files and writes classes needed 
by `lib` to the specified output directory.
This happens as part of the build controlled by the module's pom file.
In order to change any of the behaviour the following snippet needs to be changed:
```xml
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>exec-maven-plugin</artifactId>
  <version>1.6.0</version>
  <executions>
    <execution>
      <phase>package</phase>
      <goals>
        <goal>java</goal>
      </goals>
      <configuration>
        <skip>${maven.exec.skip}</skip>
        <mainClass>uk.ac.manchester.spirvproto.generator.Runner</mainClass>
        <arguments>
          <argument>${project.parent.basedir}/lib/src/main/java/uk/ac/manchester/spirvproto/lib</argument>
          <argument>${spirv.gen.majorversion}</argument>
          <argument>${spirv.gen.minorversion}</argument>
          <argument>${spirv.gen.magicnumber}</argument>
        </arguments>
      </configuration>
    </execution>
  </executions>
</plugin>
```

The `lib` module holds all of the SPIR-V related logic and code. 
It is able to assemble and disassemble SPIR-V files.

The `runner` module is the "front-end" for the `lib` module.
It only handles CLI argument parsing, calling the selected tool and error reporting.

Maven will create a fat-jar, that includes both `lib` and `runner` in the dist directory.

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
$ ./spirv-proto examples/vector_add/vector_add.spv
```

Alternatively, you can run the dissablembler using the provided jar-file:

```bash
$ java -jar dist/spirv-proto.jar examples/vector_add/vector_add.spv
```
## Examples

There are some examples included:
1. A simple vector addition
2. A simple matrix multiplication
3. From the [rodinia benchmarks](https://github.com/yuhc/gpu-rodinia) lud was modified to use a SPIR-V kernel instead of an OpenCL C kernel.

All of these can be found in the `examples` directory. 
In order to run any of the examples at least one OpenCL 2.1 or higher platform is needed (e.g. Intel iGPU).

### Creating SPIR-V modules for testing

To try the tool with different kernels either a binary SPIR-V module is needed or one that was hand written in the assembly language of SPIR-V.
Since the assembly language is not very human friendly it is recommended that anything worth testing is first written in OpenCL C and then compiled to SPIR-V.

To achieve this a compiled version of what the Khronos Group recommends follows. The original can be found [here](https://www.khronos.org/blog/offline-compilation-of-opencl-kernels-into-spir-v-using-open-source-tooling).
There are some example kernels provided in the examples directory.

Tools needed:

- clang (version 10.0.0 or higher) (To install: `$ sudo apt-get install clang`)
- llvm-spirv (See build instructions [here](https://github.com/KhronosGroup/SPIRV-LLVM) )

Go into the vector add example directory:

```bash
$ cd examples/vector_add
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
To demonstrate with vector_add:

```bash
$ make build
```

And can be run with: 
```bash
$ make run
```
OR
```bash
$ ./vector_add.bin ./vector_add.spv
```

This requires at least one device with a driver that supports OpenCL 2.1 or higher (Intel Graphics or the experimental Intel CPU driver) and an OpenCL ICD Loader that supports OpenCL 2.1 or higher. 

The application tries to run the program on the first device of the last OCL Platform (check `clinfo` to see, which one that is). Your setup might be different and you might have to change that [here](https://github.com/beehive-lab/spirv-proto/blob/665a19e9527f2bf5121ecc23c19e17656bfbf0a2/examples/vector_add_il.c#L72)

In a lot of cases vendors include their own ICD Loader (libOpenCL.so) in their driver package, which means that an outdated ICD Loader might be in use on your system and you won't be able to run the example as `OPENCL_2_1 cannot be found`. To see more information on this visit the [ArchWiki page](https://wiki.archlinux.org/index.php/GPGPU)