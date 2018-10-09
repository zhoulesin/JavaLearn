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
				System.out.println("��Ʒ��ȫ�����ѣ��ȴ�����������");
				synchronized (list) {
					list.wait();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else {
			System.out.println("���������Ѳ�Ʒ");
			list.remove(0);
			synchronized (list) {
				list.notifyAll();
			}
		}
	}

}
