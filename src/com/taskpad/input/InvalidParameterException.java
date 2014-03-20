package com.taskpad.input;

public class InvalidParameterException extends Exception {

	protected static final String MESSAGE_INVALID_PARAMETER_NUMBER = "Error: Invalid number of parameters.\nType help if you need! :)";
	
	public InvalidParameterException(String[] inputString) {
		String errorMessage = String.format(MESSAGE_INVALID_PARAMETER_NUMBER);
		InputManager.outputToGui(errorMessage);
	}

}
