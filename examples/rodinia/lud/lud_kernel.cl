// #define 16 16
__kernel void 
lud_diagonal(__global float *m, 
			 __local  float *shadow,
			 int   matrix_dim, 
			 int   offset)
{ 
	int i,j;
	int tx = get_local_id(0);

	int array_offset = offset*matrix_dim+offset;
	for(i=0; i < 16; i++){
		shadow[i * 16 + tx]=m[array_offset + tx];
		array_offset += matrix_dim;
	}
  
	barrier(CLK_LOCAL_MEM_FENCE);
  
	for(i=0; i < 16-1; i++) {

    if (tx>i){
      for(j=0; j < i; j++)
        shadow[tx * 16 + i] -= shadow[tx * 16 + j] * shadow[j * 16 + i];
		shadow[tx * 16 + i] /= shadow[i * 16 + i];
    }

	barrier(CLK_LOCAL_MEM_FENCE);
    if (tx>i){

      for(j=0; j < i+1; j++)
        shadow[(i+1) * 16 + tx] -= shadow[(i+1) * 16 + j]*shadow[j * 16 + tx];
    }
    
	barrier(CLK_LOCAL_MEM_FENCE);
    }

    array_offset = (offset+1)*matrix_dim+offset;
    for(i=1; i < 16; i++){
      m[array_offset+tx]=shadow[i * 16 + tx];
      array_offset += matrix_dim;
    }
  
}

__kernel void
lud_perimeter(__global float *m, 
			  __local  float *dia,
			  __local  float *peri_row,
			  __local  float *peri_col,
			  int matrix_dim, 
			  int offset)
{
    int i,j, array_offset;
    int idx;

    int  bx = get_group_id(0);	
    int  tx = get_local_id(0);

    if (tx < 16) {
      idx = tx;
      array_offset = offset*matrix_dim+offset;
      for (i=0; i < 16/2; i++){
      dia[i * 16 + idx]=m[array_offset+idx];
      array_offset += matrix_dim;
      }
    
    array_offset = offset*matrix_dim+offset;
    for (i=0; i < 16; i++) {
      peri_row[i * 16+ idx]=m[array_offset+(bx+1)*16+idx];
      array_offset += matrix_dim;
    }

    } else {
    idx = tx-16;
    
    array_offset = (offset+16/2)*matrix_dim+offset;
    for (i=16/2; i < 16; i++){
      dia[i * 16 + idx]=m[array_offset+idx];
      array_offset += matrix_dim;
    }
    
    array_offset = (offset+(bx+1)*16)*matrix_dim+offset;
    for (i=0; i < 16; i++) {
      peri_col[i * 16 + idx] = m[array_offset+idx];
      array_offset += matrix_dim;
    }
  
   }
    barrier(CLK_LOCAL_MEM_FENCE);

    if (tx < 16) { //peri-row
     idx=tx;
      for(i=1; i < 16; i++){
      for (j=0; j < i; j++)
        peri_row[i * 16 + idx]-=dia[i * 16+ j]*peri_row[j * 16 + idx];
    }
    } else { //peri-col
     idx=tx - 16;
     for(i=0; i < 16; i++){
      for(j=0; j < i; j++)
        peri_col[idx * 16 + i]-=peri_col[idx * 16+ j]*dia[j * 16 + i];
      peri_col[idx * 16 + i] /= dia[i * 16+ i];
     }
   }

	barrier(CLK_LOCAL_MEM_FENCE);
    
  if (tx < 16) { //peri-row
    idx=tx;
    array_offset = (offset+1)*matrix_dim+offset;
    for(i=1; i < 16; i++){
      m[array_offset+(bx+1)*16+idx] = peri_row[i*16+idx];
      array_offset += matrix_dim;
    }
  } else { //peri-col
    idx=tx - 16;
    array_offset = (offset+(bx+1)*16)*matrix_dim+offset;
    for(i=0; i < 16; i++){
      m[array_offset+idx] =  peri_col[i*16+idx];
      array_offset += matrix_dim;
    }
  }

}

__kernel void
lud_internal(__global float *m, 
			 __local  float *peri_row,
			 __local  float *peri_col,
			int matrix_dim, 
			int offset)
{
  
  int  bx = get_group_id(0);	
  int  by = get_group_id(1);	
  
  int  tx = get_local_id(0);
  int  ty = get_local_id(1);

  int i;
  float sum;

  int global_row_id = offset + (by+1)*16;
  int global_col_id = offset + (bx+1)*16;

  peri_row[ty * 16 + tx] = m[(offset+ty)*matrix_dim+global_col_id+tx];
  peri_col[ty * 16 + tx] = m[(global_row_id+ty)*matrix_dim+offset+tx];

  barrier(CLK_LOCAL_MEM_FENCE);

  sum = 0;
  for (i=0; i < 16; i++)
    sum += peri_col[ty * 16 + i] * peri_row[i * 16 + tx];
  m[(global_row_id+ty)*matrix_dim+global_col_id+tx] -= sum;


}





