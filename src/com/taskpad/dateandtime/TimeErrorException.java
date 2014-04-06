package com.taskpad.dateandtime;

//@author A0119646X

public class TimeErrorException extends Exception {
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -8922390543512908633L;

	private static final String MESSAGE = "Error: Invalid Time supplied";
		
	public TimeErrorException(){
		super(MESSAGE);
		
	}
	
	public TimeErrorException(String input){
		super(MESSAGE + ": " + input);
	}
}
