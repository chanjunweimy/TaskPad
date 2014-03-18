package com.taskpad.input;

public class EmptyStringException extends Exception {
	
	protected static final String MESSAGE_EMPTY_INPUT = "Error: Empty Input";
	
	public EmptyStringException(){
		super();
		InputManager.outputToGui(MESSAGE_EMPTY_INPUT);
	}
}
