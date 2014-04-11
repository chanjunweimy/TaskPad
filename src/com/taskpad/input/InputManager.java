//@author A0119646X

/** This class is used for calling APIs from other packages
 * 
 */

package com.taskpad.input;

import java.awt.Color;
import java.util.logging.Logger;

import com.taskpad.storage.DataManager;
import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.execute.ExecutorManager;
import com.taskpad.execute.InvalidTaskIdException;
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
		if (!debug){
			GuiManager.clearOutput();
		}else {
			System.out.println(STATUS_CLEAR);
		}
		return STATUS_CLEAR;
	}
	
	protected static String getStartTimeForTask(int taskId) throws InvalidTaskIdException{
		return ExecutorManager.getStartTimeForTask(taskId);
	}
	
	protected static String getEndDateAndTimeForTask(int taskId){
		return null;
	}
	
	/**
	 * compare the input date with startTime and now
	 * @param now
	 * @param startEarliest
	 * @param dateLatest
	 * @return Date: null if smaller or equal, original date if bigger
	 */
	protected static String checkDateAndTimeWithStart(String startEarliest,
			String dateLatest) {
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		return datm.checkDateAndTimeWithStart(startEarliest, dateLatest);
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
	
	/**
	 * CompareDateAndTime(String, String) : compare two date and time, 
	 * can accept date only. Return -2, if it is not date or date and time
	 * @param firstDateString the first date you want to compare
	 * @param secondDateString the second date you want to compare
	 * @return int: if int > 0, first is bigger; int < 0, first is smaller; int = 0, both are equal
	 */
	public static int compareDateAndTime(String firstDateString, String secondDateString){
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		return datm.compareDateAndTime(firstDateString, secondDateString);
	}
	
	/**
	 * compareDateAndTime(String) : compare the user's date and today 23:59
	 * can accept date only. Return -2, if it is not date or date and time
	 * @param dateString the date you want to compare
	 * @return int: if int > 0, first is bigger; int < 0, first is smaller; int = 0, both are equal
	 */
	public static int compareDateAndTime(String dateString){
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		return datm.compareDateAndTime(dateString);
	}
	
}
