#!/bin/bash

FILES=${1:-$(find ../rodinia -type f -name "*.spv")}

# Generate files
echo "Differences:"
for FILE in $FILES; do
	echo "\t" $FILE
	
	# Prototype
	java -jar ../../dist/spirv-beehive-toolkit.jar -d --no-indent --no-grouping --no-header $FILE -o proto/out.spv.dis

	# Tools
	spirv-dis --raw-id --no-indent --no-header $FILE -o tools/out.spv.dis

	diff --ignore-trailing-space <(sort proto/out.spv.dis) <(sort tools/out.spv.dis)
done
