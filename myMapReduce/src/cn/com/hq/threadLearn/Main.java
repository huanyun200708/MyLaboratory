package cn.com.hq.threadLearn;

public class Main {
	public static void main(String[] args) {
		MyThread t1 = new MyThread();
		t1.start();
		
		System.out.println("子线程ID："+Thread.currentThread().getId());
		MyRunnable mr = new MyRunnable();
		Thread t = new Thread(mr);
		t.start();
		
		
	}
}
