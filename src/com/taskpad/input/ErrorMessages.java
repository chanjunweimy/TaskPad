package com.taskpad.input;

/** Class for all Input error messages for Input Handling
 * 
 * @author Lynnette
 *
 */

public class ErrorMessages {
	
	private static String MESSAGE_TIME_ERROR = "Error: Invalid time format: %s. Time format should be hh:mm or hhmm";

	protected static String timeErrorMessage(String input){
		String errorMessage = String.format(MESSAGE_TIME_ERROR, input);
		return errorMessage;
	}
	
}
