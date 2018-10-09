package com.zhoulesin.javabase;

import java.util.List;

public class Producer implements Runnable {

	private List<Product> list;

	public Producer(List<Product> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
	}

	@Override
	public void run() {

		if (list.size() >= 10) {
			try {
				System.out.println("����������ȴ�����������");
				synchronized (list) {
					list.wait();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			list.add(new Product());
			System.out.println("����һ��������");
			synchronized (list) {
				list.notifyAll();
			}
		}
	}

}
