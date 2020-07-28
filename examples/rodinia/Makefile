build:
	find . -type f -name "*.cl" -print | xargs -i -t clang-10 -cc1 -triple spir {} -O0 -finclude-default-header -emit-llvm-bc -w -std=cl1.2
	find . -type f -name "*.bc" -print | xargs -i -t llvm-spirv {}

clean:
	find . -type f \( -name "*.bc" -or -name "*.spv" \) -print -delete