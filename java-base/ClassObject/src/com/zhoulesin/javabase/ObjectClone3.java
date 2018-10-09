package com.zhoulesin.javabase;

public class ObjectClone3 implements Cloneable{
	public static void main(String[] args) {
		ObjectClone3 oc1 = new ObjectClone3();
		
		try {
			ObjectClone3 oc2 = (ObjectClone3) oc1.clone();
			System.out.println(oc2);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//com.zhoulesin.javabase.ObjectClone3@15db9742
	}
}
