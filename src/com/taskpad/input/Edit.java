//@author A0119646X

package com.taskpad.input;



import java.util.logging.Logger;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidQuotesException;

public class Edit extends Command{
	private final static Logger LOGGER = Logger.getLogger("TaskPad");
		
	private static final int NUMBER_ARGUMENTS = 2;
	
	private static final int POSITION_TIME_ENDTIME = 1;
	private static final int POSITION_DATE_ENDTIME = 2;
	private static final int POSITION_TIME_STARTTIME = 3;
	private static final int POSITION_DATE_STARTTIME = 4;
	private static final int POSITION_TIME_DEADLINE = 5;
	private static final int POSITION_DATE_DEADLINE = 6;

	private static final String COMMAND_EDIT = "EDIT";
	
	private static String PARAMETER_TASK_ID = "TASKID";
	private static String PARAMETER_DESC = "DESC";
	private static String PARAMETER_DEADLINE = "DEADLINE";
	private static String PARAMETER_START_DATE = "START DATE";
	private static String PARAMETER_START_TIME = "START TIME";
	private static String PARAMETER_END_DATE = "END DATE";
	private static String PARAMETER_END_TIME = "END TIME";
	
	private String _taskID;
	private String _desc;
	private String _deadline;
	private String _startDate;
	private String _startTime;
	private String _endDate;
	private String _endTime;
	
	private static final String STRING_SPACE = " ";
	private static final String STRING_COMMA = ",";
	private static final String STRING_NULL = "null";
	private static final String STRING_EMPTY = "";

	private static final String KEYWORD_ENDTiME = "TO";
	private static final String KEYWORD_STARTTIME = "FROM";
	private static final String KEYWORD_DEADLINE = "BY";

	public Edit(String input, String fullInput) {
		super(input, fullInput);
	}
	
	@Override
	protected void initialiseOthers(){
		LOGGER.info("initializing...");
		LOGGER.info("NUMBER_ARGUMENTS: " + NUMBER_ARGUMENTS);
		LOGGER.info("COMMAND_EDIT: " + COMMAND_EDIT);
		
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_EDIT);
		
