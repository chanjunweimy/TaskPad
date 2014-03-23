package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.taskpad.alarm.AlarmManager;
import com.taskpad.input.CommandTypes.CommandType;

public class InputMain {

	private static final String MESSAGE_CONFIRMATION_CLEAR_SCREEN = "Confirm clear screen? (Y/N)";
	private static final String MESSAGE_CONFIRMATION_CLEAR_DATA = "Confirm clear data? (Y/N)";
	private static final String MESSAGE_NOT_CLEAR_DATA = "Not clearing data";
	private static final String MESSAGE_NOT_CLEAR_SCREEN = "Not clearing Screen";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid Command: %s ";
	
	private static final String COMMAND_CLEAR = "CLEAR";
	private static final String COMMAND_CLEAR_SCREEN = "CLEARSCREEN";
	
	private static final String STRING_EMPTY = "";
			
	@SuppressWarnings("unused")
	private static CommandTypes commandTypes = new CommandTypes();
	@SuppressWarnings("unused")
	private static Input inputObject;
	private static boolean isConfirmation = false;
	protected static boolean hasCheckedFlexi = false;
	private static String currentCommand = STRING_EMPTY;
	
	@SuppressWarnings("unused")
	private static Map<String, String> inputParameters = new HashMap<String, String>();
	
	private static Logger logger = Logger.getLogger("TaskPad");
	
	protected static String receiveInput(String input){
		input = input.trim();
		String outputString = STRING_EMPTY;
		hasCheckedFlexi = false;
		
		if (isConfirmation){
			processConfirmation(input);
		} else {
			String inputCopy = input;
			if (errorIfNoInput(input)){
				ErrorMessages.emptyInputMessage();
			}
				
			String commandTypeString = parseInput(inputCopy);
			CommandTypes.CommandType commandType = determineCommandType(commandTypeString);
			logger.info("Command: " + commandType.toString());
			
			if (isValidCommandType(commandType)){		
				commandTypeString = removeFirstWord(input);
				outputString = commandType.toString() + " " + commandTypeString;
				performCommand (commandType, commandTypeString, input);
			} else if (hasCheckedFlexi){
				invalidCommand(input);
				outputString += commandType.toString();
			} else {
				hasCheckedFlexi = true;
				outputString = flexiCommand(input);
			}
		}
		return outputString;
	}
	
	private static void processConfirmation(String input) {
		if (isConfirmClear(input)){
			executeConfirmation();
		} else if (isNotClearing(input)){
			processNoClear();
		} else {
			ErrorMessages.invalidConfirmationInput();
		}
		resetConfirmationVariable();		
	}

	private static boolean isConfirmClear(String input){
		return input.equalsIgnoreCase("Y");
	}
	
	private static boolean isNotClearing(String input){
		return input.equalsIgnoreCase("N");
	}
	
	private static void executeConfirmation(){
		if (currentCommand.equals(COMMAND_CLEAR)){
			clearAllTasks();
		} else if (currentCommand.equals(COMMAND_CLEAR_SCREEN)){
			InputManager.clearScreen();
		}
	}
	
	private static void processNoClear(){
		if (currentCommand.equals(COMMAND_CLEAR)){
			InputManager.outputToGui(MESSAGE_NOT_CLEAR_DATA);
		} else if (currentCommand.equals(COMMAND_CLEAR_SCREEN)){
			InputManager.outputToGui(MESSAGE_NOT_CLEAR_SCREEN);
		}
	}
	
	private static void resetConfirmationVariable(){
		isConfirmation = false;
		currentCommand = STRING_EMPTY;
	}
	
	private static boolean errorIfNoInput(String input) {
		return input.equals(STRING_EMPTY);
	}

	@SuppressWarnings("static-access")
	private static boolean isValidCommandType(CommandType commandType) {
		if (commandType.equals(commandType.INVALID)){
			return false;
		}
		return true;
	}

	private static void performCommand(CommandType commandType, String commandTypeString, String input) {
		switch(commandType){
			case ADD:
				addTask(commandTypeString, input);
				break;
			case ALARM:
				setUpAlarm(commandTypeString, input);
				break;
			case ADD_INFO:
				addInfoTask(commandTypeString, input);
				break;
			case ADD_REM:
				addRemTask(commandTypeString, input);
				break;
			case ADD_PRI:
				addPriTask(commandTypeString, input);
				break;
			case LIST:
				listTask(commandTypeString, input);
				break;
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
				deleteTask(commandTypeString, input);
				break;
			case DONE:
				doneTask(commandTypeString, input);
				break;
			case EDIT:
				editTask(commandTypeString, input);
				break;
			case SEARCH:
				searchTask(commandTypeString, input);
				break;
			case STOP:
				stopAlarm(commandTypeString, input);
				break;
			case HELP:
				help();
				break;
			case EXIT:
				exitProgram();
				break;
			case REDO:
				redoTask();
				break;
			case UNDO:
				undoLast();
				break;
			default:
				invalidCommand(commandTypeString);
				break;
		}
	}

