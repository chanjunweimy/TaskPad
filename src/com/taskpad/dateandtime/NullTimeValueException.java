package com.taskpad.dateandtime;

import java.util.logging.Logger;

/**
 * 
 * @author Jun & Lynnette
 * NullTimeValueException: an exception thrown
 * when user did not key in a time value
 */
public class NullTimeValueException extends Exception{
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 1045316937520580318L;
	
	private static final String MESSAGE = "Error: Please enter a time value";
	
	protected static Logger _logger = Logger.getLogger("TaskPad");


	public NullTimeValueException(){
		super (MESSAGE);
		_logger.info(MESSAGE);
	}
	
	public NullTimeValueException(String Message){
		super (MESSAGE);
		_logger.info(MESSAGE);
	}
}
