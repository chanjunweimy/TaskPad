//@author A0119646X

/* 
 * Abstract class for processing the commands 
 */

package com.taskpad.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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
	
	protected static final Logger LOGGER = Logger.getLogger("TaskPad");
	
	private static final String STRING_SPACE = " ";
	private static final String STRING_NULL = "null";
	private static final String STRING_EMPTY = "";
	private static final String STRING_QUOTE = "\"";
	
	protected static final String KEYWORD_ENDTiME = "TO";
	protected static final String KEYWORD_STARTTIME = "FROM";
	protected static final String KEYWORD_DEADLINE = "BY";
	
	protected static final int POSITION_TIME_ENDTIME = 1;
	protected static final int POSITION_DATE_ENDTIME = 2;
	protected static final int POSITION_TIME_STARTTIME = 3;
	protected static final int POSITION_DATE_STARTTIME = 4;
	protected static final int POSITION_TIME_DEADLINE = 5;
	protected static final int POSITION_DATE_DEADLINE = 6;
	
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
		
		String numberInput = STRING_EMPTY;
		try {
			numberInput = DateAndTimeManager.getInstance().parseNumberString(input);
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
			LOGGER.info("numberInput is " + numberInput);
			String[] inputTokens = numberInput.split(" ");
			
			for (int i = 0; i < inputTokens.length - 1; i++){
				if (inputTokens[i] == null){
					continue;
				} else if ("".equals(inputTokens[i])){
					inputTokens[i] = null;
					continue;
				} else if ("-".equals(inputTokens[i])){
					switch (inputTokens[i + 1]){
					case "d":
					case "s":
					case "e":
					case "t":
						inputTokens[i] = inputTokens[i] + inputTokens[i + 1];
						inputTokens[i + 1] = null;
						break;
					}
				}
			}
			  
			input = buildString(inputTokens);
			
			LOGGER.info("input is " + input);
		}
	}

	/**
	 * @param inputTokens
	 * @return
	 */
	protected String buildString(String[] inputTokens) {
		StringBuffer inputBuilder = new StringBuffer();
		for (int i = 0; i < inputTokens.length; i++){
			if (inputTokens[i] != null){
				inputBuilder.append(inputTokens[i] + STRING_SPACE);
			}
		}
		return inputBuilder.toString().trim();
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
		
		LOGGER.info("Input object created, command: " + inputObject.getCommand());
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
		
		LOGGER.info("Input object passed to executor");
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
	
	/* Helper methods to process date and times in strings For Edit and Add */
	
	/**
	 * findDateOrTime: helper method of getDateAndTimeValue,
	 * it uses formatDateAndTimeInString to get all the date and time
	 * @param fullInput
	 * @return
	 */
	protected String findDateOrTime(String fullInput) {
		LOGGER.info("findDateOrTime...");
		String formatInput = null;
		try {
			formatInput = DateAndTimeManager.getInstance().formatDateAndTimeInString(fullInput);
		} catch (InvalidQuotesException e) {
			LOGGER.severe("ERROR!! " + e.getMessage());
			InputManager.outputToGui(e.getMessage());
		}

		LOGGER.info("format input is: " + formatInput);
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
		
		LOGGER.info("getting date and time...");
		LOGGER.info("dateString is: " + dateString);
		LOGGER.info("timeString is: " + timeString);
		LOGGER.info("description is: " + splitResult[0]);
		
		boolean isDescNotNull = splitResult.length > 6 && !splitResult[0].trim().isEmpty();
		if (STRING_NULL.equals(dateString) || STRING_NULL.equals(timeString) || isDescNotNull){
			String errorMessage = String.format(MESSAGE_INVALID_DATE, token);
			InputManager.outputToGui(errorMessage);
			LOGGER.severe(errorMessage);
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
			errorMessage = String.format(MESSAGE_WARNING_STARTDATETIME, STRING_EMPTY + startNo);
			InputManager.outputToGui(errorMessage);
			//InputManager.outputToGui("WARNING: has " + startNo + " start date and time");
			LOGGER.warning(errorMessage);
		}
		
		if (endNo > 1){
			errorMessage = String.format(MESSAGE_WARNING_ENDDATETIME, STRING_EMPTY + endNo);
			InputManager.outputToGui(errorMessage);
			LOGGER.warning(errorMessage);
		}
		
		if (deadNo > 1){
			errorMessage = String.format(MESSAGE_WARNING_DEADLINE, STRING_EMPTY + deadNo);
			InputManager.outputToGui(errorMessage); 
			LOGGER.warning(errorMessage);
		}
	}
	
	/**
	 * Check the deadline and end times are after start times
	 * @throws InvalidTaskIdException
	 */
	protected ArrayList<String> checkDeadLineAndEndTime(String startTime, String startDate, String taskID,
			String deadline, String endTime, String endDate, boolean isEdit) 
			throws InvalidTaskIdException {
		
		LOGGER.info("deadline is " + deadline);
		LOGGER.info("startDate is " + startDate);
		LOGGER.info("startTime is " + startTime);
		LOGGER.info("endDate is " + endDate);
		LOGGER.info("endTime is " + endTime);
		LOGGER.info("taskID is " + taskID);
		LOGGER.info("isEdit? " + isEdit);
		
		String startEarliest = null;
		if (startTime != null && startDate != null){
			startEarliest = startDate + STRING_SPACE + startTime;
			startEarliest = startEarliest.trim();
			
			LOGGER.info("startEarliest is " + startEarliest); 
			
			if (!startEarliest.isEmpty()){
				boolean isDeadline = true;
				startEarliest = checkStartEarliest(taskID, deadline, isEdit,
						startEarliest, isDeadline);
				LOGGER.info("Checked with deadline: startEarliest is " + startEarliest); 
				
				if (startEarliest != null){
					if (endDate == null || endTime == null){
						isDeadline = false;
						String endDateAndTime = null;
						LOGGER.info("Checked with endDate and endTime: startEarliest is " + startEarliest); 
						startEarliest = checkStartEarliest(taskID, endDateAndTime, isEdit,
								startEarliest, isDeadline);
					}
					
					if (startEarliest == null){
						LOGGER.info("startDate ( " + startDate + " ) and startTime ( " + startTime + " ) is invalid");
						
						startDate = null;
						startTime = null;
					}
				} else {
					LOGGER.info("startDate ( " + startDate + " ) and startTime ( " + startTime + " ) is invalid");
					
					startDate = null;
					startTime = null;
				}
				
			}
			
			
		} 
		
		if (isEdit && startEarliest == null){
			//For editing task, it has a task ID stored
			startEarliest = InputManager.getStartDateAndTimeForTask(Integer.parseInt(taskID));
		}

		LOGGER.info("startEarliest is " + startEarliest);
		LOGGER.info("deadline is " + deadline);
		
		if (deadline != null){
			String tempDeadline = deadline;
			deadline = InputManager.checkDateAndTimeWithStart(startEarliest, tempDeadline);
			
			if (deadline == null){
				InputManager.outputToGui(String.format(MESSAGE_DEADLINE_STARTTIME,tempDeadline));
			} else {
				deadline = deadline.trim();
			}
		}
		LOGGER.info("deadline is " + deadline);
		LOGGER.info("endLatest is " + endDate + STRING_SPACE + endTime);
		
		String endLatest = null;
		if (endTime != null && endDate != null){
			endLatest = endDate + STRING_SPACE + endTime;
			endLatest = InputManager.checkDateAndTimeWithStart(startEarliest, endLatest);
			
			if (endLatest == null){
				InputManager.outputToGui(String.format(MESSAGE_ENDDATE_STARTTIME, endDate, endTime));
			} else {
				endLatest = endLatest.trim();
			}
			
			/*
			if (endLatest != null){
				String[] endTokens = endLatest.split(STRING_SPACE);
				int datePos = 0;
				int timePos = 1;

				endDate = endTokens[datePos];
				endTime = endTokens[timePos];
			} else {
				InputManager.outputToGui(String.format(MESSAGE_ENDDATE_STARTTIME, endDate, endTime));
				//InputManager.outputToGui(endDate + STRING_SPACE + endTime + " should be later than start time"); 
				endDate = null;
				endTime = null;
			}
			*/
			
		}
		LOGGER.info("endLatest is " + endLatest);
		
		if (startDate == null || startTime == null){
			startEarliest = null;
		}
		
		ArrayList<String> times = new ArrayList<String>();
		times.add(endLatest);
		times.add(startEarliest);
		times.add(deadline);
		
		return times;
	}

	/**
	 * 
	 * @param taskID
	 * @param dateAndTime
	 * @param isEdit
	 * @param startEarliest
	 * @param isDeadline
	 * 						: true if it is deadline, false if it is endtime
	 * @return
	 * @throws InvalidTaskIdException
	 */
	private String checkStartEarliest(String taskID, String dateAndTime,
			boolean isEdit, String startEarliest, boolean isDeadline) throws InvalidTaskIdException {
		if (isEdit){
			LOGGER.info("isEdit, checking whether startEarliest( " + startEarliest + " ) is valid");
			//use existing deadline, then start time cannot be first
			if (dateAndTime == null){
				String existDateAndTime;
				if (isDeadline){
					existDateAndTime = InputManager.getDeadlineForTask(Integer.parseInt(taskID));
				} else {
					existDateAndTime = InputManager.getEndDateAndTimeForTask(Integer.parseInt(taskID));
				}
				LOGGER.info("isDeadline is " + isDeadline);
				LOGGER.info("existDateAndTime is " + existDateAndTime);
				
				if (existDateAndTime == null || existDateAndTime.trim().isEmpty()){
					return startEarliest;
				}
				
				switch (InputManager.compareDateAndTime(startEarliest, existDateAndTime)){
				case -2:
					assert (false);
					break;
					
				case -1:
					//it is valid if it is -1
					LOGGER.info("valid startEarliest");
					break;
					
				case 0:
				case 1:
					if (isDeadline){
						InputManager.outputToGui("startDateAndTime ( " + startEarliest + " ) is "
							+ "earlier than existing deadline : " + existDateAndTime);
					} else {
						InputManager.outputToGui("startDateAndTime ( " + startEarliest + " ) is "
								+ "earlier than existing end date and end time : " + existDateAndTime);
					}
					startEarliest = null;
					LOGGER.info("invalid startEarliest");
					break;
				}
			}
		}
		return startEarliest;
	}
	
	/**
	 * putDescInQuotesFirst: find description within " "
	 * @return input string without description or empty string if " " not found
	 */
	protected  String putDescInQuotesFirst(String input){
		Scanner sc = new Scanner(input);
		
		StringBuffer tempDesc = null;
		StringBuffer normalString = new StringBuffer(STRING_EMPTY);
		boolean isStarted = false;
		boolean isFinish = false;
		
		while (sc.hasNext()){
			String buildString = sc.next();
			if (!isFinish){
				if (!isStarted){
					if (buildString.startsWith(STRING_QUOTE)){
						isStarted = true;
						tempDesc = new StringBuffer(buildString);
					} else {
						normalString.append(STRING_SPACE + buildString);
					}
				} else {
					//System.out.println(buildString);
					tempDesc.append(STRING_SPACE + buildString);
					if (buildString.endsWith(STRING_QUOTE)){
						isFinish = true;
					}
				}
			} else {
				normalString.append(STRING_SPACE + buildString);
			}
		}
		
		if (tempDesc == null){
			tempDesc = new StringBuffer(STRING_EMPTY);
			return tempDesc.toString();
		} else {
			//putOneParameter(PARAMETER_DESCRIPTION, tempDesc.toString());
			tempDesc.append(normalString);
		}
	
		sc.close();
		
		//System.out.println(tempDesc.toString());
		
		return tempDesc.toString();
	}
	
	protected String putDeadline(String param) {	
		System.out.println("HELLOHELLO");
		System.out.println("Param " + param);
		if (param.trim().isEmpty()){
			return null;
		}
		
		String token = KEYWORD_DEADLINE + STRING_SPACE + param.trim();
		String tempDead = getDateAndTimeValue(token, POSITION_DATE_DEADLINE , POSITION_TIME_DEADLINE);
		if (tempDead == null){
			return null;
		}
		
		return tempDead;
	}
	
	protected String putStartTime(String param) {
		if (param.trim().isEmpty()){
			return null;
		}
		
		String token = KEYWORD_STARTTIME + STRING_SPACE + param.trim();
		String startResult = getDateAndTimeValue(token, POSITION_DATE_STARTTIME , POSITION_TIME_STARTTIME);

		if (startResult == null){
			return null;
		}
		
		return startResult;
	}
	
	protected String putEndTime(String param) {
		if (param.trim().isEmpty()){
			return null;
		}
		String token = KEYWORD_ENDTiME + STRING_SPACE + param.trim();
		String endResult = getDateAndTimeValue(token, POSITION_DATE_ENDTIME , POSITION_TIME_ENDTIME);

		if (endResult == null){
			return null;
		}
		
		return endResult;
	}
	
	protected String removeFirstChar(String input) {
		return input.replaceFirst(getFirstChar(input), STRING_EMPTY).trim();
	}
	
	protected String getFirstChar(String input) {
		String firstChar = input.trim().split("\\s+")[0];
		return firstChar;
	}
	
}
