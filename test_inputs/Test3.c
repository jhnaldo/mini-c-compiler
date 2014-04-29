int SIZE;
int MAX_ITER;

float multiple_real(float A[2], float B[2]){
	return A[0]*B[0]-A[1]*B[1];
}

float multiple_image(float A[2], float B[2]){
	return A[0]*B[1]+A[1]*B[0];
}

float absolute2(float X[2]){
	return X[0]*X[0]+X[1]*X[1];
}

int many_depth(float X[2]){
	int i;
	float start[2],next_value[2];

	start[0] = 0;
	start[1] = 0;
	for(i=0;i<MAX_ITER;i=i+1){
		next_value[0] = multiple_real(start,start)+X[0];
		next_value[1] = multiple_image(start,start)+X[1];
		if(absolute2(next_value) >= 4.56789){
			return i;
		}
	}
	return MAX_ITER;
}

int show_value(float X){
	return 0;
}

float dividing_point(float X, float Y, int n){
	return (X*(SIZE-n)+Y*n)/SIZE;
}

int main(){
	float topleft[2], buttomright[2];
	int i,j;
	float current[2];

	SIZE = 400;
	MAX_ITER = 1024;

	for(i=0;i<SIZE;i=i+1){
		for(j=0;j<SIZE;j=j+1){
			current[0] = dividing_point(topleft[0],buttomright[0],i);
			current[1] = dividing_point(topleft[1],buttomright[1],j);
			show_value( many_depth(current) );
		}
	}
}


