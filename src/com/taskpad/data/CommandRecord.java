package com.taskpad.data;

import java.util.LinkedList;

public class CommandRecord {
	// private static String previousCommand;
	private static LinkedList<String> commands = new LinkedList<String>();
	
	public static void push(String command) {
		// previousCommand = command;
		commands.push(command);
	}
	
	public static String pop() throws NoPreviousCommandException {
		if (commands.size() < 1) {
			throw new NoPreviousCommandException();
		}
		return commands.pop();
	}
}
