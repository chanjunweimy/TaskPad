package com.taskpad.dateandtime;

import java.util.logging.Logger;

public class DatePassedException extends Exception {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 7516238062049393549L;

	private static final String MESSAGE = "Date has passed. Please enter a date in the future";
	
	protected static Logger _logger = Logger.getLogger("TaskPad");
	
	public DatePassedException(){
		super(MESSAGE);
		_logger.info(MESSAGE);
	}
	
}
