//@author A0119646Xv

package com.taskpad.input;

/** Trying to make Command a Singleton class
 * 
 */

//@author A0119646X

import java.awt.Color;
import java.util.Map;

import com.taskpad.alarm.AlarmManager;
import com.taskpad.execute.ExecutorManager;
import com.taskpad.input.CommandTypes.CommandType;

public class CommandQueue {
	
	private static final String STRING_SPACE = " ";
	private static final String STRING_EMPTY = "";
	
	private static final String MESSAGE_TODAYS_REMINDERS = "\nToday's Reminders: ";
	
	private static final CommandQueue _commandInstance = new CommandQueue();

	private CommandQueue(){
	}
	
	protected static CommandQueue getInstance(){
		return _commandInstance;		
	}
	
	/* Methods to perform commands */
	
	protected void Add(String input, String fullInput){
		new Add(input, fullInput);
	}
	
	protected void Alarm(String input, String fullInput){
		new Alarm(input, fullInput);
	}
	
	protected void stopAlarm(String input, String fullInput){
		try {
			AlarmManager.turnOffAlarm();
		} catch (Exception e) {
			return;
		}
	}
	
	protected void Help(){
		new Help();
	}
	
	protected void Exit(){
		InputManager.callGuiExit();
	}
	
	protected void addPriTask(String input, String fullInput){
		new AddPri(input, fullInput);
	}

	protected void addInfoTask(String input, String fullInput) {
		new Addinfo(input, fullInput);
	}
	
	protected void addRemTask(String input, String fullInput){
		new Addrem(input, fullInput);
	}
	
	protected void listTask(String input, String fullInput){
		new List(input, fullInput);
	}
	
	protected void redoTask(){
		new Redo(STRING_EMPTY, "REDO");
	}
	
	protected void deleteTask(String input, String fullInput) {
		new Delete(input, fullInput);
	}

	protected void doneTask(String input, String fullInput) {
		new Done(input, fullInput);
	}
	
	protected void clearAllTasks() {
		new ClearTasks(STRING_EMPTY, "CLEAR");
	}

	
	protected void undoLast() {
		new Undo(STRING_EMPTY, "UNDO");
	}
	
	protected void editTask(String input, String fullInput) {
		new Edit(input, fullInput);
	}

	protected void searchTask(String input, String fullInput) {
		new Search(input, fullInput);
	}
	
	protected void showRem(){
		InputManager.outputFormatString(MESSAGE_TODAYS_REMINDERS, Color.RED, true);
		ExecutorManager.showReminder();
	}
	
	/* Helper methods to find Command Types */
	
	/** 
	 * This method takes in the first word of input and finds the CommandType
	 * 
	 * @param inputCommand
	 * @return CommandType
	 */
	public static CommandType find(String inputCommand){
		//new CommandTypes();
		CommandTypes.getInstance();
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
	
	
	/** 
	 * This method takes in the whole input and finds the CommandType
	 * 
	 * @param inputCommand
	 * @return CommandType
	 */
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
		String[] splitInput = input.split(STRING_SPACE);
		for (int i=0; i<splitInput.length; i++){
			if (splitInput[i].toUpperCase().equals(value)){
				//System.out.println(splitInput[i] + " " + value);
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
