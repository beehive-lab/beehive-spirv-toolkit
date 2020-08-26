__kernel void matrix_mul(__global float A[], __global float B[], __global float C[], unsigned int size) {
	size_t idX = get_global_id(0);

	size_t idY = get_global_id(1);

	float sum = 0.0f;
	for (int k = 0; k < size; k++) {
		sum += A[(idX * size) + k] * B[(k * size) + idY];
	}
	C[(idX * size) + idY] = sum;
}