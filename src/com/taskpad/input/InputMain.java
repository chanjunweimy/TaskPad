package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;

import com.taskpad.input.CommandTypes.CommandType;

public class InputMain {

	private static final String MESSAGE_CONFIRMATION_CLEAR_SCREEN = "Confirm clear screen? (Y/N)";
	private static final String MESSAGE_CONFIRMATION_CLEAR_DATA = "Confirm clear data? (Y/N)";
	private static final String MESSAGE_EMPTY_INPUT = "Error: Empty input";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid Command: %s ";
	private static final String MESSAGE_INVALID_INPUT = "Error: Invalid input: %s";
	private static final String MESSAGE_INVALID_PARAMETER_NUMBER = "Error: Invalid number of parameters.\nType help if you need! :)";
	private static final String MESSAGE_ERROR_LIST = "Error: Invalid list parameters.\n Type help if you need! :)";
	
	private static final String COMMAND_ADD = "ADD";
	private static final String COMMAND_ADD_INFO = "ADDINFO";
	private static final String COMMAND_CLEAR = "CLEAR";
	private static final String COMMAND_CLEAR_SCREEN = "CLEARSCREEN";
	private static final String COMMAND_DELETE = "DELETE";
	private static final String COMMAND_DONE = "DONE";
	private static final String COMMAND_EDIT = "EDIT";
	private static final String COMMAND_LIST = "LIST";
	private static final String COMMAND_SEARCH = "SEARCH";
	private static final String COMMAND_UNDO = "UNDO";
	
	private static final int LENGTH_EDIT = 2;
	private static final int LENGTH_DELETE = 1;
	private static final int LENGTH_DONE = 1;
	private static final int LENGTH_ADD_INFO = 2;
	
	private static final String PARAMETER_TASK_ID = "TASKID";
	private static final String PARAMETER_NULL = "NULL";
	private static final String PARAMETER_DESC = "DESC";
	private static final String PARAMETER_INFO = "INFO";
	private static final String PARAMETER_LIST_KEY = "KEY";
	private static final String PARAMETER_SEARCH_KEYWORD = "KEYWORD";
	
	private static final String[] PARAMETER_LIST = {"ALL", "UNDONE", "DONE"};
	
	private static CommandTypes commandTypes = new CommandTypes();
	private static Input inputObject;
	private static boolean isConfirmation = false;
	private static String currentCommand = "";
	
	private static Map<String, String> inputParameters = new HashMap<String, String>();
	
	public static void receiveInput(String input){
		if (isConfirmation){
			if (isConfirmClear(input)){
				processConfirmation();
			}
			resetConfirmationVariable();
		} else {
			String inputCopy = input;
			if (errorIfNoInput(input)){
				return;
			}
			String commandTypeString = parseInput(inputCopy);
			CommandTypes.CommandType commandType = determineCommandType(commandTypeString);
	
			if (isValidCommandType(commandType)){		
				input = removeFirstWord(input);
				performCommand (commandType, input);
			} else {
				invalidCommand(input);
			}
		}

	}
	
	private static boolean isConfirmClear(String input){
		if (input.equalsIgnoreCase("Y")){
			return true;
		} else {
			return false;
		}
	}
	
	private static void processConfirmation(){
		if (currentCommand.equals(COMMAND_CLEAR)){
			clearAllTasks();
			
		} else if (currentCommand.equals(COMMAND_CLEAR_SCREEN)){
			InputManager.clearScreen();
		}
	}
	
	private static void resetConfirmationVariable(){
		isConfirmation = false;
		currentCommand = "";
	}
	
	private static boolean errorIfNoInput(String input) {
		if (input.equals("")){
			InputManager.outputToGui(String.format(MESSAGE_EMPTY_INPUT));
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
			case ADD_INFO:
				addInfoTask(input);
				break;
			case LIST:
				listTask(input);
			case CLEAR_ALL:
				isConfirmation = true;
				currentCommand = COMMAND_CLEAR;
				clearAllTasksConfirmation();
				break;
			case CLEAR_SCREEN:
				isConfirmation = true;
				currentCommand = COMMAND_CLEAR_SCREEN;
				clearScreen();
				break;
			case DELETE:
				deleteTask(input);
				break;
			case DONE:
				doneTask(input);
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
			case UNDO:
				undoLast();
				break;
			default:
				invalidCommand(input);
		}
	}

