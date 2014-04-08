//@author A0119646X

package com.taskpad.input;



import java.util.logging.Logger;

public class InvalidParameterException extends Exception {
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -5161609461075304008L;
	private static final String MESSAGE = "Error: Invalid Number of Parameters. Type Help if you need! :) ";
	
	private static Logger logger = Logger.getLogger("TaskPad");

	public InvalidParameterException(String message) {
		super(MESSAGE);
		logger.info(MESSAGE);
	}
	
	public InvalidParameterException(){
		super(MESSAGE);
		logger.info(MESSAGE);
	}

}
