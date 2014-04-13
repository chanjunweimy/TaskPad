//@author A0119646X

package com.taskpad.input;



import java.util.logging.Logger;

import com.taskpad.input.CommandTypes.CommandType;

public class InputMain {

	private static final String MESSAGE_INVALID_COMMAND = "Invalid Command: %s ";
	
	private static final String STRING_EMPTY = "";
			
	private static boolean hasCheckedFlexi = false;
		
	private static Logger logger = Logger.getLogger("TaskPad");
	
	protected static String receiveInput(String input){
		hasCheckedFlexi = false;
		input = input.trim();
		String outputString = STRING_EMPTY;

		if (errorIfNoInput(input)){
			ErrorMessages.emptyInputMessage();
		}
		
		String inputCopy = input;
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
			
		} else if (!hasCheckedFlexi){
			hasCheckedFlexi = true;
			outputString = flexiCommand(input);
		}
		return outputString;
	}
	
	private static boolean errorIfNoInput(String input) {
		return input.equals(STRING_EMPTY);
	}

	@SuppressWarnings("static-access")
	private static boolean isValidCommandType(CommandType commandType) {
		return !commandType.equals(commandType.INVALID);
	}

	private static void performCommand(CommandType commandType, String commandTypeString, String input) {
		switch(commandType){
			case ADD:
				CommandQueue.getInstance().Add(commandTypeString, input);
				break;
			case ALARM:
				CommandQueue.getInstance().Alarm(commandTypeString, input);
				break;
			case ADD_INFO:
				CommandQueue.getInstance().addInfoTask(commandTypeString, input);
				break;
			case ADD_REM:
				CommandQueue.getInstance().addRemTask(commandTypeString, input);
				break;
			case LIST:
				CommandQueue.getInstance().listTask(commandTypeString, input);
				break;
			case CLEAR_ALL:
				CommandQueue.getInstance().clearAllTasks();
				break;
			case CLEAR_SCREEN:
				InputManager.clearScreen();
				break;
			case DELETE:
				CommandQueue.getInstance().deleteTask(commandTypeString, input);
				break;
			case DONE:
				CommandQueue.getInstance().doneTask(commandTypeString, input);
				break;
			case EDIT:
				CommandQueue.getInstance().editTask(commandTypeString, input);
				break;
			case SEARCH:
				CommandQueue.getInstance().searchTask(commandTypeString, input);
				break;
			case STOP:
				CommandQueue.getInstance().stopAlarm(commandTypeString, input);
				break;
			case SHOW_REM:
				CommandQueue.getInstance().showRem();
				break;
			case HELP:
				CommandQueue.getInstance().Help();
				break;
			case EXIT:
				CommandQueue.getInstance().Exit();
				break;
			case REDO:
				CommandQueue.getInstance().redoTask();
				break;
			case UNDO:
				CommandQueue.getInstance().undoLast();
				break;
			default:
				invalidCommand(commandTypeString);
				break;
		}
	}

	private static String flexiCommand(String input) {
		hasCheckedFlexi = true; 
		CommandType command = CommandQueue.findFlexi(input);
		logger.info("Flexicommands: " + command.toString());
		
		if (isValidCommandType(command)){
			String inputString = replaceCommandWord(input, command);
			performCommand(command, inputString, input);
			
			return command.toString() + " " + inputString;
		} else {
			//throw new EmptyStringException();
			invalidCommand(input);
			return command.toString();
		}

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
