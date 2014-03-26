package com.taskpad.dateandtime;

/**
 * InvalidDateException thrown while it is not a valid date format
 * @author Jun
 */
public class InvalidDateException extends Exception{

	/**
	 * generated
	 */
	private static final long serialVersionUID = 8886449578429827179L;
		
	public InvalidDateException(){
		super();
	}
	
	public InvalidDateException(String Message){
		super (Message);
	}

}
