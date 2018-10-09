package com.zhoulesin.javabase;

public class ObjectWaitNotify {
//	private static String a = "11";
	private static Thread t;
	
	public static void main(String[] args) {
		MyRunnable r = new MyRunnable();
		t = new Thread(r);
		t.start();
		
		
		synchronized(t) {
			try {
				System.out.println("main thread 等待t线程执行完");
				t.wait();
				System.out.println("被notify唤醒，得以继续执行");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			System.out.println("线程t执行相加结果:" + r.getTotal());
		}
	}
	
	static class MyRunnable implements Runnable{
		private int total;

		@Override
		public void run() {
			synchronized(t) {
				System.out.println("Thread name is："+ Thread.currentThread().getName());
				for (int i = 0; i < 10; i++) {
					total += i;
				}
				t.notify();
				System.out.println("同步代码块中");
			}
			System.out.println("同步代码块外");
		}
		
		public int getTotal() {
			return total;
		}
		
	}
}
