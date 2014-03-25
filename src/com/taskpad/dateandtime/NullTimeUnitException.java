package com.taskpad.dateandtime;

/**
 * 
 * @author Jun
 * NullTimeUnitException: an exception thrown when users did not
 * key in time unit
 */
public class NullTimeUnitException extends Exception{
	/**
	 * generated
	 */
	private static final long serialVersionUID = -4075294108323634736L;

	public NullTimeUnitException(){
		super ();
	}
	
	public NullTimeUnitException(String Message){
		super (Message);
	}
}
