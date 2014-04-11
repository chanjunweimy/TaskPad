//@author A0119646X

/* 
 * Abstract class for processing the commands 
 */

package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidQuotesException;
import com.taskpad.execute.InvalidTaskIdException;

public abstract class Command {

	protected static Input inputObject;
	protected static Map<String, String> inputParameters;
	protected static String input;
	protected static String fullInput;
	
	protected static int NUMBER_ARGUMENTS;
	protected static String COMMAND;
	
	protected static final String MESSAGE_EMPTY_INPUT = "Error: Empty Input";
	protected static final String MESSAGE_INVALID_INPUT = "Error: Invalid input: %s";
	protected static final String MESSAGE_INVALID_PARAMETER_NUMBER = "Error: Invalid number of parameters.\nType help if you need! :)";
		
	protected static final String MESSAGE_DEADLINE_STARTTIME = "%s should be later than start time";
	protected static final String MESSAGE_ENDDATE_STARTTIME = "%s %s should be later than start time";
	protected static final String MESSAGE_WARNING_STARTDATETIME ="WARNING: has %s start date and time";
	protected static final String MESSAGE_WARNING_ENDDATETIME = "WARNING: has %s end date and time";
	protected static final String MESSAGE_WARNING_DEADLINE = "WARNING: has %s deadline";
	protected static final String MESSAGE_INVALID_DATE = "%s is not a valid date";
	
	protected static Logger _logger = Logger.getLogger("TaskPad");
	
	private static final String STRING_SPACE = " ";
	private static final String STRING_NULL = "null";
	private static final String STRING_EMPTY = "";
	
	public Command(String input, String fullInput){
		Command.fullInput = fullInput;
		Command.input = input;
		inputParameters = new HashMap<String,String>();
		initialiseOthers();
		run();
	}
	
	protected abstract void initialiseOthers();

	public void run() {
		input = input.trim();
		try {
			checkIfEmptyString();
		} catch (EmptyStringException e) {
			showEmptyString();
			return;
		}
		
		boolean isDateAndTimePreserved = true;
		String numberInput = STRING_EMPTY;
		try {
			numberInput = DateAndTimeManager.getInstance().parseNumberString(input, isDateAndTimePreserved);
		} catch (InvalidQuotesException e1) {
			InputManager.outputToGui(e1.getMessage());
			return;
		}
		//System.out.println(numberInput);
		checkIfNumberInputEmpty(numberInput);
				
		try {
			checkIfIncorrectArguments();
		} catch (TaskIDException | InvalidParameterException e) {
			//showIncorrectArguments();
			InputManager.outputToGui(e.getMessage());
			return;
		}
		
		clearInputParameters();
		initialiseParametersToNull();
		
		if (commandSpecificRun()){
			createInputObject();
			passObjectToExecutor();
		} else {
			return;
		}
	}

	private void checkIfNumberInputEmpty(String numberInput) {
		numberInput = numberInput.trim();
		if (!numberInput.equals("") || numberInput != null){
			input = numberInput;
		}
	}
	
	protected void showEmptyString(){
		String errorMessage = String.format(MESSAGE_EMPTY_INPUT);
		InputManager.outputToGui(errorMessage);
	}

	protected void showNoDesc() {
		String errorMessage = String.format(MESSAGE_INVALID_INPUT, input);
		InputManager.outputToGui(errorMessage);
	}
	
	protected abstract boolean commandSpecificRun();
	
	protected boolean checkIfEmptyString() throws EmptyStringException {
		
		if(isEmptyString()){
			throw new EmptyStringException();
//			InputManager.outputToGui(MESSAGE_EMPTY_INPUT);
//			return true;
		}
		return false;
	}
	
	protected abstract void initialiseParametersToNull();
	
	protected boolean checkIfIncorrectArguments() throws TaskIDException, InvalidParameterException{
		String inputString[] = input.split(" ");
		
		if (isNotNumberArgs(inputString)){
			throw new InvalidParameterException();
		}
		
		if(isNotValidTaskID(inputString[0])){
			throw new TaskIDException(inputString[0]);
		}
		
		return false;
	}
	
