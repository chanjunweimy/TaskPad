/** This class is used for calling APIs from other packages
 * 
 * @author Lynnette
 *
 */

package com.taskpad.input;

import java.awt.Color;
import java.util.logging.Logger;

import com.taskpad.storage.DataManager;
import com.taskpad.execute.ExecutorManager;
import com.taskpad.ui.GuiManager;


public class InputManager {
	
	private static final String STATUS_CLEAR = "Clear GUI Screen";
	private static final String STATUS_EXECUTOR = "Passed to Executor";
	private static final String STATUS_EXIT = "Exit program";
	private static final String STATUS_GUI_OUTPUT = "Output to GUI: %s";
	
	private static final String STRING_NULL = "";
	
	private static boolean debug = false;
	
	protected static Logger logger = Logger.getLogger("TaskPad");

	
	public static String receiveFromGui(String inputString){
		String outputString = STRING_NULL;
		outputString = InputMain.receiveInput(inputString);
		return outputString;
	}

	public static String outputToGui(String outputString){
		if (!debug){
			GuiManager.callOutput(outputString);
		}else {
			System.out.println(String.format(STATUS_GUI_OUTPUT, outputString));
		}
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
	
	public static void passToExecutor(Input input, String fullInput){
		if (!debug){
			ExecutorManager.receiveFromInput(input, fullInput);
			logger.info(STATUS_EXECUTOR);
		} else {
			formatInputForTest(input);
		}
	}
	
	private static void formatInputForTest(Input input){
		input.showAll();
	}
	
	public static int retrieveNumberOfTasks(){
		return DataManager.retrieveNumberOfTasks();
	}
	
	public static void setDebug(boolean debug){
		InputManager.debug = debug;
	}
	
}
