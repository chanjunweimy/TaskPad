package com.taskpad.input;

/** Trying to make Command a Singleton class
 * 
 * @author Lynnette
 */

import java.util.Map;

import com.taskpad.alarm.AlarmManager;
import com.taskpad.input.CommandTypes.CommandType;

public class CommandQueue {
	
	private static final String STRING_SPACE = " ";
	private static final String STRING_EMPTY = "";
	
	private static final String MESSAGE_CONFIRMATION_CLEAR_SCREEN = "Confirm clear screen? (Y/N)";
	private static final String MESSAGE_CONFIRMATION_CLEAR_DATA = "Confirm clear data? (Y/N)";
	
	private static final CommandQueue _commandInstance = new CommandQueue();

	private CommandQueue(){
	}
	
	protected static CommandQueue getInstance(){
		return _commandInstance;		
	}
	
	/* Methods to perform commands */
	
	protected void Add(String input, String fullInput){
		Add add = new Add(input, fullInput);
		add.run();
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
		Help help = new Help();
		help.outputHelp();
	}
	
	protected void Exit(){
		InputManager.callGuiExit();
	}
	
	protected void addPriTask(String input, String fullInput){
		AddPri addPri = new AddPri(input, fullInput);
		addPri.run();
	}

	protected void addInfoTask(String input, String fullInput) {
		Addinfo addinfo = new Addinfo(input, fullInput);
		addinfo.run();
	}
	
	protected void addRemTask(String input, String fullInput){
		Addrem addRem = new Addrem(input, fullInput);
		addRem.run();
	}
	
	protected void listTask(String input, String fullInput){
		List list = new List(input, fullInput);
		list.run();
	}
	
	protected void redoTask(){
		Redo redo = new Redo(STRING_EMPTY, "REDO");
		redo.run();
	}
	
	protected void deleteTask(String input, String fullInput) {
		Delete delete = new Delete(input, fullInput);
		delete.run();
	}

	protected void doneTask(String input, String fullInput) {
		Done done = new Done(input, fullInput);
		done.run();
	}

	protected void clearAllTasksConfirmation(){
		InputManager.outputToGui(MESSAGE_CONFIRMATION_CLEAR_DATA);
	}
	
	protected void clearAllTasks() {
		ClearTasks clearTask = new ClearTasks(STRING_EMPTY, "CLEAR");
		clearTask.run();
	}
	
	protected void clearScreen(){
		InputManager.outputToGui(MESSAGE_CONFIRMATION_CLEAR_SCREEN);
	}
	
	protected void undoLast() {
		Undo undo = new Undo(STRING_EMPTY, "UNDO");
		undo.run();
	}
	
	protected void editTask(String input, String fullInput) {
		Edit edit = new Edit(input, fullInput);
		edit.run();
	}

	protected void searchTask(String input, String fullInput) {
		Search search = new Search(input, fullInput);
		search.run();	
	}
	
	/* Helper methods to find Command Types */
	
	/** 
	 * This method takes in the first word of input and finds the CommandType
	 * 
	 * @param inputCommand
	 * @return CommandType
	 */
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
