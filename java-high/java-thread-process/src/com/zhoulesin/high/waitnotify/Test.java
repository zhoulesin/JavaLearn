package com.zhoulesin.high.waitnotify;

public class Test {
	public static void main(String[] args) {
		Account account = new Account();
		new Thread(new GetThread(account, 400)).start();
		new Thread(new PutThread(account, 200)).start();
//		new Thread(new PutThread(account, 300)).start();
	}
}
