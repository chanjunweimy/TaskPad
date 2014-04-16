//@author A0105788U

package com.taskpad.execute;

/**
 * InvalidTaskIdException
 * 
 * This exception will be thrown if the task id from user input is invalid
 * 
 */
public class InvalidTaskIdException extends Exception {
	/**
	 * generated
	 */
	private static final long serialVersionUID = 4039361586685055328L;

	public InvalidTaskIdException() {
		System.out.println("Invalid task id.");
	}
}
