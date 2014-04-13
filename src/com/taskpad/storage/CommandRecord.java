//@author A0105788U

package com.taskpad.storage;

import java.util.LinkedList;

/**
 * CommandRecord
 * 
 * This class is to keep a record of executed user commands at runtime, for (multiple)
 * undo and redo purpose. It is done by maintaining two stacks for undo and redo
 * respectively.
 * 
 */
public class CommandRecord {
	private static LinkedList<String> commandsForUndo = new LinkedList<String>();
	private static LinkedList<String> commandsForRedo = new LinkedList<String>();
	
	public static void pushForUndo(String command) {
		commandsForUndo.push(command);
	}
	
	public static String popForUndo() throws NoPreviousCommandException {
		if (commandsForUndo.size() < 1) {
			throw new NoPreviousCommandException();
		}
		return commandsForUndo.pop();
	}
	
	public static void pushForRedo(String command) {
		commandsForRedo.push(command);
	}
	
	public static String popForRedo() throws NoPreviousCommandException {
		if (commandsForRedo.size() < 1) {
			throw new NoPreviousCommandException();
		}
		return commandsForRedo.pop();
	}
}