		_taskID = null;
		_desc = null;
		_deadline = null;
		_startDate = null;
		_startTime = null;
		_endDate = null;
		_endTime = null;
	}

	@Override
	protected boolean commandSpecificRun() {
		clearInputParameters();

		//String[] splitParams = input.split(" ");
		
		//_taskID = splitParams[0].trim();
		
		LOGGER.info("Got in commandSpecificRun ... ");
		
		try {
			_taskID = findTaskID(fullInput);
		} catch (TaskIDException e) {
			InputManager.outputToGui(e.getMessage());
			return false;
		}
		
		LOGGER.info("taskID is " + _taskID);
		
		getOtherKeysValue();
		
		/*
		if (isDesc()){
			fullInput = removeWordDesc(fullInput);
			_desc = removeTaskID(fullInput, _taskID);
		} else if (isDeadline()){
			fullInput = removeWordDeadline(fullInput);
			_deadline = findDeadline(fullInput);
		} else {
			//no tag, just assume description
			_desc = removeTaskID(fullInput, _taskID);
		}
		*/
		
		//System.out.println("Desc: " + _desc + "deadline: " + _deadline);
		
		putInputParameters();
		return true;
	}

	/**
	 * 
	 */
	private void getOtherKeysValue() {
		String inputString = fullInput;
		inputString = removeTaskID(inputString, _taskID);
		
		LOGGER.info("getting other parameters value (exclude ID).");
		LOGGER.info("inputString is " + inputString);
		
		String[] fullInputTokens = inputString.split(STRING_COMMA);
		
		int startNo = 0;
		int deadNo = 0;
		int endNo = 0;
		
		for (String token : fullInputTokens){
			String tag = findTag(token);
			
			LOGGER.info("token is " + token);
			LOGGER.info("tag is " + tag);
			
			switch(tag){
				case "DESC":
					token = removeWordDesc(token);
					//_desc = removeTaskID(token, _taskID);
					if (_desc == null){
						_desc = token;
						LOGGER.info("description is " + _desc);
					} else {
						_desc = _desc + STRING_COMMA + token;
						LOGGER.info("description is " + _desc);
					}
					break;
				case "DEADLINE":	
					deadNo++;
					
					token = removeWordDeadline(token);
					if (token.trim().isEmpty()){
						_deadline = STRING_EMPTY;
						LOGGER.info("deadline is " + _deadline);
						continue;
					}

					token = KEYWORD_DEADLINE + STRING_SPACE + token.trim();
					
					LOGGER.info("after editing, token is " + token);
					String tempDead = getDateAndTimeValue(token, POSITION_DATE_DEADLINE , POSITION_TIME_DEADLINE);
					
					if (tempDead == null){
						continue;
					}
					_deadline = tempDead;
					
					LOGGER.info("deadline is " + _deadline);
					break;
				case "START":	
					startNo++;
					
					token = removeWordStart(token);
					if (token.trim().isEmpty()){
						_startDate = STRING_EMPTY;
						_startTime = STRING_EMPTY;
						LOGGER.info("_startDate and _startTime is " + STRING_EMPTY);
						continue;
					}

					token = KEYWORD_STARTTIME + STRING_SPACE + token.trim();
					
					LOGGER.info("after editing, token is " + token);
					String startResult = getDateAndTimeValue(token, POSITION_DATE_STARTTIME , POSITION_TIME_STARTTIME);
					LOGGER.info("startResult is " + startResult);

					if (startResult == null){
						continue;
					}
					
					inputStartTimeDate(startResult);
					break;
				case "END":
					endNo++;
					
					token = removeWordEnd(token);
					if (token.trim().isEmpty()){
						_endDate = STRING_EMPTY;
						_endTime = STRING_EMPTY;
						LOGGER.info("_endDate and _endTime is " + STRING_EMPTY);
						continue;
					}
					
					token = KEYWORD_ENDTiME + STRING_SPACE + token.trim();
						
					LOGGER.info("after editing, token is " + token);
					String endResult = getDateAndTimeValue(token, POSITION_DATE_ENDTIME , POSITION_TIME_ENDTIME);
					LOGGER.info("endResult is " + endResult);

					if (endResult == null){
						continue;
					}
					
					inputEndTimeDate(endResult);
					break;
				default:
					//_desc = removeTaskID(token, _taskID);
					if (_desc == null){
						_desc = token;
						LOGGER.info("description is " + _desc);
					} else {
						_desc = _desc + STRING_COMMA + token;
						LOGGER.info("description is " + _desc);
					}
					break;
			}
		}
		
		if (_desc != null){
			_desc = _desc.trim();
			LOGGER.info("At last, desc is " + _desc);
			
			if (_desc.isEmpty()){
				_desc = null;
			} 
		}
		
		if (startNo > 1){
			InputManager.outputToGui("WARNING: has " + startNo + " start date and time");
			LOGGER.warning("WARNING: has " + startNo + " start date and time");
		}
		
		if (endNo > 1){
			InputManager.outputToGui("WARNING: has " + endNo + " end date and time");
			LOGGER.warning("WARNING: has " + endNo + " end date and time");
		}
		
		if (deadNo > 1){
			InputManager.outputToGui("WARNING: has " + deadNo + " deadline date and time");
			LOGGER.warning("WARNING: has " + deadNo + " deadline date and time");

		}
	}

	private String findTag(String fullInput){
		String tag = STRING_EMPTY;
		
		if (containsDesc(fullInput)){
			tag = "DESC";
		} else if (containsDeadline(fullInput)){
			tag = "DEADLINE";
		} else if (containsStart(fullInput)){
			tag = "START";
		} else if (containsEnd(fullInput)){
			tag = "END";
		}
		
		return tag;		
	}
	
	/**
	 * getDateAndTimeValue: is called by main functions when finding
	 * deadlines/starttimes/endtimes and it returns the date and time of them
	 * @param token
	 * @param datePos
	 * @param timePos
	 * @return date and time if parsed correctly or null if got error
	 */
	private String getDateAndTimeValue(String token, int datePos, int timePos) {
		assert (token != null && token.trim().isEmpty());
		
		String formatInput = findDateOrTime(token);
		
		String[] splitResult = formatInput.split(STRING_SPACE);
		int arrDatePos = splitResult.length - datePos;
		int arrTimePos = splitResult.length - timePos;

		return getDateAndTime(token, splitResult, arrDatePos, arrTimePos);
	}

	/**
	 * helper method of getDateAndTimeValue
	 * main logic of getting date and time is at here
	 * @param token
	 * @param splitResult
	 * @param datePos
	 * @param timePos
	 * @return
	 */
	private String getDateAndTime(String token, String[] splitResult, int datePos, int timePos) {
		String dateString = splitResult[datePos];
		String timeString = splitResult[timePos];
		
		LOGGER.info("getting date and time...");
		LOGGER.info("dateString is: " + dateString);
		LOGGER.info("timeString is: " + timeString);
		LOGGER.info("description is: " + splitResult[0]);
		
		boolean isDescNotNull = splitResult.length > 6 && !splitResult[0].trim().isEmpty();
		if (STRING_NULL.equals(dateString) || STRING_NULL.equals(timeString) || isDescNotNull){
			InputManager.outputToGui(token + " is not a valid date!");
			LOGGER.severe(token + " is not a valid date!");
			return null;
		}
		
		return dateString + " " + timeString;
	}

	@Override
	protected boolean checkIfIncorrectArguments() throws InvalidParameterException, TaskIDException{
		String inputString[] = input.split(STRING_SPACE);
		
		if (isNotNumberArgs(inputString)){
			LOGGER.severe("Throw");
			LOGGER.severe("inputString is " + inputString);
			throw new InvalidParameterException();
		}
			
		return false;
	}
	
	/**
	 * Takes in input string and finds the first integer as taskID
	 * @param input
	 * @return
	 * @throws TaskIDException 
	 */
	private String findTaskID(String input) throws TaskIDException{
		boolean isDateAndTimePreserved = true;
		String numberInput = DateAndTimeManager.getInstance().parseNumberString(input, isDateAndTimePreserved);

		LOGGER.info("finding TaskID. Converted to numberInput");
		LOGGER.info("numberInput is " + numberInput);
		
		input = numberInput;
		fullInput = numberInput;
	
		LOGGER.info("input is " + input);
		LOGGER.info("fullInput is " + fullInput);
		
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
				
		LOGGER.info("taskID is " + taskID);
		
		if (taskID == -1){
			LOGGER.severe("TASK ID is invalid!");
			throw new TaskIDException();
		}
		
		
		return "" + taskID;
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_TASK_ID, STRING_EMPTY);
		putOneParameter(PARAMETER_DESC, STRING_EMPTY);
		putOneParameter(PARAMETER_DEADLINE, STRING_EMPTY);
		putOneParameter(PARAMETER_START_TIME, STRING_EMPTY);
		putOneParameter(PARAMETER_START_DATE, STRING_EMPTY);
		putOneParameter(PARAMETER_END_TIME, STRING_EMPTY);
		putOneParameter(PARAMETER_END_DATE, STRING_EMPTY);
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_TASK_ID, _taskID);
		putOneParameter(PARAMETER_DESC, _desc);		
		putOneParameter(PARAMETER_DEADLINE, _deadline);
		putOneParameter(PARAMETER_START_TIME, _startTime);
		putOneParameter(PARAMETER_START_DATE, _startDate);
		putOneParameter(PARAMETER_END_TIME, _endTime);
		putOneParameter(PARAMETER_END_DATE, _endDate);
	}
	
	@Override
	protected void putOneParameter(String parameter, String input){
		if (input != null){
			inputParameters.put(parameter, input);
		}
	}
	
	@Override
	protected boolean isNotNumberArgs(String[] inputString){
		if (inputString.length >= getNUMBER_ARGUMENTS()){
			return false;
		}
		return true;
	}
	
	private String removeTaskID(String input, String taskID){
		input = input.replaceFirst("(?i)" + COMMAND_EDIT, "").trim();
		return input.replaceFirst(taskID, "").trim();
	}
	
	@SuppressWarnings("unused")
	private String removeFirstWord(String input){
		return input.replace(getFirstWord(input), "").trim();
	}
	
	private static String getFirstWord(String input) {
		return input.trim().split("\\s+")[0];
	}
	
	private boolean containsDesc(String input){
		String inputCopy = STRING_SPACE + input.toUpperCase() + STRING_SPACE;
		if (inputCopy.contains(" DESC ") || inputCopy.contains(" DESCRIPTION ")){
			return true;
		} 
		return false;
	}
	
	private boolean containsStart(String input){
		String inputCopy = STRING_SPACE + input.toUpperCase() + STRING_SPACE;
		if (inputCopy.contains(" START ")){
			return true;
		} 
		return false;
	}
	
	private boolean containsEnd(String input){
		String inputCopy = STRING_SPACE + input.toUpperCase() + STRING_SPACE;
		if (inputCopy.contains(" END ")){
			return true;
		} 
		return false;
	}

	private String removeWordDesc(String inputCopy) {
		String newString = "";
		int count = 0;	//Just replace only one occurrence of description
		
		String[] inputSplit = inputCopy.split(" ");
		for (int i=0; i<inputSplit.length; i++){
			if (isNotDescWord(inputSplit[i].trim())){
				newString += inputSplit[i] + " ";
			} else if (count > 0) {
				newString += inputSplit[i] + " ";
			} else {
				count ++;
			}
		}
		return newString;
	}

	private boolean isNotDescWord(String string) {
		return !(string.toUpperCase().equals("DESC") || 
				string.toUpperCase().equals("DESCRIPTION"));
	}
	
	private boolean containsDeadline(String input){
		String inputCopy = STRING_SPACE + input.toUpperCase() + STRING_SPACE;
		if (inputCopy.contains(" DEADLINE ") || inputCopy.contains(" DEAD ") || inputCopy.contains(" DATE ")){
			return true;
		} 
		return false;
	}
	
	private String removeWordDeadline(String inputCopy) {
		String newString = "";
		int count = 0;
		String[] inputSplit = inputCopy.split(" ");
		for (int i=0; i<inputSplit.length; i++){
			if (!isDeadlineWord(inputSplit[i].trim())){
				newString += inputSplit[i] + " ";
			} else if (count > 0 ){
				newString += inputSplit[i] + " ";
			} else {
				count++;
			}
		}
		return newString;
	}
	
	private boolean isDeadlineWord(String string) {
		return string.toUpperCase().equals("DEADLINE") || 
				string.toUpperCase().equals("DATE") || 
				string.toUpperCase().equals("DEAD");
	}

	private String removeWordEnd(String inputCopy) {
		String newString = "";
		int count = 0;	//Just replace only one occurrence of description
		
		String[] inputSplit = inputCopy.split(" ");
		for (int i=0; i<inputSplit.length; i++){
			if (isNotEndWord(inputSplit[i].trim())){
				newString += inputSplit[i] + " ";
			} else if (count > 0) {
				newString += inputSplit[i] + " ";
			} else {
				count ++;
			}
		}
		return newString;
	}
	
	private boolean isNotEndWord(String string) {
		return !string.toUpperCase().equals("END");
	}

	/**
	 * findDateOrTime: helper method of getDateAndTimeValue,
	 * it uses formatDateAndTimeInString to get all the date and time
	 * @param fullInput
	 * @return
	 */
	private String findDateOrTime(String fullInput) {
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
	
	private void inputStartTimeDate(String result){
		String[] splitResult = result.split(STRING_SPACE);
		_startDate = splitResult[0];
		_startTime = splitResult[1];
	}
	
	private void inputEndTimeDate(String result){
		String[] splitResult = result.split(STRING_SPACE);
		_endDate = splitResult[0];
		_endTime = splitResult[1];
	}

	private String removeWordStart(String inputCopy) {
		String newString = "";
		int count = 0;	//Just replace only one occurrence of description
		
		String[] inputSplit = inputCopy.split(" ");
		for (int i=0; i<inputSplit.length; i++){
			if (isNotStartWord(inputSplit[i].trim())){
				newString += inputSplit[i] + " ";
			} else if (count > 0) {
				newString += inputSplit[i] + " ";
			} else {
				count ++;
			}
		}
		return newString;
	}
	
	private boolean isNotStartWord(String string) {
		return !string.toUpperCase().equals("START");
	}

}
