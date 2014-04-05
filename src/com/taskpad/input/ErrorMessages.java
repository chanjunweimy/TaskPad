package com.taskpad.input;

/** Class for all Input error messages for Input Handling
 * 
 */

//@author A0119646X

public class ErrorMessages {
	
	private static final String MESSAGE_EMPTY_INPUT = "Error: Empty input";
	private static String MESSAGE_TIME_ERROR = "Error: Invalid time format: %s. Time format should be hh:mm or hhmm";
	private static final String MESSAGE_INVALID_CONFIRM_INPUT = "Error: Invalid confirmation. Please enter Y or N";
	private static final String MESSAGE_INVALID_TIME = "Invalid time parameter";
	private static final String MESSAGE_INVALID_TASKID = "Invalid Task ID";
	
	private ErrorMessages(){
	}

	protected static void timeErrorMessage(String input){
		String errorMessage = String.format(MESSAGE_TIME_ERROR, input);
		InputManager.outputToGui(errorMessage);
	}
	
	protected static void invalidTimeMessage(){
		InputManager.outputToGui(MESSAGE_INVALID_TIME);
	}
	
	protected static void emptyInputMessage(){
		InputManager.outputToGui(String.format(MESSAGE_EMPTY_INPUT));
	}
	
	protected static void invalidConfirmationInput(){
		InputManager.outputToGui(MESSAGE_INVALID_CONFIRM_INPUT);
	}
	
	protected static void invalidTaskIDMessage(){
		InputManager.outputToGui(MESSAGE_INVALID_TASKID);
	}

	
}
