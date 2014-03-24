package com.taskpad.dateandtime;

/**
 * 
 * @author Jun
 * NullTimeValueException: an exception thrown
 * when user did not key in a time value
 */
public class NullTimeValueException extends Exception{
	/**
	 * generated
	 */
	private static final long serialVersionUID = 1045316937520580318L;

	public NullTimeValueException(){
		super ();
	}
	
	public NullTimeValueException(String Message){
		super (Message);
	}
}
