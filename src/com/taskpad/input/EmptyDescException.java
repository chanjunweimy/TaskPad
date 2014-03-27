package com.taskpad.input;

import java.util.logging.Logger;

public class EmptyDescException extends Exception {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 886846093768153430L;

	private static final String MESSAGE = "Error: Please enter a description";
	private static Logger logger = Logger.getLogger("TaskPad");

	
	public EmptyDescException(){
		super(MESSAGE);
		logger.info(MESSAGE);
	}
	
}
