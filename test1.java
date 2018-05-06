class class_A {
	static int attributA1;
	static int attributA2;

	public static void methodA1() { attributA1=0;
		methodA2(); }
	public static void methodA2() { attributA2=0;
		attributA1=0; }
	public	static void methodA3() { attributA1=0;
		attributA2=0;
		methodA1();
		methodA2(); }
 	}
