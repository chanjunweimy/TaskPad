package com.taskpad.dateandtime;

import java.util.logging.Logger;

public class InvalidDayException extends Exception{
	/**
	 * generated
	 */
	private static final long serialVersionUID = 2392793659585047867L;	
	
	private static final String MESSAGE = "Error: Invalid Day";
	
	private static Logger _logger = Logger.getLogger("TaskPad");
	
	public InvalidDayException() {
		super(MESSAGE);
		_logger.info(MESSAGE);
	}

	public InvalidDayException(String message) {
		super(MESSAGE + ": " + message);
		_logger.info(MESSAGE);
	}
	
}
