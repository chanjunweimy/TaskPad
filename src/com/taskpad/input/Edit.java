//@author A0119646X

package com.taskpad.input;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidQuotesException;
import com.taskpad.execute.InvalidTaskIdException;

public class Edit extends Command{
	private final static Logger LOGGER = Logger.getLogger("TaskPad");
		
	private static final int NUMBER_ARGUMENTS = 2;

	private static final String COMMAND_EDIT = "EDIT";
	
	private static String PARAMETER_TASK_ID = "TASKID";
	private static String PARAMETER_DESC = "DESC";
	private static String PARAMETER_DEADLINE = "DEADLINE";
	private static String PARAMETER_START_DATE = "START DATE";
	private static String PARAMETER_START_TIME = "START TIME";
	private static String PARAMETER_END_DATE = "END DATE";
	private static String PARAMETER_END_TIME = "END TIME";
	private static String PARAMETER_INFO = "INFO";
	
	private String _taskID;
	private String _desc;
	private String _deadline;
	private String _startDate;
	private String _startTime;
	private String _endDate;
	private String _endTime;
	private String _editInput;
	private String _info;
	
	private int _deadNo;
	private int _endNo;
	private int _startNo;
	private int _infoNo;
	
	private static final String STRING_SPACE = " ";
	private static final String STRING_COMMA = ",";
	private static final String STRING_EMPTY = "";

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
		_info = null;
		
