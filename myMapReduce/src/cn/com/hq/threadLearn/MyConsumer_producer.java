package cn.com.hq.threadLearn;

import java.util.ArrayList;
import java.util.List;

public class MyConsumer_producer {
	private List<String> queue = new ArrayList<String>(10);
	public static void main(String[] args) {
		MyConsumer_producer test = new MyConsumer_producer();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();
          
        producer.start();
        consumer.start();
	}
	
	 class Consumer extends Thread{
         
	        @Override
	        public void run() {
	            consume();
	        }
	          
	        private void consume() {
	            while(true){
	                synchronized (queue) {
	                    if(queue.size() == 0){
	                        try {
	                            System.out.println("队列空，等待数据");
	                            queue.wait();
	                        } catch (InterruptedException e) {
	                            e.printStackTrace();
	                            queue.notify();
	                        }
	                    }else{
	                    	System.out.println("从队列取走一个元素:"+queue.get(0));
		                    queue.remove(0);          //每次移走队首元素
		                    queue.notify(); 
		                    System.out.println("向队列中取走一个元素，队列剩余空间："+(queue.size()));
	                    }
	                }
	                try {
                    	Thread.sleep(Math.round(Math.random()*1000)+1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            }
	        }
	    }
	      
	    class Producer extends Thread{
	          
	        @Override
	        public void run() {
	            produce();
	        }
	          
	        private void produce() {
	            while(true){
	                synchronized (queue) {
	                    if(queue.size() == 10){
	                        try {
	                            System.out.println("队列满，等待有空余空间");
	                            queue.wait();
	                        } catch (InterruptedException e) {
	                            e.printStackTrace();
	                            queue.notify();
	                        }
	                    }else{
	                    	String prodactor = "Prodactor_" + Math.round(Math.random()*1000);
		                    System.out.println("从队列插入一个元素:"+prodactor);
		                    queue.add(prodactor);        //每次插入一个元素
		                    queue.notify();
		                    System.out.println("向队列中插入一个元素，队列剩余空间："+(queue.size()));
	                    }
	                }
	                try {
						Thread.sleep(Math.round(Math.random()*1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            }
	        }
	    }

}
