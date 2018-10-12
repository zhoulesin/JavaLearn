package com.zhoulesin.javabase.delayedqueuedemo;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Event implements Delayed{
	
	private Date startDate;
	
	public Event(Date startDate) {
		super();
		this.startDate = startDate;
	}

	@Override
	public int compareTo(Delayed o) {
		long result = this.getDelay(TimeUnit.MILLISECONDS)
				- o.getDelay(TimeUnit.MILLISECONDS);
		if (result<0) {
			return -1;
		}else if (result > 0) {
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public long getDelay(TimeUnit unit) {
		Date now = new Date();
		long diff = startDate.getTime() - now.getTime();
		return unit.convert(diff, TimeUnit.MILLISECONDS);
	}

}
