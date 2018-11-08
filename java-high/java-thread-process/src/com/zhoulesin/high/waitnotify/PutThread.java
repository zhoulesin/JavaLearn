package com.zhoulesin.high.waitnotify;

public class PutThread implements Runnable{
	private Account account;
	private int bb;
	
	public PutThread(Account account, int bb) {
		this.account = account;
		this.bb = bb;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			account.put(bb);
		}
	}

}
