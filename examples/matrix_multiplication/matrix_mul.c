#include <stdio.h>
#include <stdlib.h>
#include <CL/opencl.h>
#include <stdbool.h>

#include "../util.h"

#define ERROR_MARGIN 0.000001f

int main( int argc, char* argv[] )
{
	FILE *f = fopen(argv[1], "rb");
	fseek(f, 0, SEEK_END);
	long fsize = ftell(f);
	fseek(f, 0, SEEK_SET);

	char *string = malloc(fsize + 1);
	fread(string, 1, fsize, f);
	fclose(f);

	string[fsize] = 0;

    // Length of vectors
    unsigned int n = 64;
    unsigned int vec_size = n * n;
 
    // Host input vectors
    float *h_a;
    float *h_b;
    // Host output vector
    float *h_c;
 
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
    size_t bytes = vec_size * sizeof(float);
 
    // Allocate memory for each vector on host
    h_a = (float*)malloc(bytes);
    h_b = (float*)malloc(bytes);
    h_c = (float*)malloc(bytes);
 
    // Initialize vectors on host
    int i;
    for( i = 0; i < vec_size; i++ )
    {
        h_a[i] = 1.0f;
        h_b[i] = 2.0f;
    }
 
    size_t globalSize[2], localSize[2];
    cl_int err;
 
    // Number of work items in each local work group
    localSize[0] = 16;
    localSize[1] = 16;
 
    // Number of total work items - localSize must be devisor
    globalSize[0] = n;
    globalSize[1] = n;
 
    // Bind to platform
    cl_uint num_platforms;
    err = clGetPlatformIDs(1, NULL, &num_platforms);
    cl_platform_id all_platforms[num_platforms];
    err = clGetPlatformIDs(num_platforms, &all_platforms, NULL);
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
    kernel = clCreateKernel(program, "matrix_mul", &err);
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
    err = clEnqueueWriteBuffer(queue, d_a, CL_TRUE, 0,
                                   bytes, h_a, 0, NULL, NULL);
    err |= clEnqueueWriteBuffer(queue, d_b, CL_TRUE, 0,
                                   bytes, h_b, 0, NULL, NULL);
 
    // Set the arguments to our compute kernel
    err  = clSetKernelArg(kernel, 0, sizeof(cl_mem), &d_a);
    err |= clSetKernelArg(kernel, 1, sizeof(cl_mem), &d_b);
    err |= clSetKernelArg(kernel, 2, sizeof(cl_mem), &d_c);
    err |= clSetKernelArg(kernel, 3, sizeof(unsigned int), &n);
 
    // Execute the kernel over the entire range of the data set
    err = clEnqueueNDRangeKernel(queue, kernel, 2, NULL, &globalSize, &localSize,
                                                              0, NULL, NULL);
 
    // Wait for the command queue to get serviced before reading back results
    clFinish(queue);
 
    // Read the results from the device
    clEnqueueReadBuffer(queue, d_c, CL_TRUE, 0, bytes, h_c, 0, NULL, NULL );
 
    //Sum up vector c and print result divided by n, this should equal 1 within error
    bool is_correct = true;
    for(i=0; i < vec_size; i++) {
        if (abs(h_c[i] - 128.0f) > ERROR_MARGIN) {
            is_correct = false;
        }
    }

    printf("final result: %s\n", is_correct ? "correct" : "wrong");
 
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