		_deadNo = 0;
		_startNo = 0;
		_endNo = 0;
		_infoNo = 0;
	}

	@Override
	protected boolean commandSpecificRun() {
		clearInputParameters();
		
		LOGGER.info("input is " + input);
		
		_editInput = fullInput;  
		if(noTaskIDInPosition(fullInput)){
			try {
				_taskID = findTaskID(fullInput);
			} catch (TaskIDException | InvalidQuotesException e) {
				InputManager.outputToGui(e.getMessage());
				LOGGER.severe(e.getMessage());
				return false;
			}
		}
		
		LOGGER.info("taskID is " + _taskID);
		input = removeTaskID(fullInput, _taskID);
		LOGGER.info("input is " + input);
		
		fullInput = _editInput;
		
		if(isDelimitedString(" " + input + " ")){
			String temp = putDescInQuotesFirst(input);
			
			if (!temp.trim().isEmpty()){
				input = temp;
			}
			editDelimitedString();
		} else {
			
			LOGGER.info("taskID is " + _taskID);
			
			try {
				getOtherKeysValue();
			} catch (InvalidTaskIdException e) {
				InputManager.outputToGui("Not a valid TaskID!");
				LOGGER.severe("Not a valid TaskID!");
				return false;
			}
		}

		putInputParameters();
		return true;
	}

	/**
	 * delimited string syntax: edit <taskID> <desc> -d <deadline...> -s <start...> -e <end...>
	 */
	private void editDelimitedString() {
		extractTaskID();
		extractDescription();		
		
		Scanner sc = new Scanner(input);
		sc.useDelimiter("\\s-");
		
		while(sc.hasNext()){
			String nextParam = sc.next().trim();
			
			nextParam = nextParam.replaceFirst("-", STRING_EMPTY);

			parseNextParam(nextParam.trim());
		}
		sc.close();
	}
	
	/**
	 * Check if second word entered is an integer (likely to be taskID)
	 * @param fullInput
	 * @return true if is integer and sets it as taskID
	 */
	private boolean noTaskIDInPosition(String fullInput) {
		String splitInput[] = fullInput.split(STRING_SPACE);
		
		if (isNotValidTaskID(splitInput[1])){
			InputManager.outputToGui(MESSAGE_WARNING_TASKID);
			return true;
		} else {
			int taskID = Integer.parseInt(splitInput[1]);
			_taskID = "" + taskID;
			return false;
		}
		/*
		try{
			int taskID = Integer.parseInt(splitInput[1]);
			_taskID = "" + taskID;
			return false;
		} catch (NumberFormatException e){
			InputManager.outputToGui(MESSAGE_WARNING_TASKID);
			return true;
		}
		*/
	}

	/**
	 * @throws InvalidTaskIdException 
	 * 
	 */
	private void getOtherKeysValue() throws InvalidTaskIdException {
		//String inputString = fullInput;
		//inputString = removeTaskID(inputString, _taskID);
		String inputString = input;
		
		LOGGER.info("getting other parameters value (exclude ID).");
		LOGGER.info("inputString is " + inputString);
		
		String[] fullInputTokens = inputString.split(STRING_COMMA);
		
		for (String token : fullInputTokens){
			String tag = findTag(token);
			
			LOGGER.info("token is " + token);
			LOGGER.info("tag is " + tag);
			
			switch(tag){
				case "DESC":
					token = removeWordDesc(token);
					if (_desc == null){
						_desc = token;
						LOGGER.info("description is " + _desc);
					} else {
						_desc = _desc + STRING_COMMA + token;
						LOGGER.info("description is " + _desc);
					}
					break;
				case "DEADLINE":	
					_deadNo++;
					
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
					_startNo++;
					
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
					_endNo++;
					
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
				case "INFO":
					_infoNo++;
					token = removeWordInfo(token);
					if (_info == null){
						_info = token;
						LOGGER.info("information is " + _info);
					} else {
						_info = _info + STRING_COMMA + token;
						LOGGER.info("information is " + _info);
					}
					break;
				default:
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
		
		checkDesc();
		
		showErrorWhenActionRepeated(_startNo, _deadNo, _endNo, _infoNo);
	
		boolean isEdit = true;
		ArrayList<String> times =
				checkDeadLineAndEndTime(_startTime, _startDate, _taskID, _deadline, _endTime, _endDate, isEdit);
		String endLatest = times.get(POSITION_TIME_ENDTIME / 2);
		String startEarliest = times.get(POSITION_TIME_STARTTIME / 2);
		_deadline = times.get(POSITION_TIME_DEADLINE / 2);
		
		if (_deadline != null && !_deadline.trim().isEmpty  ()){
			String[] deadTokens = _deadline.split(STRING_SPACE);
			_deadline = deadTokens[1] + STRING_SPACE + deadTokens[0];
		}
		
		if (endLatest == null){
			_endDate = null;
			_endTime = null;
		}
		
		if (startEarliest == null){
			_startDate = null;
			_startTime = null;
		}
	}
	
	/**
	 * Takes in input string and finds the first integer as taskID
	 * @param input
	 * @return taskID
	 * @throws TaskIDException 
	 * @throws InvalidQuotesException 
	 */
	private String findTaskID(String input) throws TaskIDException, InvalidQuotesException{
		String numberInput = DateAndTimeManager.getInstance().parseNumberString(input);

		LOGGER.info("finding TaskID. Converted to numberInput");
		LOGGER.info("numberInput is " + numberInput);
		
		input = numberInput;
		fullInput = numberInput;
		
		LOGGER.info("input is " + input);
		LOGGER.info("fullInput is " + fullInput);
		
		int taskID = -1;
		String[] splitInput = input.split(STRING_SPACE);
		
		for (int i = 0; i < splitInput.length; i++){
			if (taskID == -1){
				String token = splitInput[i];
				if (!isNotValidTaskID(token)){
					taskID = Integer.parseInt(token);
					break;
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

	/**
	 * 
	 */
	private void checkDesc() {
		if (_desc != null){
			_desc = _desc.trim();
			LOGGER.info("At last, desc is " + _desc);
			
			if (_desc.isEmpty()){
				_desc = null;
			} 
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
		} else if (containsInfo(fullInput)){
			tag = "INFO";
		}
		
		return tag;		
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

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_TASK_ID, STRING_EMPTY);
		putOneParameter(PARAMETER_DESC, STRING_EMPTY);
		putOneParameter(PARAMETER_DEADLINE, STRING_EMPTY);
		putOneParameter(PARAMETER_START_TIME, STRING_EMPTY);
		putOneParameter(PARAMETER_START_DATE, STRING_EMPTY);
		putOneParameter(PARAMETER_END_TIME, STRING_EMPTY);
		putOneParameter(PARAMETER_END_DATE, STRING_EMPTY);
		putOneParameter(PARAMETER_INFO, STRING_EMPTY);
	}

	@Override
	protected void putInputParameters() {
		if (_deadline != null){
			if (! _deadline.trim().isEmpty()){
				String[] tempDeadSplit = _deadline.split(STRING_SPACE);
			
				_deadline = tempDeadSplit[1] + STRING_SPACE + tempDeadSplit[0]; 
			}
			putOneParameter(PARAMETER_DEADLINE, _deadline);
		}
		
		putOneParameter(PARAMETER_TASK_ID, _taskID);
		putOneParameter(PARAMETER_DESC, _desc);		
		putOneParameter(PARAMETER_START_TIME, _startTime);
		putOneParameter(PARAMETER_START_DATE, _startDate);
		putOneParameter(PARAMETER_END_TIME, _endTime);
		putOneParameter(PARAMETER_END_DATE, _endDate);
		putOneParameter(PARAMETER_INFO, _info);
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
		input = input.replaceFirst("(?i)" + COMMAND_EDIT, "");
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
	
	private boolean containsInfo(String input){
		String inputCopy = STRING_SPACE + input.toUpperCase() + STRING_SPACE;
		if (inputCopy.contains(" INFO ") || inputCopy.contains(" INFORMATION ") ||
				inputCopy.contains(" DETAILS ") || inputCopy.contains(" DETAIL ")){
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
	
	private String removeWordInfo(String inputCopy) {
		String newString = "";
		int count = 0;	//Just replace only one occurrence of description
		
		String[] inputSplit = inputCopy.split(" ");
		for (int i=0; i<inputSplit.length; i++){
			LOGGER.info("token is " + inputSplit[i]);
			LOGGER.info("cnt is " + count + " newString is: " + newString);
			if (isNotInfoWord(inputSplit[i].trim())){
				newString += inputSplit[i] + " ";
				LOGGER.info("added notInfoWord: " + newString);
			} else if (count > 0) {
				newString += inputSplit[i] + " ";
				LOGGER.info("added other InfoWord: " + newString);
			} else {
				count ++;
			}
		}
		
		LOGGER.info("after deleting word info : " + newString);
		return newString;
	}
	
	private boolean isNotInfoWord(String string) {
		string = string.toUpperCase();
		return (!string.equals("INFO") &&
				!string.equals("INFORMATION") &&
				!string.equals("DETAILS") &&
				!string.equals("DETAIL"));
	}

	private boolean isNotDescWord(String string) {
		return (!string.toUpperCase().equals("DESC") &&
				!string.toUpperCase().equals("DESCRIPTION"));
	}
	
	private boolean containsDeadline(String input){
		String inputCopy = STRING_SPACE + input.toUpperCase() + STRING_SPACE;
		if (inputCopy.contains(" DEADLINE ") || inputCopy.contains(" DEAD ") || 
				inputCopy.contains(" DATE ") || inputCopy.contains(" -D ")){
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
				string.toUpperCase().equals("DEAD") ||
				string.toUpperCase().equals("-D");
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
		return !string.toUpperCase().equals("END") && !string.toUpperCase().equals("-E");
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
		for (int i = 0; i < inputSplit.length; i++){
			LOGGER.info("remove " + inputSplit[i] + "?");
			if (isNotStartWord(inputSplit[i].trim())){
				newString += inputSplit[i] + " ";
				LOGGER.info("NO");
				LOGGER.info("newString is " + newString);
			} else if (count > 0) {
				newString += inputSplit[i] + " ";
				LOGGER.info("count is " + count);
				LOGGER.info("NO");
				LOGGER.info("newString is " + newString);
			} else {
				count ++;
				LOGGER.info("YES");
			}
		}
		return newString;
	}
	
	private boolean isNotStartWord(String string) {
		return !string.toUpperCase().equals("START") && !string.toUpperCase().equals("-S");
	}

	private boolean isDelimitedString(String input) {
		if (input.contains(" -s ") || input.contains(" -d ") || 
				input.contains(" -e ") || input.contains(" -i ")){
			return true;
		}
		return false;
	}
	
	private void parseNextParam(String param){
		String firstChar = getFirstChar(param);
		param = removeFirstChar(param).trim();

		switch (firstChar){
			case "d":
				_deadNo++;
				processDeadline(param);
				break;
			case "s":
				_startNo++;
				processStart(param);
				break;
			case "e": 
				_endNo++;
				processEnd(param);
				break;
			case "i":
				_infoNo++;
				processInfo(param);
				break;
		}
		
		LOGGER.info("deadline is " + _deadline );
		LOGGER.info("start time and date is " + _startTime + " " + _startDate);
		LOGGER.info("end time and date is " + _endTime + " " + _endDate);
		
		showErrorWhenActionRepeated(_startNo, _deadNo, _endNo, _infoNo);
	}

	private void processDeadline(String param){
		if (param.isEmpty()){
			return;
		}
		
		String tempDate = putDeadline(param);
		_deadline = swapDeadlinePlaces(tempDate);
	}
	
	private String swapDeadlinePlaces(String deadline){
		String[] tempDead = deadline.split(STRING_SPACE);
		return tempDead[1] + STRING_SPACE + tempDead[0];
	}
	
	private void processEnd(String param){
		if (param.isEmpty()){
			return;
		}
		
		String endResult = putEndTime(param);
		if (endResult != null){
			inputEndResult(endResult);
		}
	}
	
	private void processStart(String param){
		if (param.isEmpty()){
			return;
		}
		
		String startResult = putStartTime(param);
		if (startResult != null){
			inputStartResult(startResult);
		}
	}
	
	private void inputStartResult(String startResult) {
		String[] splitResult = startResult.split(STRING_SPACE);
		_startDate = splitResult[0];
		_startTime = splitResult[1];
	}
	
	private void inputEndResult(String endResult) {
		String[] splitResult = endResult.split(STRING_SPACE);
		_endDate = splitResult[0];
		_endTime = splitResult[1];
	}
	
	private void extractDescription(){
		int index = input.indexOf("-");
		if (index != 0){
			_desc = input.substring(0, index).trim();
			int size = input.length();
			input = input.substring(index, size).trim();
		}
	}
	
	private void processInfo(String param){
		param = param.trim();
		if (param.isEmpty()){
			return;
		}
		_info = param;
	}
	
	/**
	 * =======================================DEPRECATED=================================================================
	 */
	/* Helper methods for delimited string */
	/**
	 * @deprecated
	 */
	private void extractTaskID() {
		/*
		String[] split = input.split(STRING_SPACE);
		_taskID = split[0];
		
		String newInput = STRING_EMPTY;
		for (int i=1; i<split.length; i++){
			newInput += split[i] + STRING_SPACE;
		}
		input = newInput;
		*/
	}
}
