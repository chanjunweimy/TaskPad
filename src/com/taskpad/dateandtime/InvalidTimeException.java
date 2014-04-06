//@author A0119646X

package com.taskpad.dateandtime;

/**
 * InvalidTimeException when user keys in AM string >12 or PM string > 25 or minutes >60
 *
 */

public class InvalidTimeException extends Exception {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -1003877340664378926L;
	
	private static final String MESSAGE = "Error: Invalid time entered";
	
	public InvalidTimeException(){
		super(MESSAGE);
	}
	
	public InvalidTimeException(String message){
		super(MESSAGE + ": " + message);
	}
	
}
