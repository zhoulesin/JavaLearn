package com.zhoulesin.high.waitnotify;

public class Account {

	public String name;
	public int money;
	//是否有钱
	public boolean flag = false;
	
	public Account() {}
	public Account(String name,int money) {
		this.name = name;
		this.money = money;
	}

	//取钱
	public synchronized void get(int aa) {
		try {
			if(flag) {
				System.out.println(Thread.currentThread().getName()+"取钱:"+aa);	
				money -= aa;
				System.out.println("余额:" + money);
				flag = false;
				this.notifyAll();
			}else {
				this.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//存钱
	public synchronized void put(int bb) {
		try {
			if(flag) {
				this.wait();
			}else {
				System.out.println(Thread.currentThread().getName()+"存钱:" +bb);
				money +=bb;
				System.out.println("余额:" + money);
				flag = true;
				this.notifyAll();
			}
		} catch (Exception e) {
		}
	}
}
