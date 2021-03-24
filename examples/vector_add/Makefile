
all:
	g++ -std=c++0x vector_add.cpp -lm -o vector_add.bin -lOpenCL
	clang -cc1 -triple spir vector_add.cl -O0 -finclude-default-header -emit-llvm-bc -o vector_add.bc
	llvm-spirv vector_add.bc -o vector_add.spv

run: all
	./vector_add.bin ./vector_add.spv
