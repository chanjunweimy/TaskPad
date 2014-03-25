package com.taskpad.input;

/**
 * Exception for when TaskID larger than number of tasks in storage
 * @author Lynnette
 *
 */

public class TaskIDException extends Exception {
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = -1929853963909529411L;

	public TaskIDException(String message){
		super(message);
	}
	
}
