package com.taskpad.data;

public class CommandRecord {
	private static String previousCommand;
	
	public static void setPreviousCommand(String command) {
		previousCommand = command;
	}
	
	public static String getPreviousCommand() {
		return previousCommand;
	}
}
