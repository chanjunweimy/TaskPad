//@author A0112084U

package com.taskpad.dateandtime;

public class InvalidQuotesException extends Exception{
	/**
	 * generated
	 */
	private static final long serialVersionUID = 1973772979261355980L;
	
	private static final String MESSAGE = "Error: Cannot have odd numbers of quotes";
		
	public InvalidQuotesException() {
		super(MESSAGE);
	}

	public InvalidQuotesException(String message) {
		super(MESSAGE + ": " + message);
	}
	
}
