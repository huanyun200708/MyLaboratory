package cn.com.hq;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestStatic {
	private Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		People p1 = new People();
		People p2 = new People();
		MyThread thread1 = new MyThread(p1);
		MyThread thread2 = new MyThread(p2);
		thread1.start();
		thread2.start();
		synchronized (p1.clock) {
			p1.sayName();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}

class People {
	public static final String clock = "";

	public void sayName() {
		synchronized (clock) {
			System.out.println("name----------------------------");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void sayAge() {
		synchronized (clock) {
			System.out.println("age----------------------------");
		}
	}
}

class MyThread extends Thread {
	private People p = null;

	public MyThread(People p) {
		this.p = p;
	}

	@Override
	public void run() {

		p.sayAge();
	}
}
