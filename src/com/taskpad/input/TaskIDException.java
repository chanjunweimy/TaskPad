package com.taskpad.input;

import java.util.logging.Logger;

/**
 * Exception for when TaskID larger than number of tasks in storage
 *
 */
//@author A0119646X


public class TaskIDException extends Exception {
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -1929853963909529411L;
	private static final String MESSAGE = "Error: Invalid TaskID";
	
	private static Logger logger = Logger.getLogger("TaskPad");
	
	public TaskIDException(){
		super(MESSAGE);
		logger.info(MESSAGE);
	}
	
	public TaskIDException(String message){
		super(MESSAGE + ": " + message);
		logger.info(MESSAGE);
	}
	
}
