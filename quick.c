int array[10];


int partition(int l, int r) {
   int pivot, i, j, t, dd;
   pivot = array[l];
   i = l;
   j = r+1;
   dd = 1;
   while(dd)
   {
       int aa;
        aa = 1;
        do{
            i = i + 1;
            if( i > r ){
                aa = 0;
            }
            else if( array[i] > pivot){
                aa = 0;
            }
        }
        while(aa);

        do{
            j = j - 1;
        }
        while( array[j] > pivot );

        if( i >= j ){
            dd = 0;
        }
        else{
            t = array[i];
            array[i] = array[j];
            array[j] = t;
        }
   }
   t = array[l];
   array[l] = array[j];
   array[j] = t;
   return j;
}


int quickSort(int l, int r)
{
   int j;

   if( l < r )
   {
       j = partition(l, r);
       quickSort(l, j-1);
       quickSort(j+1, r);
   }

}

int main(){
    int i;
    array[0] = 546;
    array[1] = 35;
    array[2] = 1;
    array[3] = 19;
    array[4] = 69;
    array[5] = 239;
    array[6] = 9;
    array[7] = 469;
    array[8] = 359;
    array[9] = 92;
    scanf(array[8]);
    do{
        printf(array[i]);
        i = i + 1;
    }
    while(i < 10);

    quickSort(0, 9);

    for(i = 0; i < 10 ; i = i + 1){
        printf(array[i]);
    }
    return 0;
}
