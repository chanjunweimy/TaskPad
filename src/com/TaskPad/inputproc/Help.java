package com.TaskPad.inputproc;

import java.util.LinkedHashMap;

public class Help {

	private static LinkedHashMap<String, String> helpCommands;
	
	private static String KEY_START = "START";
	private static String KEY_ADD_TASK = "ADD TASK";
	private static String KEY_ADD_INFO_TASK = "ADD INFO TO TASK";
	private static String KEY_CLEAR_DATA = "CLEAR MEMORY";
	private static String KEY_CLEAR_SCREEN = "CLEAR SCREEN";
	private static String KEY_DELETE_TASK = "DELETE TASK";
	private static String KEY_EDIT_TASK = "EDIT TASK DESCRIPTION";
	private static String KEY_EXIT = "EXIT PROGRAM";
	private static String KEY_UNDO_LAST_DONE = "UNDO LAST DONE";
	
	public Help(){
		helpCommands  = new LinkedHashMap<String, String>();
		initialiseCommands();
		outputHelp();
	}
	
	private static void initialiseCommands(){
		helpCommands.put(KEY_START, "Here's a list of commands TaskPad can perform!");
		helpCommands.put(KEY_ADD_TASK, "add <desc> -d <deadline> -s <start time> -e <end time> -v <venue");
		helpCommands.put(KEY_ADD_INFO_TASK, "addinfo <taskID> <info>");
		helpCommands.put(KEY_CLEAR_DATA, "clc");
		helpCommands.put(KEY_CLEAR_SCREEN, "clcsr");
		helpCommands.put(KEY_DELETE_TASK, "del <taskID>");
		helpCommands.put(KEY_EDIT_TASK, "edit <taskID> <new desc>");
		helpCommands.put(KEY_UNDO_LAST_DONE, "undo");
		helpCommands.put(KEY_EXIT, "exit");
	}
	
	public void outputHelp(){
		String output = "";
	}
	
	private static String formatOutput(String key, String value){
		String formattedOutput = "" + key + "\n" + value + "\n";
		return formattedOutput;
	}
	
}