	protected boolean isNotValidTaskID(String taskID){
		if(isNotInteger(taskID) || isInvalidID(taskID)){
			//outputIdError();
			return true;
		}	
		return false; 
	}
	
	protected Input createInputObject() {
		//clearInputParameters();	
		putInputParameters();
		inputObject = new Input(getCOMMAND(), inputParameters);	
		
		//inputObject.showAll();
		
		_logger.info("Input object created, command: " + inputObject.getCommand());
		return inputObject;
	}

	protected abstract void putInputParameters();
	
	protected static void clearInputParameters(){
		inputParameters.clear();
	}
	
	protected boolean isEmptyString(){
		if (input.isEmpty()){
			return true;
		}
		return false;
	}
	
	protected void putOneParameter(String parameter, String input){
		inputParameters.put(parameter, input);
	}
	
	protected void passObjectToExecutor(){
		assert (inputObject.getParameters().size() != 0);
		InputManager.passToExecutor(inputObject, fullInput);
		
		clearInputParameters();
		
		_logger.info("Input object passed to executor");
	}
	
	protected boolean isNotInteger(String input){
		try{
			Integer.parseInt(input);
		} catch (NumberFormatException e){
			return true;
		}
		return false;
	}
	
	protected boolean isInvalidID(String input){
		input = input.trim();
		int inputNum = Integer.parseInt(input.trim());
		if (inputNum > InputManager.retrieveNumberOfTasks() || inputNum < 0){
			return true;
		}
		return false;
	}
	
	protected boolean isNotNumberArgs(String[] inputString){
		if (inputString.length != getNUMBER_ARGUMENTS()){
			return true;
		}
		return false;
	}
	
	protected void invalidParameterError(){
		String errorMessage = String.format(MESSAGE_INVALID_PARAMETER_NUMBER);
		InputManager.outputToGui(errorMessage);
	}
	
	protected void outputIdError() {
		String errorMessage = String.format(MESSAGE_INVALID_INPUT, input);
		InputManager.outputToGui(errorMessage);
	}

	protected int getNUMBER_ARGUMENTS() {
		return NUMBER_ARGUMENTS;
	}

	protected void setNUMBER_ARGUMENTS(int nUMBER_ARGUMENTS) {
		NUMBER_ARGUMENTS = nUMBER_ARGUMENTS;
	}

	protected String getCOMMAND() {
		return COMMAND;
	}

	protected void setCOMMAND(String cOMMAND) {
		COMMAND = cOMMAND;
	}
	
	/* Helper methods to process date and times in strings */
	/**
	 * findDateOrTime: helper method of getDateAndTimeValue,
	 * it uses formatDateAndTimeInString to get all the date and time
	 * @param fullInput
	 * @return
	 */
	protected String findDateOrTime(String fullInput) {
		_logger.info("findDateOrTime...");
		String formatInput = null;
		try {
			formatInput = DateAndTimeManager.getInstance().formatDateAndTimeInString(fullInput);
		} catch (InvalidQuotesException e) {
			_logger.severe("ERROR!! " + e.getMessage());
			InputManager.outputToGui(e.getMessage());
		}

		_logger.info("format input is: " + formatInput);
		return formatInput;
	}
	
	/**
	 * Main logic of getting date and time
	 * @param token
	 * @param splitResult
	 * @param datePos
	 * @param timePos
	 * @return dateString and timeString
	 */
	protected String getDateAndTime(String token, String[] splitResult, int datePos, int timePos) {
		String dateString = splitResult[datePos];
		String timeString = splitResult[timePos];
		
		_logger.info("getting date and time...");
		_logger.info("dateString is: " + dateString);
		_logger.info("timeString is: " + timeString);
		_logger.info("description is: " + splitResult[0]);
		
		boolean isDescNotNull = splitResult.length > 6 && !splitResult[0].trim().isEmpty();
		if (STRING_NULL.equals(dateString) || STRING_NULL.equals(timeString) || isDescNotNull){
			String errorMessage = String.format(token, MESSAGE_INVALID_DATE);
			InputManager.outputToGui(errorMessage);
			_logger.severe(errorMessage);
			return null;
		}
		
		return dateString + " " + timeString;
	}
	
