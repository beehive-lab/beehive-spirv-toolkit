__kernel void matrix_mul(__global float A[], __global float B[], __global float C[], unsigned int size) {
	size_t idX = get_global_id(0);
	size_t strideX = get_global_size(0);

	size_t idY = get_global_id(1);
	size_t strideY = get_global_size(1);

	for (int i = idX; i < size; i += strideX) {
		for (int j = idY; j < size; j += strideY) {
			float sum = 0.0f;
			for (int k = 0; k < size; k++) {
				sum += A[(i * size) + k] * B[(k * size) + j];
			}
			C[(i * size) + j] = sum;
		}
	}
}