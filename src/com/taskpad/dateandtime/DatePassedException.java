//@author A0119646X

package com.taskpad.dateandtime;

public class DatePassedException extends Exception {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 7516238062049393549L;

	private static final String MESSAGE = "Date has passed. Please enter a date in the future";
		
	public DatePassedException(){
		super(MESSAGE);
	}
	
}
