# This is a quick and dirty makefile so that the examples can be run
# Will needto be updated for the fututre

build:
	gcc vector_add.c -lm -o vector_add.bin -lOpenCL
	clang -cc1 -triple spir vector_add.cl -O0 -finclude-default-header -emit-llvm-bc -o vector_add.bc
	llvm-spirv vector_add.bc -o vector_add.spv

run: build
	./vector_add.bin ./vector_add.spv