	/**
	 * getDateAndTimeValue: is called by main function when finding
	 * deadlines/starttimes/endtimes and it returns the date and time
	 * @param token
	 * @param datePos
	 * @param timePos
	 * @return date and time if parsed correctly or null there error
	 */
	protected String getDateAndTimeValue(String token, int datePos, int timePos) {
		assert (token != null);
		assert (!token.trim().isEmpty());
		
		String formatInput = findDateOrTime(token);
		
		String[] splitResult = formatInput.split(STRING_SPACE);
		int arrDatePos = splitResult.length - datePos;
		int arrTimePos = splitResult.length - timePos;

		return getDateAndTime(token, splitResult, arrDatePos, arrTimePos);
	}
	
	/**
	 * @param startNo
	 * @param deadNo
	 * @param endNo
	 */
	protected void showErrorWhenActionRepeated(int startNo, int deadNo, int endNo) {
		String errorMessage = STRING_EMPTY;
		
		if (startNo > 1){
			errorMessage = String.format(STRING_EMPTY+startNo, MESSAGE_WARNING_STARTDATETIME);
			InputManager.outputToGui(errorMessage);
			//InputManager.outputToGui("WARNING: has " + startNo + " start date and time");
			_logger.warning(errorMessage);
		}
		
		if (endNo > 1){
			errorMessage = String.format(STRING_EMPTY+endNo, MESSAGE_WARNING_ENDDATETIME);
			InputManager.outputToGui(errorMessage);
			_logger.warning(errorMessage);
		}
		
		if (deadNo > 1){
			errorMessage = String.format(STRING_EMPTY+deadNo + MESSAGE_WARNING_DEADLINE);
			InputManager.outputToGui(errorMessage);
			_logger.warning(errorMessage);
		}
	}
	
	/**
	 * Check the deadline and end times are after start times
	 * @throws InvalidTaskIdException
	 */
	protected void checkDeadLineAndEndTime(String startTime, String startDate, String taskID,
			String deadline, String endTime, String endDate, boolean isExistingTask) 
			throws InvalidTaskIdException {
		
		String startEarliest;
		if (startTime != null && startDate != null){
			startEarliest = startDate + STRING_SPACE + startTime;
		} else {
			if (isExistingTask){
				//For editing task, it has a task ID stored
				startEarliest = InputManager.getStartTimeForTask(Integer.parseInt(taskID));
			} else {
				//for add task, can only start at NOW 
				startEarliest = DateAndTimeManager.getInstance().getTodayDate() + STRING_SPACE +
						DateAndTimeManager.getInstance().getTodayTime();
			}
		}
		
		if (deadline != null){
			String tempDeadline = deadline;
			deadline = InputManager.checkDateAndTimeWithStart(startEarliest, deadline);
			
			if (deadline == null){
				InputManager.outputToGui(String.format(tempDeadline, MESSAGE_DEADLINE_STARTTIME));
				//InputManager.outputToGui(tempDeadline + " should be later than start time"); 
			}
		}
		
		String endLatest = null;
		if (endTime != null && endDate != null){
			endLatest = endDate + STRING_SPACE + endTime;
			endLatest = InputManager.checkDateAndTimeWithStart(startEarliest, endLatest);
			
			if (endLatest != null){
				String[] endTokens = endLatest.split(STRING_SPACE);
				int datePos = 0;
				int timePos = 1;

				endDate = endTokens[datePos];
				endTime = endTokens[timePos];
			} else {
				InputManager.outputToGui(String.format(endDate, endTime, MESSAGE_ENDDATE_STARTTIME));
				//InputManager.outputToGui(endDate + STRING_SPACE + endTime + " should be later than start time"); 
				endDate = null;
				endTime = null;
			}
		}
		
	}
	
}
