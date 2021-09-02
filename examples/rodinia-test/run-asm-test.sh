#!/bin/bash

FILES=${1:-$(find ../rodinia -type f -name "*.spv")}

# Check all files
for FILE in $FILES; do
	# Assemble
	java -jar ../../dist/spirv-beehive-toolkit.jar -d $FILE -o proto/out.spvasm
	java -jar ../../dist/spirv-beehive-toolkit.jar -d --tool asm -o proto/out.spv proto/out.spvasm

	# Validate
	if spirv-val proto/out.spv; then
		echo "$FILE success"
	else 
		echo "$FILE fail"
	fi
done