	/* Methods to perform commands */
	
	private static void addTask(String input) {
		Add add = new Add(input);
		inputParameters.clear();
		inputParameters = add.run();
		if (isEmptyInputParameters()){
			InputManager.outputToGui(MESSAGE_EMPTY_INPUT);
		} else {
			inputObject = new Input(COMMAND_ADD, inputParameters);
			passObjectToExecutor();
		}
	}

	private static boolean isEmptyInputParameters() {
		if (inputParameters.size() == 0){
			return true;
		}
		return false;
	}

	private static void addInfoTask(String input) {
		String[] splitInput = input.split(" ");
		
		if (isEmptyInput(input)){
			InputManager.outputToGui(MESSAGE_EMPTY_INPUT);
			return;
		}
		
		if (isValidAddInfoInput(splitInput)){
			clearInputParameters();
			putInputParameters(PARAMETER_TASK_ID, splitInput[0]);
			putInputParameters(PARAMETER_INFO, splitInput[1]);
			inputObject = new Input(COMMAND_ADD_INFO, inputParameters);
			passObjectToExecutor();
		} 
	}
	
	private static boolean isValidAddInfoInput(String[] input){		
		if (input.length != LENGTH_ADD_INFO){
			InputManager.outputToGui(MESSAGE_INVALID_PARAMETER_NUMBER);
			return false;
		} 
		
		if(isNotInteger(input[0]) || isInvalidID(input[0])){
			outputIdError(input[0]);
			return false;
		}
		
		return true;
	}
	
	private static void listTask(String input){
		if (isEmptyInput(input)){
			InputManager.outputToGui(String.format(MESSAGE_EMPTY_INPUT));
			return;
		} else if (isInvalidListInput(input)){
			InputManager.outputToGui(String.format(MESSAGE_ERROR_LIST));
			return; 
		} else {
			inputObject = createListObject(input);
			passObjectToExecutor();
		}
	}
	
	private static boolean isInvalidListInput(String input){
		for (int i=0; i<PARAMETER_LIST.length; i++){
			if (input.equalsIgnoreCase(PARAMETER_LIST[i])){
				return false;
			}
		}
		return true;
	}
	
	private static Input createListObject(String input){
		clearInputParameters();
		putInputParameters(PARAMETER_LIST_KEY, input);
		inputObject = new Input(COMMAND_LIST, inputParameters);
		return inputObject;
	}
	
	private static void deleteTask(String input) {
		Delete delete = new Delete(input);
		delete.run();
	}

	private static void doneTask(String input) {
		if (isValidTaskIDInput(input, "DONE")){
			inputObject = createDoneObject(input);
			passObjectToExecutor();
		} else {
			return;
		}
	}
	
	private static Input createDoneObject(String input) {
		clearInputParameters();
		putInputParameters(PARAMETER_TASK_ID, input);
		inputObject = new Input(COMMAND_DONE, inputParameters);
		return inputObject;
	}
	
	private static void passObjectToExecutor(){
		InputManager.passToExecutor(inputObject);
	}
	
	private static boolean isValidTaskIDInput(String input, String commandString){
		String errorMessage = "";
		
		if (isEmptyInput(input)){
			errorMessage = String.format(MESSAGE_EMPTY_INPUT);
			InputManager.outputToGui(errorMessage);
			return false;
		}
		
		String inputString[] = input.split(" ");
		
		if (commandString.equals(COMMAND_DELETE)){
			if (isNotNumberArgs(inputString, commandString)){
				InputManager.outputToGui(MESSAGE_INVALID_PARAMETER_NUMBER);
				return false;
			}
		} else if (commandString.equals(COMMAND_DONE)){
			if (isNotNumberArgs(inputString, commandString)){
				InputManager.outputToGui(MESSAGE_INVALID_PARAMETER_NUMBER);
				return false;
			}
		} 
		
		if(isNotInteger(input) || isInvalidID(input)){
			outputIdError(input);
			return false;
		}
		
		return true;
	}
	
