package com.taskpad.execute;

public class CommandFactoryStub extends CommandFactory{
	protected static void outputToGui(String feedback) {
		System.out.println(feedback);
	}
}
