//@author A0105788U

package com.taskpad.storage;


public class NoPreviousFileException extends Exception {
	/**
	 * generated
	 */
	private static final long serialVersionUID = 5481977052834704074L;

	public NoPreviousFileException() {
		System.out.println("No previous data file.");
	}
}
