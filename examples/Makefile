# This is a quick and dirty makefile so that the examples can be run
# Will needto be updated for the fututre

build:
	gcc -lOpenCL -lm vector_add_il.c -o vector_add_il.bin
	clang -cc1 -triple spir vector_add.cl -O0 -finclude-default-header -emit-llvm-bc -o vector_add.bc
	llvm-spirv vector_add.bc -o vector_add.spv

run: build
	./vector_add_il.bin ./vector_add.spv
