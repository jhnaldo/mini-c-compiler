int magic_square(){
    int array[2500];
    int num;
    int i,j;
    int new_i,new_j;
    int n;
    scanf(n);
    if((n - (n / 2) * 2) == 0){
        printf(n);
        return 1;
    }
    for(i = 0; i < n ; i = i + 1){
        for(j = 0; j < n ; j = j + 1){
            array[i * n + j] = 0;
        }
    }

    num = 1;
    i = 0;
    j = n / 2;

    while(num <= n * n){
        array[i * n + j]=num;
        new_i = i - 1;
        new_j = j + 1;
        if(new_i < 0 ) new_i= n - 1;
        if(new_j > n - 1) new_j = 0;
 
        if(array[new_i * n + new_j] != 0){
            i = i + 1;
        }
        else{
            i = new_i;
            j = new_j;
        }
        num = num + 1;
    }
 
    for(i=0; i<n; i = i + 1){
        for(j=0; j<n; j = j + 1){
            printf(array[i * n + j]);
        }
    }
}
 
int main(){
    magic_square();
    return 0;
}
