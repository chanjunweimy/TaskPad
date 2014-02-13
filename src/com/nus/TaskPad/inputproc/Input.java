package com.nus.TaskPad.inputproc;

public class Input {

	private static final String MESSAGE_INVALID_FORMAT = "Invalid Command: %s ";
	
	private static final String[] ADD_COMMANDS = {"ADD", "CREATE", "NEW"};
	private static final String[] LIST_COMMANDS = {"LIST", "SHOW", "DISPLAY"};
	private static final String[] CLEAR_COMMANDS = {"CLEAR", "CLR"};
	private static final String[] UNDO_COMMANDS = {"UNDO"};
	private static final String[] EDIT_COMMANDS = {"EDIT", "MODIFY", "CHANGE"};
	private static final String[] SEARCH_COMMANDS = {"SEARCH", "FIND"};
	private static final String[] HELP_COMMANDS = {"HELP", "MANUAL"};
	private static final String[] EXIT_COMMANDS = {"EXIT", "QUIT"};
	
	enum CommandType{
		ADD, LIST, DELETE, CLEAR, UNDO, EDIT, SEARCH, INVALID, HELP, EXIT
	};
	
	public static void receiveInput(String input){
		String commandTypeString = parseInput(input);
//		CommandType commandType = determineCommandType(commandTypeString);
		
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

	private static String parseInput(String input) {
//		if (isInvalidCommand(input)){
//			return invalidInput(input);
//		}
		
		String commandTypeString = getFirstWord(input);
		return commandTypeString;			
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
