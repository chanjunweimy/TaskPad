package com.taskpad.execute;

/**
 * InvalidTaskIdException
 * 
 * This exception will be thrown if the task id from user input is invalid
 * 
 */
public class InvalidTaskIdException extends Exception {
	public InvalidTaskIdException() {
		System.out.println("Invalid task id.");
	}
}
