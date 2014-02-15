package com.TaskPad.inputproc;

import java.util.Stack;

import com.TaskPad.inputproc.Command.CommandType;

public class Input {

	private static final String MESSAGE_INVALID_FORMAT = "Invalid Command: %s ";
	
	private static Command Command;
	private static Stack commandStack;
	
	public Input(){
		Command = new Command();
		commandStack = new Stack();
		System.out.println("test");
	}
	
	public static void receiveInput(String input){
		String commandTypeString = parseInput(input);
		Command.CommandType commandType = determineCommandType(commandTypeString);
		
		if (isValidCommandType(commandType)){
			pushCommandToStack(commandType);
			input = removeFirstWord(commandTypeString);
			performCommand (commandType, input);
		} else {
			invalidCommand(input);
		}
				
		
	}

	
	private static boolean isValidCommandType(CommandType commandType) {
		if (commandType.equals(commandType.INVALID)){
			return false;
		}
		return true;
	}

	private static void pushCommandToStack(CommandType commandType) {
		// TODO Auto-generated method stub
		
	}

	private static void performCommand(CommandType commandType, String input) {
		switch(commandType){
			case ADD:
				addTask(input);
				break;
			case LIST:
				deleteTask(input);
			case CLEAR:
				clearAllTasks();
				break;
			case UNDO:
				undoLast();
				break;
			case EDIT:
				editTask(input);
				break;
			case SEARCH:
				searchTask(input);
				break;
			case HELP:
				help();
				break;
			case EXIT:
				exitProgram();
				break;
			default:
				invalidCommand(input);
		}
	}

	/* Methods to perform commands */
	
	private static void addTask(String input) {
		// TODO Auto-generated method stub
		
	}

	private static void deleteTask(String input) {
		// TODO Auto-generated method stub
		
	}

	private static void clearAllTasks() {
		//Call executor function to clear
		
	}

	private static void undoLast() {
		//Command.CommandType commandType = commandStack.pop();
		
	}

	private static void editTask(String input) {
		// TODO Auto-generated method stub
		
	}

	private static void searchTask(String input) {
		// TODO Auto-generated method stub
		
	}
	
	private static void help() {
		// TODO Auto-generated method stub
		
	}

	private static void exitProgram() {
		//Call executor function to exit
	}

	private static void invalidCommand(String input) {
		// TODO Auto-generated method stub
		
	}

	private static Command.CommandType determineCommandType(String commandTypeString) {
		String commandToFind = getFirstWord(commandTypeString);
		Command.CommandType commandType = Command.find(commandToFind);
		
		return commandType;
	}

	private static String parseInput(String input) {
		if (isInvalidCommand(input)){
			return invalidInput(input);
		}
		
		String commandTypeString = getFirstWord(input);
		return commandTypeString;			
	}

	
	/* Helper methods for parsing commands */ 
	private static boolean isInvalidCommand(String userCommand) {
		if (userCommand.trim().equals("")){
			return true;
		}
		return false;
	}
	
	private static String invalidInput(String input) {
		//Call the GUI and send this message 
		return String.format(MESSAGE_INVALID_FORMAT, input);
	}
	
	private static String removeFirstWord(String input) {
		return input.replace(getFirstWord(input), "").trim();
	}
	
	private static String getFirstWord(String input) {
		String commandTypeString = input.trim().split("\\s+")[0];
		return commandTypeString;
	}

}
