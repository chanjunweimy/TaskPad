package com.taskpad.inputproc;

import java.util.HashMap;
import java.util.Map;

import com.taskpad.inputproc.Command.CommandType;

public class InputMain {

	private static final String MESSAGE_EMPTY_INPUT = "Error: Empty input";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid Command: %s ";
	private static final String MESSAGE_INVALID_INPUT = "Error: Invalid input: %s";
	private static final String MESSAGE_INVALID_PARAMETER_NUMBER = "Error: Invalid number of parameters. Type help if you need! :)";
	
	private static final String COMMAND_ADD = "ADD";
	private static final String COMMAND_ADD_INFO = "ADDINFO";
	private static final String COMMAND_CLEAR = "CLEAR";
	private static final String COMMAND_DELETE = "DELETE";
	private static final String COMMAND_DONE = "DONE";
	private static final String COMMAND_EDIT = "EDIT";
	private static final String COMMAND_LIST = "LIST";
	private static final String COMMAND_SEARCH = "SEARCH";
	private static final String COMMAND_UNDO = "UNDO";
	
	private static final int LENGTH_EDIT = 2;
	
	private static final String PARAMETER_TASK_ID = "TASKID";
	private static final String PARAMETER_NULL = "NULL";
	private static final String PARAMETER_DESC = "DESC";
	private static final String PARAMETER_INFO = "INFO";
	private static final String PARAMETER_LIST_KEY = "KEY";
	private static final String PARAMETER_SEARCH_KEYWORD = "KEYWORD";
	
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
		String commandTypeString = parseInput(input);
		Command.CommandType commandType = determineCommandType(commandTypeString);

		if (isValidCommandType(commandType)){			
			input = removeFirstWord(commandTypeString);
			performCommand (commandType, input);
		} else {
			invalidCommand(input);
		}

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
			case ADD_INFO:
				addInfoTask(input);
				break;
			case LIST:
				listTask(input);
			case CLEAR_ALL:
				clearAllTasks();
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
		
		if (isValidEditInput(splitInput)){
			clearInputParameters();
			putInputParameters(PARAMETER_TASK_ID, splitInput[0]);
			putInputParameters(PARAMETER_INFO, splitInput[1]);
			inputObject = new Input(COMMAND_ADD_INFO, inputParameters);
			passObjectToExecutor();
		} else {
			return;
		}
	}
	
	private static void listTask(String input){
		if (isEmptyInput(input)){
			inputManager.outputToGui(String.format(MESSAGE_EMPTY_INPUT));
			return;
		} else {
			inputObject = createListObject(input);
			passObjectToExecutor();
		}
	}
	
	private static Input createListObject(String input){
		clearInputParameters();
		putInputParameters(PARAMETER_LIST_KEY, input);
		inputObject = new Input(COMMAND_LIST, inputParameters);
		return inputObject;
	}
	
	private static void deleteTask(String input) {
		if (isValidTaskIDInput(input)){
			inputObject = createDeleteObject(input);
			passObjectToExecutor();
		} else {
			return;
		}
	}

	private static void doneTask(String input) {
		if (isValidTaskIDInput(input)){
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

	private static Input createDeleteObject(String input) {
		clearInputParameters();
		putInputParameters(PARAMETER_TASK_ID, input);
		inputObject = new Input(COMMAND_DELETE, inputParameters);		
		return inputObject;
	}
	
	private static void passObjectToExecutor(){
		inputManager.passToExecutor(inputObject);
	}
	
	private static boolean isValidTaskIDInput(String input){
		String errorMessage = "";
		
		if (isEmptyInput(input)){
			errorMessage = String.format(MESSAGE_EMPTY_INPUT);
			inputManager.outputToGui(errorMessage);
			return false;
		}
		
		if(isNotInteger(input) || isInvalidID(input)){
			outputIdError(input);
			return false;
		}
		
		return true;
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
			return false;
		}
		return true;
	}
	
	private static boolean isInvalidID(String input){
		int inputNum = Integer.parseInt(input);
		if (inputNum > inputManager.retrieveNumberOfTasks()){
			return false;
		}
		return true;
	}
	
	private static void clearInputParameters(){
		inputParameters.clear();
	}
	
	private static void putInputParameters(String parameter, String input){
		inputParameters.put(parameter, input);
	}

	private static void clearAllTasks() {
		clearInputParameters();
		putInputParameters(PARAMETER_NULL, "");
		inputObject = new Input(COMMAND_CLEAR, inputParameters);
		passObjectToExecutor();
	}
	
	private static void undoLast() {
		clearInputParameters();
		putInputParameters(PARAMETER_NULL, "");
		inputObject = new Input(COMMAND_UNDO, inputParameters);
		passObjectToExecutor();
	}
	
	private static void editTask(String input) {
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
			inputManager.outputToGui(MESSAGE_INVALID_PARAMETER_NUMBER);
			return false;
		} else if (!isValidTaskIDInput(splitInput[0])) {
			outputIdError(splitInput[0]);
			return false;
		}
		return true;
	}

	private static void outputIdError(String input) {
		String errorMessage = String.format(MESSAGE_INVALID_INPUT, input);
		inputManager.outputToGui(errorMessage);
	}
	
	
	private static boolean isInvalidParameterNumber(int length){
		if (length != LENGTH_EDIT){
			return false;
		}
		return true;
	}

	private static void searchTask(String input) {
		clearInputParameters();
		putInputParameters(PARAMETER_SEARCH_KEYWORD, input);
		inputObject = new Input(COMMAND_SEARCH, inputParameters);		
	}
	
	private static void help() {
		Help help = new Help();
		help.outputHelp();
	}

	private static void exitProgram() {
		inputManager.callGuiExit();
	}

	private static void invalidCommand(String input) {
		inputManager.outputToGui(String.format(MESSAGE_INVALID_COMMAND, input));	
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