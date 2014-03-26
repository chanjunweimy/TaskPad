package com.taskpad.dateandtime;

import java.util.logging.Logger;

import com.taskpad.ui.GuiManager;

/**
 * InvalidDateException thrown while it is not a valid date format
 * @author Jun
 */
public class InvalidDateException extends Exception{

	/**
	 * generated
	 */
	private static final long serialVersionUID = 8886449578429827179L;
	
	private static Logger _logger = Logger.getLogger("TaskPad");
	
	public InvalidDateException(){
		super();
		GuiManager.callOutput("Not a supported date format");
		_logger.info("Not a supported date format");
		//super ();
	}
	
	public InvalidDateException(String Message){
		super (Message);
	}

}
