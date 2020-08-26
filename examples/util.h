#include <CL/cl.h>

int get_platform_with_2_1(cl_platform_id *ids, int num_platforms) {
	for (int i = 0; i < num_platforms; i++) {
		size_t version_length;
		if (clGetPlatformInfo(ids[i], CL_PLATFORM_VERSION, 0, NULL, &version_length) != CL_SUCCESS) {puts("ERROR: clGetPlatformInfo failed"); return -1;}
		char version[version_length];
		if (clGetPlatformInfo(ids[i], CL_PLATFORM_VERSION, version_length, version, &version_length) != CL_SUCCESS) {puts("ERROR: clGetPlatformInfo failed"); return -1;}
		if (strlen(version) >= 10 && (strncmp(version, "OpenCL 2.1", 10) == 0)) {
			return i;
		}
	}
	return -1;
}