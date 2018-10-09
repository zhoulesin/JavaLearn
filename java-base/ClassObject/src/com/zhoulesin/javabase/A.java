package com.zhoulesin.javabase;

public class A {
	public static void main(String[] args) {
		ThreadB b = new ThreadB();
		b.start();
		
		//		System.out.println("b is start...");
		
		synchronized (b) {
			try {
				System.out.println("waiting for b to complete...");
				b.wait();
				System.out.println("completed now back to main thread");
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("total is :" + b.getTotal());
		}
		
		
	}
	
	static class ThreadB extends Thread{
		int total;
		public int getTotal() {
			return total;
		}
		@Override
		public void run() {
			synchronized (this) {
				System.out.println("thread b is running...");
				for (int i = 0; i < 10; i++) {
					total += i;
				}
				notify();
			}
		}
	}
}
