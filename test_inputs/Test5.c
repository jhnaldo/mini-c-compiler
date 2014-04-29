int a, b , i;
float test;

int function(int a , int b)
{
	return a + b;
}

int main()
{
    b = 2 - 1;
    
    a = -1 + 5;
    
    test = 0.09;
    
	for(a = 0 ; a < 12 ; a = a + 1)
		b = b+1;
		
	function(a,b);
	
	switch(b)
	{
		case 0:
		case 1:
	 		break;
		case 2:
	 		if(a == b)
	 			return -1;
		case 3:
	 		for(i = 0 ; i < 9 ; i = i+ 1)
	 			function(i,b);
	 		break;
	 	case 4:
	 		if(a == b)
	 			return 1;
	 		else
	 			return 2;
	 	default:
	 		break;
	}
	
	return 0;
}