//@author A0105788U

package com.taskpad.storage;

import java.util.LinkedList;

/**
 * CommandRecord
 * 
 * This class is to keep a record of older-version data files, for (multiple)
 * undo and redo purpose. It is done by maintaining two stacks for undo and redo
 * respectively.
 * 
 */
public class DataFileStack {
	public static final String FILE = ".data.xml";
	public static LinkedList<String> undoStack = new LinkedList<String>();
	public static LinkedList<String> redoStack = new LinkedList<String>();
	
	public static String requestDataFile() {
		return ".data" + (undoStack.size() + redoStack.size()) + ".xml";
	}
	
	public static void pushForUndo(String file) {
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
