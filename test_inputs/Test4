int myname, yourname, hisname, hername, theirname;
float compiler, database, concurrent, linguistic, algebra;

int add(int a[10], int b[10]){
	int i;
	a[0] = (b[0]+b[1]+b[3]);
	a[1] = b[1]+0.123;
	sub(a[4],b[4]);
	for(i=0;i<10;i=i+1){
		a[i] = a[i] + b[i];
		if(0 < i){
			a[i] = a[i] * b[i-1];
		}
	}
	return a[5]+b[9]-sub(a[3],b[3]-a[1]-a[0]);
}

int sub(int a, int b){
	return a-b;
}

int binarySearch(float L[10000], float value){
	int start,middle,end;
	start = 0;
	end = 10000;
	while(start+1 < end){
		middle = (start+end)/2;
		if(L[middle] < value)
			start = middle;
		else
			end = middle;
	}
	return middle;
}

int reminder(int a, int b){
	return a-(a/b)*b;
}

int iter_step(int start, int iter_count){
	int i,r;
	for(i=0;i<iter_count;i=i+1){
		r = reminder(start,2);
		switch(r){
			case 0:
				start = start/2;
				break;
			default:
				start = start-1;
		}
	}
	return start;
}

int main(){
	return 0;
}



