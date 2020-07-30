#!/bin/sh

FILES=${1:-$(find ../rodinia -type f -name "*.spv")}

# Generate files
echo "Differences:"
for FILE in $FILES; do
	echo "\t" $FILE
	
	# Prototype
	java -jar ../../dist/spirv-proto.jar -d --no-indent --no-grouping --no-header $FILE -o proto/out.spv.dis

	# Tools
	spirv-dis --raw-id --no-indent --no-header $FILE -o tools/out.spv.dis

	diff proto/out.spv.dis tools/out.spv.dis
done