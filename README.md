# Beehive SPIR-V Toolkit

This is a prototype written in Java to disassemble and assemble SPIR-V binary modules. It provides a Java programming framework to allow client Java applications to assemble binary modules and dissasemble binary modules into text. More information on the format of SPIR-V can be found [here](docs/SPIRV.md). To see an example kernel in SPIR-V go [here](docs/EXAMPLE.md).


## Build on Linux

Dependencies:
- Java JDK 21. It also supports JDK 17, 11 and 8 but this branch will be deprecated soon. We highly recommend JDK 21.
- Maven >= 3.6.3

Clone the repository:

```bash
git clone https://github.com/beehive-lab/beehive-spirv-toolkit.git
cd spirv-beehive-toolkit
mvn clean install
```

## Build on Windows

Working configurations:
- Lenovo IdeaPad Gaming 3 15IHU6
- Windows 11
- Maven 3.9.1, JDK 21

Note: Repo builds without changes on Winos as well as on Linos.


## Usage

The running is a client application to be used from the command line that allows developers to assembly SPIR-V written in a text format into a SPIR-V binary module, and dissasemble SPIR-V modules into a text format. The application can be used as follows:

```bash
beehive-spirv-toolkit [OPTIONS] <filepath>
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
# Linux
./beehive-spirv-toolkit test.spv

# Windows
.\beehive-spirv-toolkit.cmd test.spv
```

Alternatively, you can run the disassembler using the provided jar-file:

```bash
java -jar dist/beehive-spirv-toolkit.jar test.spv
```


## Disassembler/ assembler roundtrip of SPIR-V modules on the command line

How to?

```bash
# SPIR-V binary -> text SPIR-V
java -jar dist/beehive-spirv-toolkit.jar -d test.spv -o test.spirvText

# Text SPIR-V -> SPIR-V binary
java -jar dist/beehive-spirv-toolkit.jar -d  --tool asm -o out.spv test.spirvText

# Disassemble again (binary to text)
java -jar dist/beehive-spirv-toolkit.jar out.spv
```

### Testing the disassembler with SPIRV-DIS on Linux

```bash
## Create SPIR-V binary
java -cp lib/target/beehive-spirv-lib-0.0.3.jar uk.ac.manchester.beehivespirvtoolkit.lib.tests.TestRunnerAssembler

## Disassemble SPIR-V binary
spirv-dis testSPIRV.spv

## Validate SPIR-V
spirv-val testSPIRV.spv
```

### Testing the disassembler with SPIRV-DIS on Windows

