package com.zhoulesin.base;

public class ReferenceCountingGC {
	private Object instance = null;
	private static final int _1MB = 1024*1024;
	private byte[] bigSize = new byte[2*_1MB];
	
	public static void main(String[] args) {
		ReferenceCountingGC objectA = new ReferenceCountingGC();
		ReferenceCountingGC objectB = new ReferenceCountingGC();
		objectA.instance = objectB;
		objectB.instance = objectA;
		objectA = null;
		objectB = null;
		
		System.gc();
	}
}
