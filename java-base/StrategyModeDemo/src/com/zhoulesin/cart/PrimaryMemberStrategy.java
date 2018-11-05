package com.zhoulesin.cart;

public class PrimaryMemberStrategy implements MemberStrategy {

	@Override
	public double calcPrice(double booksPrice) {
		System.out.println("初几会员没有折扣");
		return booksPrice;
	}

}
