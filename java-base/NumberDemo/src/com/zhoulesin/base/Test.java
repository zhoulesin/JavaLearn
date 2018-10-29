package com.zhoulesin.base;

public class Test {
	public static void main(String[] args) {
//		int a = 65536;
		int a = 16777216;
		
		int count1 = a&0xff;
		System.out.println(count1);
		
		int count2 = a&0xff00;
		System.out.println(count2);
		
		int count3 = a >>16 &0xff;
		System.out.println(count3);
		
		System.out.println(a>>24);
	}
}
