package com.taskpad.storage;

import java.util.LinkedList;

public class CommandRecord {
	// private static String previousCommand;
	private static LinkedList<String> commandsForUndo = new LinkedList<String>();
	private static LinkedList<String> commandsForRedo = new LinkedList<String>();
	
	public static void pushForUndo(String command) {
		// previousCommand = command;
		commandsForUndo.push(command);
	}
	
	public static String popForUndo() throws NoPreviousCommandException {
		if (commandsForUndo.size() < 1) {
			throw new NoPreviousCommandException();
		}
		return commandsForUndo.pop();
	}
	
	public static void pushForRedo(String command) {
		// previousCommand = command;
		commandsForRedo.push(command);
	}
	
	public static String popForRedo() throws NoPreviousCommandException {
		if (commandsForRedo.size() < 1) {
			throw new NoPreviousCommandException();
		}
		return commandsForRedo.pop();
	}	// dummy
}
