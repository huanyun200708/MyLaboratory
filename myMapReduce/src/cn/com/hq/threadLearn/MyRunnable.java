package cn.com.hq.threadLearn;

public class MyRunnable implements Runnable{

	@Override
	public void run() {
		System.out.println("子线程ID："+Thread.currentThread().getId());
		
	}

}
