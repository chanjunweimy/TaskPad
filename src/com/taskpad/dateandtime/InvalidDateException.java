package com.taskpad.dateandtime;


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
	
	public InvalidDateException(){
		super(MESSAGE);
	}
	
	public InvalidDateException(String Message){
		super (MESSAGE + ": " + Message);
	}

}
