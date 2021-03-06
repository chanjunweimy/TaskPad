//@author A0112084U

package com.taskpad.dateandtime;


public class InvalidDayException extends Exception{
	/**
	 * generated
	 */
	private static final long serialVersionUID = 2392793659585047867L;	
	
	private static final String MESSAGE = "Error: Invalid Day";
		
	public InvalidDayException() {
		super(MESSAGE);
	}

	public InvalidDayException(String message) {
		super(MESSAGE + ": " + message);
	}
	
}
