package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;

public class CommandTypes {
	
	//Lynnette: Alarm is added by Jun Wei
	public enum CommandType{
		ADD, ADD_INFO, ADD_REM, ADD_PRI, CLEAR_ALL, CLEAR_SCREEN, 
		DELETE, DONE, EDIT, EXIT, HELP, INVALID, LIST, SEARCH, UNDO,
		ALARM
	};
	
	private static Map<CommandType, String[]> commandVariations = new HashMap<CommandType, String[]>();

	public CommandTypes(){
		createHashMap();
	}
	
	public static CommandType find(String inputCommand){
		String variations[];

		for (Map.Entry<CommandType, String[]> entry : commandVariations.entrySet()){
			variations = entry.getValue();
			for (int i=0; i<variations.length; i++){
				if (isValueInputCommand(variations[i], inputCommand)){
					return (CommandType) entry.getKey();
				}
			}
		}
		
		return CommandType.INVALID;
	}
	
	public static CommandType findFlexi(String input){
		String variations[];
		
		for (Map.Entry<CommandType, String[]> entry : commandVariations.entrySet()){
			variations = entry.getValue();
			for (int i=0; i<variations.length; i++){
				if (isContainsInput(variations[i], input)){
					return (CommandType) entry.getKey();
				}
			}
		}
		
		return CommandType.INVALID;
	}
	
	private static boolean isContainsInput(String value, String input){
		return input.toUpperCase().contains(value);
	}
	
	private static boolean isValueInputCommand(String value, String inputCommand) {
		inputCommand = inputCommand.trim();
		if (value.equalsIgnoreCase(inputCommand)){
			return true;
		}
		
		return false;
	}

	private static void createHashMap(){
		putAddVariations();
		putAddInfoVariations();
		putAddRemVariations();
		putAddPriVariations();
		putAlarmVariations();
		putClearVariations();
		putClearScreenVariations();
		putDeleteVariations();
		putDoneVariations();
		putEditVariations();
		putExitVariations();
		putHelpVariations();
		putListVariations();
		putSearchVariations();
		putUndoVariations();
	}
	
	/* Helper methods for creating the hashmap */
	
	private static void putAddVariations(){
		String[] addVariations = {"ADD", "NEW", "CREATE", "INSERT"};
		commandVariations.put(CommandType.ADD, addVariations);
	}
	
	private static void putAddInfoVariations(){
		String[] addInfoVariations = {"INFO", "INFORMATION", "CREATEDESC"};
		commandVariations.put(CommandType.ADD_INFO, addInfoVariations);
	}
	
	private static void putAddRemVariations(){
		String[] addRemVariations = {"REM", "REMINDER", "REMIND", "REMAINDER"};
		commandVariations.put(CommandType.ADD_REM, addRemVariations);
	}
	
	private static void putAddPriVariations(){
		String[] addPriVariations = {"ADDPRI", "ADDPRIORITY", "PRI", "PRIORITY"};
		commandVariations.put(CommandType.ADD_PRI, addPriVariations);
	}
	
	private static void putAlarmVariations() {
		String[] exitVariations = {"ALARM", "ADDALARM", "SETALARM", "SETTIMER", "RING"};
		commandVariations.put(CommandType.ALARM, exitVariations);
	}
	
	private static void putDeleteVariations(){
		String[] deleteVariations = {"DELETE", "DEL", "REMOVE", "REM"};
		commandVariations.put(CommandType.DELETE, deleteVariations);
	}
	
	private static void putDoneVariations(){
		String[] doneVariations = {"DONE", "FINISHED", "COMPLETED", "FINISH", "COMPLETE"};
		commandVariations.put(CommandType.DONE, doneVariations);
	}
	
	private static void putClearVariations(){
		String[] clearVariations = {"CLEAR", "CLR", "CLEAN", "CLC"};
		commandVariations.put(CommandType.CLEAR_ALL, clearVariations);
	}
	
	private static void putClearScreenVariations(){
		String[] clearScreenVariations = {"CLEARSCR", "CLEARSCREEN", "CLEARSC", "CLCSR", "SCREEN"};
		commandVariations.put(CommandType.CLEAR_SCREEN, clearScreenVariations);
	}
	
	private static void putEditVariations(){
		String[] editVariations = {"EDIT", "CHANGE"};
		commandVariations.put(CommandType.EDIT, editVariations);
	}
	
	private static void putUndoVariations(){
		String[] undoVariations = {"UNDO", "U"};
		commandVariations.put(CommandType.UNDO, undoVariations);
	}
	
	private static void putSearchVariations(){
		String[] searchVariations = {"SEARCH", "FIND"};
		commandVariations.put(CommandType.SEARCH, searchVariations);
	}
	
	private static void putListVariations(){
		String[] listVariations = {"LIST", "LS", "SHOW", "DISPLAY", "LST"};
		commandVariations.put(CommandType.LIST, listVariations);
	}
	
	public static void putHelpVariations(){
		String[] helpVariations = {"HELP", "HLP", "MAN"};
		commandVariations.put(CommandType.HELP, helpVariations);
	}
	
	private static void putExitVariations(){
		String[] exitVariations = {"EXIT", "QUIT", "END", "CLOSE", "SHUTDOWN"};
		commandVariations.put(CommandType.EXIT, exitVariations);
	}

}
