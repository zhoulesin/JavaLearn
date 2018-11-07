package com.zhoulesin.state2;

public class SoldState implements State{
	GumballMachine gumballMachine;
	
	public SoldState(GumballMachine gumballMachine) {
		this.gumballMachine = gumballMachine;
	}

	@Override
	public void insertQuarter() {
		System.out.println("please wait,we're already giving you a gumball");
	}

	@Override
	public void ejectQuarter() {
		System.out.println("sorry,you already turned the crank");
	}

	@Override
	public void turnCrank() {
		System.out.println("rurning twice doesn't get you another gumball");
	}

	@Override
	public void dispense() {
		
	}

}