	private static boolean isNotNumberArgs(String[] inputString, String commandString){
		if (commandString.equals(COMMAND_DELETE)){
			if (inputString.length != LENGTH_DELETE){
				return true;
			}
		} else if (commandString.equals(COMMAND_DONE)){
			if (inputString.length != LENGTH_DONE){
				return true;
			}
		} 
		
		return false;
	}
	
	private static boolean isEmptyInput(String input){
		if (input.equals("")){
			return true;
		}
		return false;
	}
	
	private static boolean isNotInteger(String input){
		try{
			Integer.parseInt(input);
		} catch (NumberFormatException e){
			return true;
		}
		return false;
	}
	
	private static boolean isInvalidID(String input){
		int inputNum = Integer.parseInt(input);
		if (inputNum > InputManager.retrieveNumberOfTasks()){
			return true;
		}
		return false;
	}
	
	private static void clearInputParameters(){
		inputParameters.clear();
	}
	
	private static void putInputParameters(String parameter, String input){
		inputParameters.put(parameter, input);
	}

	private static void clearAllTasksConfirmation(){
		InputManager.outputToGui(MESSAGE_CONFIRMATION_CLEAR_DATA);
	}
	
	private static void clearAllTasks() {
		clearInputParameters();
		putInputParameters(PARAMETER_NULL, "");
		inputObject = new Input(COMMAND_CLEAR, inputParameters);
		passObjectToExecutor();
	}
	
	private static void clearScreen(){
		InputManager.outputToGui(MESSAGE_CONFIRMATION_CLEAR_SCREEN);
	}
	
	private static void undoLast() {
		clearInputParameters();
		putInputParameters(PARAMETER_NULL, "");
		inputObject = new Input(COMMAND_UNDO, inputParameters);
		passObjectToExecutor();
	}
	
	private static void editTask(String input) {
		if (isEmptyInput(input)){
			InputManager.outputToGui(MESSAGE_EMPTY_INPUT);
			return;
		}
		
		String[] splitInput = input.split(" ");
		
		if (isValidEditInput(splitInput)){
			clearInputParameters();
			putInputParameters(PARAMETER_TASK_ID, splitInput[0]);
			putInputParameters(PARAMETER_DESC, splitInput[1]);
			inputObject = new Input(COMMAND_EDIT, inputParameters);
			passObjectToExecutor();
		} else {
			return;
		}
	}
	
	private static boolean isValidEditInput(String[] splitInput){
		if (isInvalidParameterNumber(splitInput.length)){
			InputManager.outputToGui(MESSAGE_INVALID_PARAMETER_NUMBER);
			return false;
		} 	else if(isNotInteger(splitInput[0]) || isInvalidID(splitInput[0])){
			outputIdError(splitInput[0]);
			return false;
		}
		
		return true;
	}

	private static void outputIdError(String input) {
		String errorMessage = String.format(MESSAGE_INVALID_INPUT, input);
		InputManager.outputToGui(errorMessage);
	}
	
	
	private static boolean isInvalidParameterNumber(int length){
		if (length != LENGTH_EDIT){
			return true;
		}
		return false;
	}

	private static void searchTask(String input) {
		if (isEmptyInput(input)){
			InputManager.outputToGui(MESSAGE_EMPTY_INPUT);
			return;
		}
		
		clearInputParameters();
		putInputParameters(PARAMETER_SEARCH_KEYWORD, input);
		inputObject = new Input(COMMAND_SEARCH, inputParameters);		
	}
	
	private static void help() {
		Help help = new Help();
		help.outputHelp();
	}

	private static void exitProgram() {
		InputManager.callGuiExit();
	}

	private static void invalidCommand(String input) {
		InputManager.outputToGui(String.format(MESSAGE_INVALID_COMMAND, input));	
	}

	private static CommandTypes.CommandType determineCommandType(String commandTypeString) {
		String commandToFind = getFirstWord(commandTypeString);
		CommandTypes.CommandType commandType = CommandTypes.find(commandToFind);
		
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
		return String.format(MESSAGE_INVALID_COMMAND, input);
	}
	
	private static String removeFirstWord(String input) {
		return input.replace(getFirstWord(input), "").trim();
	}
	
	private static String getFirstWord(String input) {
		String commandTypeString = input.trim().split("\\s+")[0];
		return commandTypeString;
	}

}
