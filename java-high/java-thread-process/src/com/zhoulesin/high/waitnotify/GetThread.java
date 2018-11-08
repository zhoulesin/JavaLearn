package com.zhoulesin.high.waitnotify;

public class GetThread implements Runnable{
	private Account account;
	private int aa;
	
	public GetThread(Account account ,int aa) {
		this.account = account;
		this.aa = aa;
	}

	@Override
	public void run() {
		while(true) {
			account.get(aa);
		}
	}

}