	/* Methods to perform commands */

	private static void addTask(String input, String fullInput) {
		Add add = new Add(input, fullInput);
		add.run();
	}
	
	private static void addPriTask(String input, String fullInput){
		AddPri addPri = new AddPri(input, fullInput);
		addPri.run();
	}

	private static void addInfoTask(String input, String fullInput) {
		Addinfo addinfo = new Addinfo(input, fullInput);
		addinfo.run();
	}
	
	private static void addRemTask(String input, String fullInput){
		Addrem addRem = new Addrem(input, fullInput);
		addRem.run();
	}
	
	private static void listTask(String input, String fullInput){
		List list = new List(input, fullInput);
		list.run();
	}
	
	private static void redoTask(){
		Redo redo = new Redo(STRING_EMPTY, "REDO");
		redo.run();
	}
	
	private static void deleteTask(String input, String fullInput) {
		Delete delete = new Delete(input, fullInput);
		delete.run();
	}

	private static void doneTask(String input, String fullInput) {
		Done done = new Done(input, fullInput);
		done.run();
	}

	private static void clearAllTasksConfirmation(){
		InputManager.outputToGui(MESSAGE_CONFIRMATION_CLEAR_DATA);
	}
	
	private static void clearAllTasks() {
		ClearTasks clearTask = new ClearTasks(STRING_EMPTY, "CLEAR");
		clearTask.run();
	}
	
	private static void clearScreen(){
		InputManager.outputToGui(MESSAGE_CONFIRMATION_CLEAR_SCREEN);
	}
	
	private static void undoLast() {
		Undo undo = new Undo(STRING_EMPTY, "UNDO");
		undo.run();
	}
	
	private static void editTask(String input, String fullInput) {
		Edit edit = new Edit(input, fullInput);
		edit.run();
	}

	private static void searchTask(String input, String fullInput) {
		Search search = new Search(input, fullInput);
		search.run();	
	}
	
	private static void stopAlarm(String input, String fullInput){
		try {
			AlarmManager.turnOffAlarm();
		} catch (Exception e) {
			return;
		}
	}
	
	private static void help() {
		Help help = new Help();
		help.outputHelp();
	}

	private static void exitProgram() {
		InputManager.callGuiExit();
	}
	
	private static void setUpAlarm(String input, String fullInput) {
		new Alarm(input, fullInput);
	}
	
	private static String flexiCommand(String input){
		hasCheckedFlexi = true;
		CommandType command = CommandQueue.findFlexi(input);
		logger.info("Flexicommands: " + command.toString());
		String commandTypeString = replaceCommandWord(input, command);
		performCommand(command, commandTypeString, input);
		
		return command.toString() + " " + commandTypeString;
	}
	
	private static String replaceCommandWord (String input, CommandType command){
		//return input.replaceFirst("(?i)"+command.toString()+" ", "");  //deprecated
		String desc = STRING_EMPTY;
		String[] splitInput = input.split(" ");
		
		for (int i=0; i<splitInput.length; i++){
			if (isNotCommandString(command, splitInput, i)){
				desc += splitInput[i] + " ";
			}
		}
			
		return desc;
	}

	private static boolean isNotCommandString(CommandType command,
			String[] splitInput, int i) {
		return !splitInput[i].toUpperCase().equals(command.toString());
	}
	
	private static void invalidCommand(String input) {
		InputManager.outputToGui(String.format(MESSAGE_INVALID_COMMAND, input));	
	}

	private static CommandTypes.CommandType determineCommandType(String commandTypeString) {
		String commandToFind = getFirstWord(commandTypeString);
		CommandTypes.CommandType commandType = CommandQueue.find(commandToFind);
		
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
		if (userCommand.trim().equals(STRING_EMPTY)){
			return true;
		}
		return false;
	}
	
	private static String invalidInput(String input) {
		return String.format(MESSAGE_INVALID_COMMAND, input);
	}
	
	private static String removeFirstWord(String input) {
		return input.replaceFirst(getFirstWord(input), STRING_EMPTY).trim();
	}
	
	private static String getFirstWord(String input) {
		String commandTypeString = input.trim().split("\\s+")[0];
		return commandTypeString;
	}

}
