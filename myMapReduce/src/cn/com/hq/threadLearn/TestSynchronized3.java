package cn.com.hq.threadLearn;

import java.util.ArrayList;

public class TestSynchronized3 {
	public static void main(String[] args) {
		TestSynchronized3 test = new TestSynchronized3();
		final InsertData insertData = test.new InsertData();

		new Thread() {
			public void run() {
				insertData.insert1(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				insertData.insert2(Thread.currentThread());
			};
		}.start();
	}
	
	class InsertData {
		private ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
		private ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
		private Object object = new Object();
		
		public void insert1(Thread thread) {
			synchronized (object) {
				for (int i = 0; i < 5; i++) {
					System.out.println(thread.getName() + "在插入arrayList1数据" + i);
					arrayList1.add(i);
				}
			}
		}
		
		public void insert2(Thread thread) {
			synchronized (object) {
				for (int i = 0; i < 5; i++) {
					System.out.println(thread.getName() + "在插入arrayList2数据" + i);
					arrayList2.add(i);
				}
			}
		}
	}
}


