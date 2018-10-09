package com.zhoulesin.javabase;

public class ObjectEquals {
	public static void main(String[] args) {
		User u1 = new User();
		u1.setUid(111);
		u1.setName("asd");
		
		User u2 = new User();
		u2.setUid(111);
		u2.setName("dsa");
		
		System.out.println(u1.equals(u2));
		
//		true
	}
}
