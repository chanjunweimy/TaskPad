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
		
	protected static Logger _logger = Logger.getLogger("TaskPad");
	
	private static final String STRING_SPACE = " ";
	private static final String STRING_NULL = "null";
	
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
		String numberInput = DateAndTimeManager.getInstance().parseNumberString(input, isDateAndTimePreserved);
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
	 * Takes in input string and finds the first integer as taskID
	 * @param input
	 * @return taskID
	 * @throws TaskIDException 
	 */
	protected String findTaskID(String input) throws TaskIDException{
		boolean isDateAndTimePreserved = true;
		String numberInput = DateAndTimeManager.getInstance().parseNumberString(input, isDateAndTimePreserved);

		_logger.info("finding TaskID. Converted to numberInput");
		_logger.info("numberInput is " + numberInput);
		
		input = numberInput;
		fullInput = numberInput;
	
		_logger.info("input is " + input);
		_logger.info("fullInput is " + fullInput);
		
		int taskID = -1;
		String[] splitInput = input.split(STRING_SPACE);
		
		for (int i=0; i<splitInput.length; i++){
			if (taskID == -1){
				try{
					taskID = Integer.parseInt(splitInput[i]);
				} catch (NumberFormatException e){
					//do nothing
				}
			}
		}
				
		_logger.info("taskID is " + taskID);
		
		if (taskID == -1){
			_logger.severe("TASK ID is invalid!");
			throw new TaskIDException();
		}
		
		
		return "" + taskID;
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
			InputManager.outputToGui(token + " is not a valid date!");
			_logger.severe(token + " is not a valid date!");
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
		if (startNo > 1){
			InputManager.outputToGui("WARNING: has " + startNo + " start date and time");
			_logger.warning("WARNING: has " + startNo + " start date and time");
		}
		
		if (endNo > 1){
			InputManager.outputToGui("WARNING: has " + endNo + " end date and time");
			_logger.warning("WARNING: has " + endNo + " end date and time");
		}
		
		if (deadNo > 1){
			InputManager.outputToGui("WARNING: has " + deadNo + " deadline date and time");
			_logger.warning("WARNING: has " + deadNo + " deadline date and time");
		}
	}
	
	/**
	 * Check the deadline and end times are after start times
	 * @throws InvalidTaskIdException
	 */
	protected void checkDeadLineAndEndTime(String startTime, String startDate, String taskID,
			String deadline, String endTime, String endDate) 
			throws InvalidTaskIdException {
		String startEarliest;
		if (startTime != null && startDate != null){
			startEarliest = startDate + STRING_SPACE + startTime;
		} else {
			startEarliest = InputManager.getStartTimeForTask(Integer.parseInt(taskID));
		}
		
		if (deadline != null){
			String tempDeadline = deadline;
			deadline = InputManager.checkDateAndTimeWithStart(startEarliest, deadline);
			
			if (deadline == null){
				InputManager.outputToGui(tempDeadline + " should be later than start time"); 
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
				InputManager.outputToGui(endDate + STRING_SPACE + endTime + " should be later than start time"); 
				endDate = null;
				endTime = null;
			}
		}
		
	}

	
}
