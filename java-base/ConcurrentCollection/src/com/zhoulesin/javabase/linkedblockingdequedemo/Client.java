package com.zhoulesin.javabase.linkedblockingdequedemo;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable{
	
	private LinkedBlockingDeque requestList;
	
	public Client(LinkedBlockingDeque requestList) {
		this.requestList = requestList;
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				StringBuilder request = new StringBuilder();
				request.append(i);
				request.append(":");
				request.append(j);
				try {
					requestList.put(request.toString());
				} catch (Exception e) {
					// TODO: handle exception
				}
				System.out.printf("Client %s at %s.\n",request,new Date());
			}
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.printf("Client End.\n");
	}
	
	

}
