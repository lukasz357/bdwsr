package main;

public class Controller {

	private static Thread t0;
	private static Thread t1;
	private static Thread t2;
	private static Thread t3;
	private static Thread t4;
	private static Thread t5;
	private static Thread t6;
	private static Thread t7;
	
	public static void startThreads(int pr0, int pr1, int pr2, int pr3, int pr4, int pr5, int pr6, int pr7) {
		Runnable r0 = new MyThread(pr0, 0);
		Runnable r1 = new MyThread(pr1, 1);
		Runnable r2 = new MyThread(pr2, 2);
		Runnable r3 = new MyThread(pr3, 3);
		Runnable r4 = new MyThread(pr4, 4);
		Runnable r5 = new MyThread(pr5, 5);
		Runnable r6 = new MyThread(pr6, 6);
		Runnable r7 = new MyThread(pr7, 7);
		
		t0 = new java.lang.Thread(r0);
		t1 = new java.lang.Thread(r1);
		t2 = new java.lang.Thread(r2);
		t3 = new java.lang.Thread(r3);
		t4 = new java.lang.Thread(r4);
		t5 = new java.lang.Thread(r5);
		t6 = new java.lang.Thread(r6);
		t7 = new java.lang.Thread(r7);
		
		t0.start();
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		
		
	}
	
	public static void stopThreads() {
		t0.interrupt();
		t1.interrupt();
		t2.interrupt();
		t3.interrupt();
		t4.interrupt();
		t5.interrupt();
		t6.interrupt();
		t7.interrupt();
	}
	
}
