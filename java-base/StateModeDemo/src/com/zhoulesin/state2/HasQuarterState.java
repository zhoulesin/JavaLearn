package com.zhoulesin.state2;

public class HasQuarterState implements State{
	GumballMachine gumballMachine;
	
	public HasQuarterState(GumballMachine gumballMachine) {
		this.gumballMachine = gumballMachine;
	}

	@Override
	public void insertQuarter() {
		System.out.println("you can't insert another quarter");
	}

	@Override
	public void ejectQuarter() {
		System.out.println("quarter returned");
		gumballMachine.setState(gumballMachine.getNoQuarterState());
	}

	@Override
	public void turnCrank() {
		System.out.println("you turned...");
		gumballMachine.setState(gumballMachine.getSoldState());
	}

	@Override
	public void dispense() {
		System.out.println("no gumball dispensed");
	}

}
