# This is a quick and dirty makefile so that the examples can be run
# Will needto be updated for the fututre

build:
	gcc matrix_mul.c -lm -o matrix_mul.bin -lOpenCL
	clang -cc1 -triple spir matrix_mul.cl -O0 -finclude-default-header -std=CL1.2 -emit-llvm-bc -o matrix_mul.bc
	llvm-spirv matrix_mul.bc -o matrix_mul.spv

run: build
	./matrix_mul.bin ./matrix_mul.spv
