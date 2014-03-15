package com.taskpad.data;

public class DataFile {
	public static final String FILE = ".data.xml";
	public static final String FILE_PREV = ".data_prev.xml";
	
	private static boolean previousIsValid = false;
	
	public static boolean isValidPrevious() {
		return previousIsValid;
	}
	
	public static void setPreviousIsValid(boolean isValid) {
		previousIsValid = isValid;
	}
}
