package com.TaskPad.inputproc;

public class Input {

	private static final String MESSAGE_INVALID_FORMAT = "Invalid Command: %s ";
	private static Command Command;
	
	public Input(){
		Command = new Command();
	}
	
	public static void receiveInput(String input){
		String commandTypeString = parseInput(input);
		Command.CommandType commandType = determineCommandType(commandTypeString);
		
		input = removeFirstWord(commandTypeString);
		
//		switch(commandType){
//			case ADD:
//				addTask(input);
//				break;
//			case LIST:
//				deleteTask(input);
//			case CLEAR:
//				clearAllTasks();
//				break;
//			case UNDO:
//				undoLast();
//				break;
//			case EDIT:
//				editTask(input);
//				break;
//			case SEARCH:
//				searchTask(input);
//				break;
//			case HELP:
//				help();
//				break;
//			case EXIT:
//				exitProgram();
//				break;
//			default:
//				invalidCommand(input);
//		}
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
