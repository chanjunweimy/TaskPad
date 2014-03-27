package com.taskpad.storage;

import java.util.LinkedList;

public class DataFileStack {
	public static final String FILE = ".data.xml";
	// public static final String FILE_PREV = ".data_prev.xml";
	public static LinkedList<String> undoStack = new LinkedList<String>();
	public static LinkedList<String> redoStack = new LinkedList<String>();
	
	// private static boolean previousIsValid = false;
	
	/*
	public static boolean isValidPrevious() {
		return previousIsValid;
	}
	
	public static void setPreviousIsValid(boolean isValid) {
		previousIsValid = isValid;
	}
	*/
	
	public static String requestDataFile() {
		return ".data" + (undoStack.size() + redoStack.size()) + ".xml";
	}
	
	public static void pushForUndo(String file) {
		// previousCommand = command;
		undoStack.push(file);
	}
	
	public static String popForUndo() throws NoPreviousFileException {
		if (undoStack.size() < 1) {
			throw new NoPreviousFileException();
		}
		return undoStack.pop();
	}
	
	public static void pushForRedo(String file) {
		redoStack.push(file);
	}
	
	public static String popForRedo() throws NoPreviousFileException {
		if (redoStack.size() < 1) {
			throw new NoPreviousFileException();
		}
		return redoStack.pop();
	}
}
