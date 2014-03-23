package com.taskpad.input;

import java.util.Map;

import com.taskpad.input.CommandTypes.CommandType;

public class CommandQueue {
	
	private static final String SPACE = " ";

	private CommandQueue(){
	}
	
	public void getInstance(String command){
		
		
	}
	
	public static CommandType find(String inputCommand){
		String variations[];

		for (Map.Entry<CommandType, String[]> entry : CommandTypes.commandVariations.entrySet()){
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

		for (Map.Entry<CommandType, String[]> entry : CommandTypes.commandVariations.entrySet()){
			variations = entry.getValue();
			for (int i=0; i<variations.length; i++){
				if (isInputSubstring(variations[i], input)){
					return (CommandType) entry.getKey();
				}
			}
		}
		
		return CommandType.INVALID;
	}
	
	private static boolean isInputSubstring(String value, String input){
		String[] splitInput = input.split(SPACE);
		
		for (int i=0; i<splitInput.length; i++){
			if (splitInput[i].toUpperCase().equals(value)){
				return true;
			}
		}
		return false;
	}
	
	/* deprecated
	private static boolean isContainsInput(String value, String input){
		return input.toUpperCase().contains(value);
	}
	*/
	
	private static boolean isValueInputCommand(String value, String inputCommand) {
		inputCommand = inputCommand.trim();
		if (value.equalsIgnoreCase(inputCommand)){
			return true;
		}
		
		return false;
	}
	
}
