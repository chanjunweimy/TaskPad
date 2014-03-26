package com.taskpad.dateandtime;

import java.util.logging.Logger;

/**
 * 
 * @author Jun & Lynnette
 * NullTimeUnitException: an exception thrown when users did not
 * key in time unit
 */
public class NullTimeUnitException extends Exception{
	/**
	 * generated
	 */
	private static final long serialVersionUID = -4075294108323634736L;
	
	private static final String MESSAGE = "Error: Please enter a time unit";
	
	protected static Logger _logger = Logger.getLogger("TaskPad");
	
	public NullTimeUnitException(){
		super(MESSAGE);
		_logger.info(MESSAGE);
	}
	
	public NullTimeUnitException(String Message){
		super (MESSAGE);
		_logger.info(MESSAGE);
	}
}
