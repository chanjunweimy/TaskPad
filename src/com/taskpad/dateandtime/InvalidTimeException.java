package com.taskpad.dateandtime;

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

	public InvalidTimeException(String message){
		super(message);
	}
	
}
