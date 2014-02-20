package com.TaskPad.inputproc;

import java.util.HashMap;
import java.util.Map;

import com.TaskPad.inputproc.Command.CommandType;

public class InputMain {

	private static final String MESSAGE_INVALID_FORMAT = "Invalid Command: %s ";
	private static final String MESSAGE_EMPTY_INPUT = "Error: Empty input";
	
	private static final String COMMAND_ADD = "ADD";
	private static final String COMMAND_CLEAR = "CLEAR";
	private static final String COMMAND_DELETE = "DELETE";
	
	private static final String PARAMETER_TASK_ID = "TASKID";
	private static final String PARAMETER_NULL = "NULL";
	
	private static Command command;
	private static InputManager inputManager;
	private static Input inputObject;
	
	private static Map<String, String> inputParameters;
	
	public InputMain(){
		command = new Command();
		inputManager = new InputManager();
		inputParameters = new HashMap<String, String>();
	}
	
	public static void receiveInput(String input){
		if (errorIfNoInput(input)){
			return;
		}
//		String commandTypeString = parseInput(input);
//		Command.CommandType commandType = determineCommandType(commandTypeString);
//		
//		if (isValidCommandType(commandType)){
//			input = removeFirstWord(commandTypeString);
//			inputObject = performCommand (commandType, input);
//			inputManager.passToExecutor(inputObject);
//		} else {
//			invalidCommand(input);
//		}
//				
	}
	
	private static boolean errorIfNoInput(String input) {
		if (input.equals("")){
			inputManager.outputToGui(String.format(MESSAGE_EMPTY_INPUT));
			return true;
		}
		return false;
	}

	private static boolean isValidCommandType(CommandType commandType) {
		if (commandType.equals(commandType.INVALID)){
			return false;
		}
		return true;
	}


	private static void performCommand(CommandType commandType, String input) {
		switch(commandType){
			case ADD:
				addTask(input);
				break;
			case LIST:
				deleteTask(input);
			case CLEAR_ALL:
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

	private static Input deleteTask(String input) {
		inputObject = createDeleteObject(input);
		return inputObject;
	}

	private static Input createDeleteObject(String input) {
		clearInputParameters();
		putInputParameters(PARAMETER_TASK_ID, input);
		inputObject = new Input(COMMAND_DELETE, inputParameters);
		return inputObject;
	}
	
	private static void clearInputParameters(){
		inputParameters.clear();
	}
	
	private static void putInputParameters(String parameter, String input){
		inputParameters.put(parameter, input);
	}

	private static Input clearAllTasks() {
		clearInputParameters();
		putInputParameters(PARAMETER_NULL, "");
		inputObject = new Input(COMMAND_CLEAR, inputParameters);
		return inputObject;
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
		Help help = new Help();
		help.outputHelp();
	}

	private static void exitProgram() {
		inputManager.callGuiExit();
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
