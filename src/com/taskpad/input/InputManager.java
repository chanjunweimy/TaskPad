//@author A0119646X

/** This class is used for calling APIs from other packages
 * 
 */

package com.taskpad.input;

import java.awt.Color;
import java.util.ArrayList;
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
	
	private static boolean _isDebug = false;
	private static ArrayList<Input> _debugStor = null;
	
	protected static final Logger LOGGER = Logger.getLogger("TaskPad");

	
	public static String receiveFromGui(String inputString){
		String outputString = STRING_NULL;
		outputString = InputMain.receiveInput(inputString);
		return outputString;
	}

	protected static String outputToGui(String outputString){
		if (!_isDebug){
			GuiManager.callOutput(outputString);
		}else {
			System.out.println(String.format(STATUS_GUI_OUTPUT, outputString));
		}
		return String.format(STATUS_GUI_OUTPUT, outputString);
	}
	
	protected static String outputFormatString(String outputString, Color c, boolean isBold){
		GuiManager.showSelfDefinedMessage(outputString, c, isBold);
		return String.format(STATUS_GUI_OUTPUT, outputString);
	}
	
	protected static String callGuiExit(){
		GuiManager.callExit();
		return STATUS_EXIT;
	}
	
	protected static String clearScreen(){
		if (!_isDebug){
			GuiManager.clearOutput();
		}else {
			System.out.println(STATUS_CLEAR);
		}
		return STATUS_CLEAR;
	}
	
	protected static String getStartDateAndTimeForTask(int taskId) throws InvalidTaskIdException{
		if (_isDebug && _debugStor != null){
			taskId--;
			if (taskId >= _debugStor.size() || taskId < 0){
				throw new InvalidTaskIdException();
			}
			
			Input storedID = _debugStor.get(taskId);
			String startDate = storedID.getParameters().get("START DATE");
			String startTime = storedID.getParameters().get("START TIME");
			if (startDate == null || startTime == null){
				return null;
			}
			
			return startDate + " " + startTime;
		}
		
		return ExecutorManager.getStartDateAndTimeForTask(taskId);
	}
	
	protected static String getEndDateAndTimeForTask(int taskId) throws InvalidTaskIdException{
		if (_isDebug && _debugStor != null){
			taskId--;
			if (taskId >= _debugStor.size() || taskId < 0){
				throw new InvalidTaskIdException();
			}
			
			Input storedID = _debugStor.get(taskId);
			String endDate = storedID.getParameters().get("END DATE");
			String endTime = storedID.getParameters().get("END TIME");
			if (endDate == null || endTime == null){
				return null;
			}
			
			return endDate + " " + endTime;
		}
		
		return ExecutorManager.getEndDateAndTimeForTask(taskId);
	}
	
	protected static String getDeadlineForTask(int taskId) throws InvalidTaskIdException{
		if (_isDebug && _debugStor != null){
			taskId--;
			if (taskId >= _debugStor.size() || taskId < 0){
				throw new InvalidTaskIdException();
			}
			
			Input storedID = _debugStor.get(taskId);
			String deadline = storedID.getParameters().get("DEADLINE");
			
			return deadline;
		}
		
		return ExecutorManager.getDeadlineForTask(taskId);
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
	
	protected static void passToExecutor(Input input, String fullInput){
		if (!_isDebug){
			ExecutorManager.receiveFromInput(input, fullInput);
			LOGGER.info(STATUS_EXECUTOR);
		} else {
			formatInputForTest(input);
		}
	}
	
	private static void formatInputForTest(Input input){
		input.showAll();
	}
	
	protected static int retrieveNumberOfTasks(){
		return DataManager.retrieveNumberOfTasks();
	}
	
	public static void setDebug(boolean debug){
		_isDebug = debug;
	}
	
	public static void setDebug(boolean debug, ArrayList<Input> debugStor){
		setDebug(debug);
		_debugStor = debugStor;
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
