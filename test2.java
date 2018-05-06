class class_B {
	static int attributB1;
	static int attributB2;
	public static void methodB1(){ 
		class_A.attributA2=0;
		class_A.attributA1=0;
		class_A.methodA1();
		 }
	public static void methodB2(){ 
		class_B.attributB1=0;
		class_B.attributB2=0; }
	public static void methodB3(){ 
		attributB1=0;
		methodB1();
		methodB2(); }
 }
