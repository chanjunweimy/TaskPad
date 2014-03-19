package com.taskpad.input;

public class TaskIDException extends Exception {
	
	protected static final String MESSAGE_INVALID_INPUT = "Error: Invalid input: %s";

	public TaskIDException(String message){
		String errorMessage = String.format(MESSAGE_INVALID_INPUT, message);
		InputManager.outputToGui(errorMessage);
	}
	
}
