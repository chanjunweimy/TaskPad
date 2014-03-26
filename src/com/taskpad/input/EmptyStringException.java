package com.taskpad.input;

import java.util.logging.Logger;

public class EmptyStringException extends Exception {
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 1091219745520768583L;
	
	private static final String MESSAGE = "Error: Empty String after command";
	
	private static Logger logger = Logger.getLogger("TaskPad");
	
	public EmptyStringException(){
		super(MESSAGE);
		logger.info(MESSAGE);
	}
}
