package com.TaskPad.inputproc;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Help {

	private static LinkedHashMap<String, String> helpCommands;
	
	private static String KEY_START = "START";
	private static String KEY_ADD_TASK = "ADD TASK";
	private static String KEY_ADD_INFO_TASK = "ADD INFO TO TASK";
	private static String KEY_CLEAR_DATA = "CLEAR MEMORY";
	private static String KEY_DELETE_TASK = "DELETE TASK";
	private static String KEY_EDIT_TASK = "EDIT TASK DESCRIPTION";
	private static String KEY_EXIT = "EXIT PROGRAM";
	private static String KEY_LIST = "LIST TASKS"; 
	private static String KEY_MARK_DONE = "MARK TASK AS DONE";
	private static String KEY_SEARCH = "SEARCH TASKS";
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
		helpCommands.put(KEY_DELETE_TASK, "del <taskID>");
		helpCommands.put(KEY_EDIT_TASK, "edit <taskID> <new desc>");
		helpCommands.put(KEY_SEARCH, "search <keyword>");
		helpCommands.put(KEY_LIST, "ls <parameter (done, undone, all)>");
		helpCommands.put(KEY_MARK_DONE, "done <taskID>");
		helpCommands.put(KEY_UNDO_LAST_DONE, "undo");
		helpCommands.put(KEY_EXIT, "exit");
	}
	
	public void outputHelp(){
		String output = "";
		Iterator<Entry<String, String>> it = helpCommands.entrySet().iterator();
		while (it.hasNext()){
			Entry<String, String> entry = it.next();
			output += formatOutput(entry.getKey(), entry.getValue());
		}
		
		InputManager.outputToGui(output);
	}
	
	private static String formatOutput(String key, String value){
		String formattedOutput = "" + key + "\n" + value + "\n";
		return formattedOutput;
	}
	
}
