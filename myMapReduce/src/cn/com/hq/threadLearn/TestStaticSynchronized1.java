package cn.com.hq.threadLearn;

import java.util.ArrayList;
/**
 * 并且如果一个线程执行一个对象的非static synchronized方法，另外一个线程需要执行这个对象所属类的static synchronized方法，此时不会发生互斥现象，
 * 因为访问static synchronized方法占用的是类锁，而访问非static synchronized方法占用的是对象锁，所以不存在互斥现象。
 * 
 * */
public class TestStaticSynchronized1 {
	public static void main(String[] args) {
		final StaticInsertData insertData = new StaticInsertData();

		new Thread() {
			public void run() {
				insertData.insert1(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				StaticInsertData.insert2(Thread.currentThread());
			};
		}.start();
	}
	
	
}

class StaticInsertData {
	private ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
	
	public synchronized void insert1(Thread thread) {
		for (int i = 0; i < 5; i++) {
			System.out.println(thread.getName() + "在插入arrayList1数据" + i);
			arrayList1.add(i);
		}
	}
	
	public synchronized static void insert2(Thread thread) {
		for (int i = 0; i < 5; i++) {
			System.out.println(thread.getName() + "在插入arrayList2数据" + i);
		}
	}
}


