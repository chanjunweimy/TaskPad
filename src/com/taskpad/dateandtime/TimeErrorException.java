package com.taskpad.dateandtime;

import java.util.logging.Logger;

public class TimeErrorException extends Exception {
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -8922390543512908633L;

	private static final String MESSAGE = "Error: Invalid Time supplied";
	
	private static Logger _logger = Logger.getLogger("TaskPad");
	
	public TimeErrorException(){
		super(MESSAGE);
		
	}
	
	public TimeErrorException(String input){
		super(MESSAGE + ": " + input);
	}
}
