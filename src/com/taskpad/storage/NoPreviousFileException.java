//@author A0105788U

package com.taskpad.storage;


public class NoPreviousFileException extends Exception {
	public NoPreviousFileException() {
		System.out.println("No previous data file.");
	}
}