The disassembler and validator for testing (`spirv-dis` and `spirv-val`) are available from [The Khronos Group](https://github.com/KhronosGroup/SPIRV-Tools). Building the tools requires an [LLVM](https://github.com/llvm/llvm-project) build-tree. Another implementation is Intels' LLVM based [_oneAPI DPC++ compiler_](https://github.com/intel/llvm).

### Creating SPIR-V modules for testing on Linux

To try the tool with different kernels either a binary SPIR-V module is needed or one that was hand-written in the assembly language of SPIR-V. Since the assembly language is not very human friendly it is recommended that anything worth testing is first written in OpenCL C and then compiled to SPIR-V.

To achieve this a compiled version of what the Khronos Group recommends follows. The original can be found [here](https://www.khronos.org/blog/offline-compilation-of-opencl-kernels-into-spir-v-using-open-source-tooling). There are some example kernels provided in the example directory.

Tools needed:

- clang (version 10.0.0 or higher) (To install: `$ sudo apt-get install clang`)
- llvm-spirv (See build instructions [here](https://github.com/KhronosGroup/SPIRV-LLVM-Translator))

Go into the vector add example directory:

```bash
cd examples/vector_add
```

First, the kernel (`.cl` file) needs to be compiled to LLVM IR:
```bash
clang -cc1 -triple spir vector_add.cl -O0 -finclude-default-header -emit-llvm-bc -o vector_add.bc
```

Then the LLVM IR can be compiled into SPIR-V:
```bash
llvm-spirv vector_add.bc -o vector_add.spv
```

Now there is at least one SPIR-V module available.

An OpenCL program can then read this module using `clCreateProgramWithIL();`

To demonstrate with `vector_add`:

```bash
make build
```

And can be run with:
```bash
make run
```
OR
```bash
./vector_add.bin ./vector_add.spv
```

**This requires at least one device with a driver that supports OpenCL 2.1 or higher** (Intel Graphics or the experimental Intel CPU driver) and an OpenCL ICD Loader that supports OpenCL 2.1 or higher.

The application tries to run the program on the first device of the last OCL Platform (check `clinfo` to see, which one that is). Your setup might be different and you might have to change that [here](https://github.com/beehive-lab/spirv-beehive-toolkit/blob/665a19e9527f2bf5121ecc23c19e17656bfbf0a2/examples/vector_add_il.c#L72)

In a lot of cases vendors include their own ICD Loader (libOpenCL.so) in their driver package, which means that an outdated ICD Loader might be in use on your system and you won't be able to run the example as `OPENCL_2_1 cannot be found`.
In this case you might have to install an updated icd-loader package or configure the ld path to load the updated one.
To see more information on this visit the [ArchWiki page](https://wiki.archlinux.org/index.php/GPGPU)

### Creating SPIR-V modules for testing on Windows

Methods and tool are the same as for Windows. The tool (`llvm-spirv`) is provided by [The Khronos Group](https://github.com/KhronosGroup/SPIRV-LLVM-Translator) and requires [LLVM](https://github.com/llvm/llvm-project) to build.


## Publications

- Juan Fumero, Gyorgy Rethy, Athanasios Stratikopoulos, Nikos Foutris, Christos Kotselidis. **Beehive SPIR-V Toolkit: A Composable and Functional API for Runtime SPIR-V Code Generation**, VMIL'23. [pdf](https://dl.acm.org/doi/pdf/10.1145/3623507.3623555)

```bibtex
@inproceedings{10.1145/3623507.3623555,
    author = {Fumero, Juan and Rethy, Gy\"{o}rgy and Stratikopoulos, Athanasios and Foutris, Nikos and Kotselidis, Christos},
    title = {Beehive SPIR-V Toolkit: A Composable and Functional API for Runtime SPIR-V Code Generation},
    year = {2023},
    isbn = {9798400704017},
    publisher = {Association for Computing Machinery},
    address = {New York, NY, USA},
    url = {https://doi.org/10.1145/3623507.3623555},
    doi = {10.1145/3623507.3623555},
    booktitle = {Proceedings of the 15th ACM SIGPLAN International Workshop on Virtual Machines and Intermediate Languages},
    pages = {61--72},
    numpages = {12},
    keywords = {Library, Java, API, SPIR-V, Runtime Code Generation, Metaprogramming},
    location = {Cascais, Portugal},
    series = {VMIL 2023}
}
```


## License

This project is developed at [The University of Manchester](https://www.manchester.ac.uk/), and it is fully open source under the [MIT](https://github.com/beehive-lab/spirv-beehive-toolkit/blob/master/LICENSE) license.


## Acknowledgments

This work is partially funded by [Intel corporation](https://www.intel.com/content/www/us/en/homepage.html).
In addition, it has been supported by the following EU & UKRI grants (most recent first):
- EU Horizon Europe & UKRI [AERO 101092850](https://aero-project.eu/).
- EU Horizon Europe & UKRI [INCODE 101093069](https://incode-project.eu/).
- EU Horizon Europe & UKRI [ENCRYPT 101070670](https://encrypt-project.eu).
- EU Horizon Europe & UKRI [TANGO 101070052](https://tango-project.eu).
- EU Horizon 2020 [ELEGANT 957286](https://www.elegant-h2020.eu/).
