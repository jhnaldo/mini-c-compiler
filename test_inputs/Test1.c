int global_a;
float global_b,global_c;
int global_d[5];

int test() {
    float local_3;
    int local_1, local_2;

    local_3 = 1 * 23.22 + 11 - 0.1123 / 2;
    global_d[0] = 1+2;

    switch (global_a) {
        case 1:
            a=2;
        case 2:
            a=3;
            break;
        default:
            return 1;
    }

    while (a) {
        test2();
    }

    for(i = 0; i < 10 ; i = i + 1) {
        d[i] = i;
    }

    if(1)
        if(2)
            a = 2 * a;
        else
            a = 3 * a;

    {
        ;
    }
}

float test2() {
    return 3.14;
}
