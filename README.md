# SPIR-V Beehive Toolkit

This is a prototype written in Java to disassemble and assemble SPIR-V binary modules. It provides a Java programming framework to allow client Java applications to assemble binary modules and dissasemble binary modules into text. More information on the format of SPIR-V can be found [here](docs/SPIRV.md). To see an example kernel in SPIR-V go [here](docs/EXAMPLE.md).

## How to Build?

Dependencies:

- Java JDK 11. It also supports JDK 8 but this branch will be deprecated soon. We highly recoomand JDK >= 11. 
- Maven (>= 3.6.3) 

Clone the repository:

```bash
$ git clone https://github.com/beehive-lab/spirv-beehive-toolkit.git
$ cd spirv-beehive-toolkit
$ mvn clean install
```

## Usage

The running is a client application to be used from the the command line that allows developers to assembly SPIRV written in a text format into a SPIRV binary module, and dissasemble SPIRV modules into a text format. The application can be used as follows:

```bash
spirv-beehive-toolkit [OPTIONS] <filepath>
 -c,--no-color       Do not use coloured output
 -d,--debug          Print debug information
 -e,--no-header      Do not print the header
 -g,--grouping       Group composites together
 -h,--help           Prints this message
 -i,--no-indent      Turn off indentation
 -n,--inline-names   Inline names of nodes where possible
 -o,--out <arg>      Specify an output file/directory
 -t,--tool <arg>     Specify the tool to be used: Disassembler: dis [default] | Assembler asm
```

To run the disassembler:

```bash
$ ./spirv-beehive-toolkit examples/vector_add/vector_add.spv
```

Alternatively, you can run the dissablembler using the provided jar-file:

```bash
$ java -jar dist/spirv-beehive-toolkit.jar examples/vector_add/vector_add.spv
```

## Dissasembler and Assembler 

How to?

```bash
# SPIR-V Binary -> Text SPIR-V 
$ java -jar dist/spirv-beehive-toolkit.jar -d test.spv -o test.spirvText

# Text SPIR-V -> SPIR-V Binary
$ java -jar dist/spirv-beehive-toolkit.jar -d  --tool asm -o out.spv test.spirvText
```


### Testing the dissasembler with SPIRV-DIS 

```bash 
$ java -cp lib/target/spirv-lib-1.0-SNAPSHOT.jar uk.ac.manchester.spirvbeehivetoolkit.lib.tests.TestRunnerAssembler
$ spirv-dis /tmp/testSPIRV.spv

## Validate spir-v
$ spirv-val /tmp/testSPIRV.spv
```

## Examples

There are some examples included:
1. A simple vector addition
2. A simple matrix multiplication
3. From the [rodinia benchmarks](https://github.com/yuhc/gpu-rodinia) lud was modified to use a SPIR-V kernel instead of an OpenCL C kernel.

All of these can be found in the `examples` directory. 
In order to run any of the examples at least one OpenCL 2.1 or higher platform is needed (e.g. Intel iGPU).


#### Creating SPIR-V modules for testing

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

The application tries to run the program on the first device of the last OCL Platform (check `clinfo` to see, which one that is). Your setup might be different and you might have to change that [here](https://github.com/beehive-lab/spirv-beehive-toolkit/blob/665a19e9527f2bf5121ecc23c19e17656bfbf0a2/examples/vector_add_il.c#L72)

In a lot of cases vendors include their own ICD Loader (libOpenCL.so) in their driver package, which means that an outdated ICD Loader might be in use on your system and you won't be able to run the example as `OPENCL_2_1 cannot be found`. 
In this case you might have to install an updated icd-loader package or configure the ld path to load the updated one.
To see more information on this visit the [ArchWiki page](https://wiki.archlinux.org/index.php/GPGPU)


## Structure of this Repository

There are 3 Java modules: `generator`, `lib`, `runner`.

The `generator` module is a standalone program that reads the included SPIR-V grammar files and writes classes needed by `lib` to the specified output directory.

This happens as part of the build controlled by the module's pom file. In order to change any of the behaviour the following snippet needs to be changed:

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
        <mainClass>Runner</mainClass>
        <arguments>
          <argument>${project.parent.basedir}/lib/src/main/java/uk/ac/manchester/spirvbeehivetoolkit/lib</argument>
          <argument>${spirv.gen.majorversion}</argument>
          <argument>${spirv.gen.minorversion}</argument>
          <argument>${spirv.gen.magicnumber}</argument>
        </arguments>
      </configuration>
    </execution>
  </executions>
</plugin>
```

The `lib` module holds all of the SPIR-V related logic and code. It is able to assemble and disassemble SPIR-V files.

The `runner` module is the "front-end" for the `lib` module. It only handles CLI argument parsing, calling the selected tool and error reporting.

Maven will create a fat-jar, that includes both `lib` and `runner` in the dist directory.



## Driver support

Unfortunately driver support for SPIR-V is lacking. 
I have found that Intel is supporting OpenCL 2.1 on their iGPU and CPU devices.
Ubuntu should already have the updated graphics version in apt however you can also download the [latest](https://github.com/intel/compute-runtime/releases) or build from [source](https://github.com/intel/compute-runtime/blob/master/BUILD.md).
There are some guides on how to build from source online as well: https://gist.github.com/Brainiarc7/1d13c7f432ba03a8e38720c83cd973d5. 
In my case I had to build the igc (Intel Graphics Compiler) and gmm (Graphics Memory Management) from source and install them (with `make install`) as the apt provided versions were out of date.
This way the standalone igc compiler will be available to run on any related files.


Intel's latest OpenCL CPU runtime also supports SPIR-V.
It can be installed from [here](https://software.intel.com/content/www/us/en/develop/articles/opencl-drivers.html#cpu-section).
This has not been tested while developing this tools, however it *should* work as expected.


## License

This project is developed at [The University of Manchester](https://www.manchester.ac.uk/), and it is fully open source under the [MIT](https://github.com/beehive-lab/spirv-beehive-toolkit/blob/master/LICENSE) license.


## Acknowledgments

The work was partially funded by the EU Horizon 2020 [Elegant 957286](https://www.elegant-h2020.eu/) project, and Intel Coorporation (https://www.intel.it/content/www/it/it/homepage.html).

