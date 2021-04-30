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
			return i;
			//deviceNumber = i;
		}
	}
	return deviceNumber;
}

int main( int argc, char** argv) {

	FILE *f = fopen(argv[1], "rb");
	fseek(f, 0, SEEK_END);
	long fsize = ftell(f);
	fseek(f, 0, SEEK_SET);

	char *string = (char *)malloc(fsize + 1);
	fread(string, 1, fsize, f);
	fclose(f);

	string[fsize] = 0;

    // Length of vectors
    unsigned int n = 1024;
 
    // Host input vectors
    int *h_a;
    int *h_b;
 
    // Device input buffers
    cl_mem d_a;
    cl_mem d_b;
 
    cl_device_id device_id;           // device ID
    cl_context context;               // context
    cl_command_queue queue;           // command queue
    cl_program program;               // program
    cl_kernel kernel;                 // kernel
 
    // Size, in bytes, of each vector
    size_t bytes = n*sizeof(int);
 
    // Allocate memory for each vector on host
    h_a = (int*)malloc(bytes);
    h_b = (int*)malloc(bytes);
 
    // Initialize vectors on host
    for(int i = 0; i < n; i++) {
        h_b[i] = 120;
    }
 
    size_t globalSize, localSize;
    cl_int err;
 
 
    // Number of total work items - localSize must be devisor
    globalSize = n;
 
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
    kernel = clCreateKernel(program, "testVectorCopy", &err);
    if (err != CL_SUCCESS) {
        printf("Failed to create kernel (%d)\n", err);
    }
    else {
        printf("Successfully created kernel (%d)\n", err);
    }
 
    // Create the input and output arrays in device memory for our calculation
    d_a = clCreateBuffer(context, CL_MEM_WRITE_ONLY, bytes, NULL, NULL);
    d_b = clCreateBuffer(context, CL_MEM_READ_ONLY, bytes, NULL, NULL);
 
    // Write our data set into the input array in device memory
    err = clEnqueueWriteBuffer(queue, d_b, CL_TRUE, 0, bytes, h_b, 0, NULL, NULL);
 
    // Set the arguments to our compute kernel
    err  = clSetKernelArg(kernel, 0, sizeof(cl_mem), &d_a);
    err  = clSetKernelArg(kernel, 1, sizeof(cl_mem), &d_b);
 
    // Execute the kernel over the entire range of the data set 
    err = clEnqueueNDRangeKernel(queue, kernel, 1, NULL, &globalSize, NULL, 0, NULL, NULL);
 
    // Wait for the command queue to get serviced before reading back results
    clFinish(queue);
 
    // Read the results from the device
    clEnqueueReadBuffer(queue, d_a, CL_TRUE, 0, bytes, h_a, 0, NULL, NULL );

    bool isCorrect = true;
    for (int i = 0; i < n; i++) {
        if (h_a[i] != 120) {
            std::cout << "ERROR - result not correct" << std::endl;
            isCorrect = false;
            break;
        }
    }

    if (isCorrect) {
        std::cout << "Result is correct" << std::endl;
    }

    // release OpenCL resources
    clReleaseMemObject(d_a);
    clReleaseProgram(program);
    clReleaseKernel(kernel);
    clReleaseCommandQueue(queue);
    clReleaseContext(context);
 
    free(h_a);
 
    return 0;
}
