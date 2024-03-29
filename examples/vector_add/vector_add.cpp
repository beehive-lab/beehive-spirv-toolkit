#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <iostream>
#include <string>
#include <cstring>
#include "CL/opencl.h"

using namespace std;

#define CL_TARGET_OPENCL_VERSION 2_1

/**
 * Selects last device with OpenCL 2.1
 */
int get_platform_with_2_1(cl_platform_id *ids, int num_platforms) {
	int deviceNumber = -1;
	for (int i = 0; i < num_platforms; i++) {
		size_t version_length;
		if (clGetPlatformInfo(ids[i], CL_PLATFORM_VERSION, 0, NULL, &version_length) != CL_SUCCESS) {
			puts("ERROR: clGetPlatformInfo failed"); return -1;
		}
		char version[version_length];
		if (clGetPlatformInfo(ids[i], CL_PLATFORM_VERSION, version_length, version, &version_length) != CL_SUCCESS) {
				puts("ERROR: clGetPlatformInfo failed"); return -1;
		}
		if (strlen(version) >= 10 && (strncmp(version, "OpenCL 3.0", 10) == 0)) {
			//return i;
			deviceNumber = i;
		}
	}
	return deviceNumber;
}

int main( int argc, char** argv) {

    if (argc != 2) {
        std::cout << "Profile the SPIRV binary as the first argument\n";
        return -1;
    }

	FILE *f = fopen(argv[1], "rb");
	fseek(f, 0, SEEK_END);
	long fsize = ftell(f);
	fseek(f, 0, SEEK_SET);

	char *string = (char *)malloc(fsize + 1);
	fread(string, 1, fsize, f);
	fclose(f);

	string[fsize] = 0;

    // Length of vectors
    unsigned int n = 100000;
 
    // Host input vectors
    double *h_a;
    double *h_b;
    // Host output vector
    double *h_c;
 
    // Device input buffers
    cl_mem d_a;
    cl_mem d_b;
    // Device output buffer
    cl_mem d_c;
 
    cl_device_id device_id;           // device ID
    cl_context context;               // context
    cl_command_queue queue;           // command queue
    cl_program program;               // program
    cl_kernel kernel;                 // kernel
 
    // Size, in bytes, of each vector
    size_t bytes = n*sizeof(double);
 
    // Allocate memory for each vector on host
    h_a = (double*)malloc(bytes);
    h_b = (double*)malloc(bytes);
    h_c = (double*)malloc(bytes);
 
    // Initialize vectors on host
    for(int i = 0; i < n; i++ ) {
        h_a[i] = sinf(i) * sinf(i);
        h_b[i] = cosf(i) * cosf(i);
    }
 
    size_t globalSize, localSize;
    cl_int err;
 
    // Number of work items in each local work group
    localSize = 64;
 
    // Number of total work items - localSize must be devisor
    globalSize = ceil(n/(float)localSize)*localSize;
 
    // Bind to platform
    cl_uint num_platforms;
    err = clGetPlatformIDs(1, NULL, &num_platforms);
    cl_platform_id all_platforms[num_platforms];
    err = clGetPlatformIDs(num_platforms, all_platforms, NULL);
    int selected_platform_id = get_platform_with_2_1(all_platforms, num_platforms);

    if (selected_platform_id < 0) {
        puts("ERROR: could not find OCL platform with version 2.1 or higher");
        return -1;
    }
 
    // Get ID for the device
    err = clGetDeviceIDs(all_platforms[selected_platform_id], CL_DEVICE_TYPE_ALL, 1, &device_id, NULL);

    //Print name of the device
    size_t name_size;
    err = clGetDeviceInfo(device_id, CL_DEVICE_NAME, 0, NULL, &name_size);
    char name[name_size];
    err = clGetDeviceInfo(device_id, CL_DEVICE_NAME, name_size, name, &name_size);
    printf("%s\n", name);
 
    // Create a context 
    context = clCreateContext(0, 1, &device_id, NULL, NULL, &err);
 
    // Create a command queue
    queue = clCreateCommandQueue(context, device_id, 0, &err);
 
    // Create the compute program from the source buffer
    program = clCreateProgramWithIL(context, string, fsize, &err);
    if (err != CL_SUCCESS) {
        printf("Failed to create program (%d)\n", err);
    }
    else {
        printf("Successfully created program (%d)\n", err);
    }
 
    // Build the program executable
    err = clBuildProgram(program, 0, NULL, NULL, NULL, NULL);
    if (err != CL_SUCCESS) {
        printf("Failed to build program (%d)\n", err);
    }
    else {
        printf("Successfully build program (%d)\n", err);
    }
 
    // Create the compute kernel in the program we wish to run
    kernel = clCreateKernel(program, "vecAdd", &err);
    if (err != CL_SUCCESS) {
        printf("Failed to create kernel (%d)\n", err);
    }
    else {
        printf("Successfully created kernel (%d)\n", err);
    }
 
    // Create the input and output arrays in device memory for our calculation
    d_a = clCreateBuffer(context, CL_MEM_READ_ONLY, bytes, NULL, NULL);
    d_b = clCreateBuffer(context, CL_MEM_READ_ONLY, bytes, NULL, NULL);
    d_c = clCreateBuffer(context, CL_MEM_WRITE_ONLY, bytes, NULL, NULL);
 
    // Write our data set into the input array in device memory
    err = clEnqueueWriteBuffer(queue, d_a, CL_TRUE, 0, bytes, h_a, 0, NULL, NULL);
    err |= clEnqueueWriteBuffer(queue, d_b, CL_TRUE, 0, bytes, h_b, 0, NULL, NULL);
 
    // Set the arguments to our compute kernel
    err  = clSetKernelArg(kernel, 0, sizeof(cl_mem), &d_a);
    err |= clSetKernelArg(kernel, 1, sizeof(cl_mem), &d_b);
    err |= clSetKernelArg(kernel, 2, sizeof(cl_mem), &d_c);
    err |= clSetKernelArg(kernel, 3, sizeof(unsigned int), &n);
 
    // Execute the kernel over the entire range of the data set 
    err = clEnqueueNDRangeKernel(queue, kernel, 1, NULL, &globalSize, &localSize, 0, NULL, NULL);
 
    // Read the results from the device
    clEnqueueReadBuffer(queue, d_c, CL_TRUE, 0, bytes, h_c, 0, NULL, NULL );
 
    //Sum up vector c and print result divided by n, this should equal 1 within error
    double sum = 0;
    for(int i=0; i < n; i++) {
        sum += h_c[i];
    }   
    printf("final result: %f\n", sum/n);
 
    // release OpenCL resources
    clReleaseMemObject(d_a);
    clReleaseMemObject(d_b);
    clReleaseMemObject(d_c);
    clReleaseProgram(program);
    clReleaseKernel(kernel);
    clReleaseCommandQueue(queue);
    clReleaseContext(context);
 
    //release host memory
    free(h_a);
    free(h_b);
    free(h_c);
 
    return 0;
}
