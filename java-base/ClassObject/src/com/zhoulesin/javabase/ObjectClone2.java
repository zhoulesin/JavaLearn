package com.zhoulesin.javabase;

public class ObjectClone2 {

	public static void main(String[] args) {
		ObjectClone2 oc1 = new ObjectClone2();
		try {
			ObjectClone2 oc2 = (ObjectClone2) oc1.clone();
			System.out.println(oc2);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		//java.lang.CloneNotSupportedException
	}
}
