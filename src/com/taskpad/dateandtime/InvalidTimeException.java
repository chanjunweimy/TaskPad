package com.taskpad.dateandtime;

import java.util.logging.Logger;

/**
 * InvalidTimeException when user keys in AM string >12 or PM string > 25 or minutes >60
 * @author Lynnette
 *
 */

public class InvalidTimeException extends Exception {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -1003877340664378926L;
	
	private static final String MESSAGE = "Error: Invalid time entered";

	private static Logger _logger = Logger.getLogger("TaskPad");
	
	public InvalidTimeException(String message){
		super(MESSAGE + ": " + message);
		_logger.info(MESSAGE + ": " + message); 
	}
	
}
