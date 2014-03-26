package com.taskpad.dateandtime;

import java.util.logging.Logger;

/**
 * InvalidDateException thrown while it is not a valid date format
 * @author Jun & Lynnette 
 */
public class InvalidDateException extends Exception{

	/**
	 * generated
	 */
	private static final long serialVersionUID = 8886449578429827179L;
	
	private static final String MESSAGE = "Error: Invalid Date Entered";
	
	private static Logger _logger = Logger.getLogger(MESSAGE);
	
	public InvalidDateException(){
		super(MESSAGE);
		_logger.info(MESSAGE);
	}
	
	public InvalidDateException(String Message){
		super (MESSAGE + ": " + Message);
		_logger.info(MESSAGE);
	}

}
