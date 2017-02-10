package cn.com.hq.threadLearn;

import java.util.ArrayList;
/**
 * 在类的静态方法或者是静态变量上加锁，都是获取的这个类的类锁
 * */
public class TestStaticSynchronized2 {
	public static void main(String[] args) {
		final StaticInsertData1 insertData = new StaticInsertData1();
		final StaticInsertData1 insertData2 = new StaticInsertData1();
		
		new Thread() {
			public void run() {
				insertData.insert1(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				insertData2.insert2(Thread.currentThread());
			};
		}.start();
	}
	
	
}

class StaticInsertData1 {
	private static Object object = new Object();
	private Object object1 = new Object();
	
	public void insert1(Thread thread) {
		synchronized(object){
			for (int i = 0; i < 5; i++) {
				System.out.println(thread.getName() + "在插入arrayList1数据" + i);
			}
		}
	}
	
	public static void insert2(Thread thread) {
		synchronized(object){
			for (int i = 0; i < 5; i++) {
				System.out.println(thread.getName() + "在插入arrayList1数据" + i);
			}
		}
	}
}


