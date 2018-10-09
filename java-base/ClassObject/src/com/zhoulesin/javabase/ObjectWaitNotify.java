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
				System.out.println("main thread �ȴ�t�߳�ִ����");
				t.wait();
				System.out.println("��notify���ѣ����Լ���ִ��");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			System.out.println("�߳�tִ����ӽ��:" + r.getTotal());
		}
	}
	
	static class MyRunnable implements Runnable{
		private int total;

		@Override
		public void run() {
			synchronized(t) {
				System.out.println("Thread name is��"+ Thread.currentThread().getName());
				for (int i = 0; i < 10; i++) {
					total += i;
				}
				t.notify();
				System.out.println("ͬ���������");
			}
			System.out.println("ͬ���������");
		}
		
		public int getTotal() {
			return total;
		}
		
	}
}
