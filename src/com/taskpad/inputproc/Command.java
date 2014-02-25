package com.taskpad.inputproc;

import java.util.HashMap;
import java.util.Map;
//import java.util.ArrayList;

public class Command {
	
	public enum CommandType{
		ADD, ADD_INFO, CLEAR_ALL, DELETE, DONE, EDIT, EXIT, HELP, INVALID, LIST, SEARCH, UNDO  
	};
	
	private static Map<CommandType, String[]> commandVariations = new HashMap<CommandType, String[]>();

	
	public Command(){
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
		putClearVariations();
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
		String[] addVariations = {"ADD", "NEW", "CREATE"};
		commandVariations.put(CommandType.ADD, addVariations);
	}
	
	private static void putAddInfoVariations(){
		String[] addInfoVariations = {"ADDINFO", "ADDDESC", "CREATEDESC"};
		commandVariations.put(CommandType.ADD_INFO, addInfoVariations);
	}
	
	private static void putDeleteVariations(){
		String[] deleteVariations = {"DELETE", "DEL", "REMOVE"};
		commandVariations.put(CommandType.DELETE, deleteVariations);
	}
	
	private static void putDoneVariations(){
		String[] doneVariations = {"DONE", "FINISHED", "COMPLETED"};
		commandVariations.put(CommandType.DONE, doneVariations);
	}
	
	private static void putClearVariations(){
		String[] clearVariations = {"CLEAR", "CLR", "CLEAN"};
		commandVariations.put(CommandType.CLEAR_ALL, clearVariations);
	}
	
	private static void putEditVariations(){
		String[] editVariations = {"EDIT", "CHANGE"};
		commandVariations.put(CommandType.EDIT, editVariations);
	}
	
	private static void putUndoVariations(){
		String[] undoVariations = {"UNDO"};
		commandVariations.put(CommandType.UNDO, undoVariations);
	}
	
	private static void putSearchVariations(){
		String[] searchVariations = {"SEARCH", "FIND"};
		commandVariations.put(CommandType.SEARCH, searchVariations);
	}
	
	private static void putListVariations(){
		String[] listVariations = {"LIST", "LS", "SHOW", "DISPLAY"};
		commandVariations.put(CommandType.LIST, listVariations);
	}
	
	public static void putHelpVariations(){
		String[] helpVariations = {"HELP", "HLP", "MAN"};
		commandVariations.put(CommandType.HELP, helpVariations);
	}
	
	private static void putExitVariations(){
		String[] exitVariations = {"EXIT", "QUIT", "END"};
		commandVariations.put(CommandType.EXIT, exitVariations);
	}
}
