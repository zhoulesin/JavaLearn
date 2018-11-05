package com.zhoulesin.biaoge;

import com.zhoulesin.biaoge.tcpserver.tool.ByteTools;

public class Test {
	public static void main(String[] args) {
		//汉字三个字节
		byte[] printBytes = "123456".getBytes();
		System.out.println(printBytes.length);
		String toHexString = ByteTools.bytesToHexString(printBytes , 1, 5, 1);
		System.out.println(toHexString);
		
		
	}
}
