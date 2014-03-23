/** This class is used for calling APIs from other packages
 * 
 * @author Lynnette
 *
 */

package com.taskpad.input;

import java.awt.Color;

import com.taskpad.storage.DataManager;
import com.taskpad.execute.ExecutorManager;
import com.taskpad.ui.GuiManager;


public class InputManager {
	
	private static final String STATUS_CLEAR = "Clear GUI Screen";
	private static final String STATUS_EXECUTOR = "Passed to Executor";
	private static final String STATUS_EXIT = "Exit program";
	private static final String STATUS_GUI_OUTPUT = "Output to GUI: %s";
	
	private static final String STRING_NULL = "";
	
	public static void startInputProcessor(){
		new InputMain();
	}
	
	public static String receiveFromGui(String inputString){
		String outputString = STRING_NULL;
		try {
			outputString = InputMain.receiveInput(inputString);
		} catch (EmptyStringException e) {
			//InputManager.outputToGui(MESSAGE_EMPTY_INPUT);
		}
		return outputString;
	}

	public static String outputToGui(String outputString){
		GuiManager.callOutput(outputString);
		return String.format(STATUS_GUI_OUTPUT, outputString);
	}
	
	public static String outputFormatString(String outputString, Color c, boolean isBold){
		GuiManager.showSelfDefinedMessage(outputString, c, isBold);
		return String.format(STATUS_GUI_OUTPUT, outputString);
	}
	
	public static String callGuiExit(){
		GuiManager.callExit();
		return STATUS_EXIT;
	}
	
	public static String clearScreen(){
		GuiManager.clearOutput();
		return STATUS_CLEAR;
	}
	
	public static String passToExecutor(Input input, String fullInput){
		ExecutorManager.receiveFromInput(input, fullInput);
		return STATUS_EXECUTOR;
	}
	
	public static int retrieveNumberOfTasks(){
		return DataManager.retrieveNumberOfTasks();
	}
	
}
