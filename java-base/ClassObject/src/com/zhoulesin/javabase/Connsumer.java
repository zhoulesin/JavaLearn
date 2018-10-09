package com.zhoulesin.javabase;

import java.util.List;

public class Connsumer implements Runnable{
	
	private List<Product> list;
	
	public Connsumer(List<Product> list) {
		this.list = list;
	}

	@Override
	public void run() {
		if(list.size() == 0) {
			try {
				System.out.println("产品被全部消费，等待生产者生产");
				synchronized (list) {
					list.wait();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else {
			System.out.println("消费者消费产品");
			list.remove(0);
			synchronized (list) {
				list.notifyAll();
			}
		}
	}

}
