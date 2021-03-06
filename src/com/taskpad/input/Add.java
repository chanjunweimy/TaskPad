//@author A0119646X

package com.taskpad.input;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidDateException;
import com.taskpad.dateandtime.InvalidQuotesException;
import com.taskpad.execute.InvalidTaskIdException;


/**
 * Add syntax
 * with delimiters:
 * add <desc> -d <deadlinedate>,<deadlinetime> -s <start date>,<start time> -e <end date>,<end time>
 * 
 *
 */

public class Add extends Command {

	private static final String STRING_NULL = "null";
	private static final String STRING_EMPTY = "";
	private static final String STRING_SPACE = " ";
	
	private static final String COMMAND_ADD = "ADD";
	private static final int NUMBER_ARGUMENTS = 1;
	
	private static String PARAMETER_DEADLINE_DATE = "DEADLINE DATE";
	private static String PARAMETER_START_DATE = "START DATE";
	private static String PARAMETER_START_TIME = "START TIME";
	private static String PARAMETER_END_DATE = "END DATE";
	private static String PARAMETER_END_TIME = "END TIME";
	private static String PARAMETER_DESCRIPTION = "DESC";

	private int _startNo;
	private int _deadNo;
	private int _endNo;
	private String _taskID;
	private String _deadline;
	private String _startDate;
	private String _startTime;
	private String _endDate;
	private String _endTime;
	
	private static Scanner _sc; 
	private static boolean _invalidParameters;
	
	private final static Logger LOGGER = Logger.getLogger("TaskPad");
	
	public Add(String input, String fullInput) {
		super(input, fullInput);
		
		_invalidParameters = false;
	}
	
	@Override
	public void run(){
		try {
			checkIfEmptyString();
		} catch (EmptyStringException e) {
			showEmptyString();
			return;
		}
		
		clearInputParameters();
		initialiseParametersToNull();
		
		if (commandSpecificRun()){
			createInputObject();
			passObjectToExecutor();
		}
		_sc.close();
	}

	@Override
	protected void initialiseOthers() {
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_ADD);
		_sc = new Scanner(System.in);
		
		_startNo = 0;
		_endNo = 0;
		_deadNo = 0;
		
		_taskID = null;
		_deadline = null;
		_startDate = null;
		_startTime = null;
		_endDate = null;
		_endTime = null;
	}

	@Override
	protected boolean commandSpecificRun() {		
		if (checkIfDelimitedString()){
			//this line is only useful if it is delimited
			String temp = putDescInQuotesFirst(input);
			
			if (!temp.trim().isEmpty()){
				input = temp;
			}
			parseDelimitedString();

		} else {
			parseNonDelimitedString();
		}
		
		try {
			checkIfExistDesc();
		} catch (EmptyDescException e) {
			InputManager.outputToGui(e.getMessage());
		}
		
		if (_invalidParameters){
			return false;
		}
		
		return true;
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_DEADLINE_DATE, STRING_EMPTY);
		putOneParameter(PARAMETER_DESCRIPTION, STRING_EMPTY); 
		putOneParameter(PARAMETER_START_DATE, STRING_EMPTY);
		putOneParameter(PARAMETER_END_DATE, STRING_EMPTY);
		putOneParameter(PARAMETER_END_TIME, STRING_EMPTY);
		putOneParameter(PARAMETER_START_TIME, STRING_EMPTY);
	}

	@Override
	protected void putInputParameters() {
		//do nothing
	}
	
	private void checkIfExistDesc() throws EmptyDescException {
		if (inputParameters.get(PARAMETER_DESCRIPTION).trim().isEmpty()
				|| (inputParameters.get(PARAMETER_DESCRIPTION) == null)){
			_invalidParameters = true;
			throw new EmptyDescException();
		}		
	}

	private void parseNonDelimitedString() {
		//"..." deadlinedate deadlintime startdate starttime enddate endtime
		String inputNew = STRING_EMPTY;
		try {
			inputNew = DateAndTimeManager.getInstance().formatDateAndTimeInString(input);
		} catch (InvalidQuotesException e) {
			InputManager.outputToGui(e.getMessage());
			return;
		}
		
		String[] splitInput = inputNew.split(STRING_SPACE);
		int size = splitInput.length - 1;
		
		for (int i = size; i >= size - 5; i--){
			if (STRING_NULL.equals(splitInput[i])){
				splitInput[i] = null;
			}
		}
		
		_endTime = splitInput[size];
		_endDate = splitInput[size - 1];
		_startTime = splitInput[size - 2];
		_startDate = splitInput[size - 3];
		String deadlineDate = splitInput[size - 5];
		String deadlineTime = splitInput[size - 4];
		

		if (deadlineDate != null && deadlineTime != null){
			_deadline = deadlineTime + STRING_SPACE + deadlineDate;
		}
		checkEmptyParametersAndInput();		
		
		String desc = STRING_EMPTY;
		for (int i=0; i < size - 5; i++){
			desc += splitInput[i] + STRING_SPACE;
		}
				
		putOneParameter(PARAMETER_DESCRIPTION, desc);
		
	}

	/**
	 * 
	 * parseDelimitedString: a method that parses
	 * input that has a description in front.
	 * @throws EmptyDescException 
	 */
	private void parseDelimitedString(){
		//checkAndRemoveDate();
		
		_sc = new Scanner(input);
		
		StringBuffer description = new StringBuffer();
		StringBuffer otherPart = new StringBuffer();
		String tempInput = null;
		
		tempInput = retrieveDescription(_sc, description, tempInput);
		otherPart = restructureOtherPart(otherPart, tempInput);
			
		_sc.close();
		
		_sc = new Scanner(otherPart.toString().trim());
		_sc.useDelimiter("\\s-");
		
		while(_sc.hasNext()){
			String nextParam = _sc.next().trim();
			
			nextParam = nextParam.replaceFirst("-", STRING_EMPTY);
			parseNextParam(nextParam.trim());
		}
		_sc.close();
		
		try {
			boolean isEdit = false;
			ArrayList<String> times = 
					checkDeadLineAndEndTime(_startTime, _startDate, _taskID, _deadline, _endTime, _endDate, isEdit);
			String endLatest = times.get(POSITION_TIME_ENDTIME / 2);
			String startEarliest = times.get(POSITION_TIME_STARTTIME / 2);
			_deadline = times.get(POSITION_TIME_DEADLINE / 2);
			
			if (_deadline != null){
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
		} catch (InvalidTaskIdException e) {
			//do nothing
			assert (false);
		}
		
		checkEmptyParametersAndInput();
	}

	/**
	 * Extracted method to input parameters
	 */
	private void checkEmptyParametersAndInput() {
		if (_endTime != null){
			inputEndTime(_endTime);
		}
		
		if (_endDate != null){
			inputEndDate(_endDate);
		}
		
		if (_startTime != null) {
			inputStartTime(_startTime);
		}
		
		
		if (_startDate != null){
			inputStartDate(_startDate);
		}
		
		if (_deadline != null){
			inputDeadlineDate(_deadline);
		}
	}

	/**
	 * 
	 * @param otherPart
	 * @param tempInput
	 * @return
	 */
	private StringBuffer restructureOtherPart(StringBuffer otherPart, String tempInput) {
		if (tempInput != null){
			otherPart.append(tempInput.trim());
		}
		while (_sc.hasNext()){
			otherPart.append(STRING_SPACE + _sc.next());
		}
		return otherPart;
	}

	/**
	 * @param description
	 * @param tempInput
	 * @return
	 */
	private String retrieveDescription(Scanner sc, StringBuffer description,
			String tempInput) {
		boolean isBreak = false;
		while (sc.hasNext()){
			tempInput = sc.next();
			 
			if (tempInput.equals("-d") || tempInput.equals("-e") || tempInput.equals("-s")){
				isBreak = true;
				break;
			}
			
			description.append(tempInput + STRING_SPACE);
		}
		
		String descString = description.toString().trim();
		descString = descString.replaceAll("\"", STRING_EMPTY);
		
		putOneParameter(PARAMETER_DESCRIPTION, descString);
		
		if (!isBreak){
			tempInput = STRING_EMPTY;
		}
		
		return tempInput;
	}
	
	private void parseNextParam(String param){
		String firstChar = getFirstChar(param);
		param = removeFirstChar(param);
		
		switch (firstChar){
		case "d":
			_deadNo++;
			_deadline = putDeadline(param);
			break;
		case "s":
			_startNo++;
			processStart(param);
			break;
		case "e": 
			_endNo++;
			processEnd(param);
			break;
		}
		
		LOGGER.info("deadline is " + _deadline );
		LOGGER.info("start time and date is " + _startTime + " " + _startDate);
		LOGGER.info("end time and date is " + _endTime + " " + _endDate);
		
		showErrorWhenActionRepeated(_startNo, _deadNo, _endNo);
	}

	private boolean checkIfDelimitedString() {
		String inputToCheck = input.toLowerCase();
		//delimited String	
		if (inputToCheck.contains(" -d ") || inputToCheck.contains(" -s ") || inputToCheck.contains(" -e ")){
			return true;
		}
		
		return false;
	}


	private void inputDeadlineDate(String deadline) {
		putOneParameter(PARAMETER_DEADLINE_DATE, deadline);		
	}
	
	
	
	private void inputStartDate(String date){
		putOneParameter(PARAMETER_START_DATE, date);
	}
	
	private void inputEndDate(String date){
		putOneParameter(PARAMETER_END_DATE, date);
	}
	
	private void inputStartTime(String time){
		putOneParameter(PARAMETER_START_TIME, time);
	}
	
	private void inputEndTime(String time){
		putOneParameter(PARAMETER_END_TIME, time);
	}
	
	private String stripWhiteSpaces(String input){
		return input.replaceAll(STRING_SPACE, STRING_EMPTY);
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
	
	private void processEnd(String param){
		String endResult = putEndTime(param);
		if (endResult != null){
			inputEndTimeDate(endResult);
		}
	}
	
	private void processStart(String param){
		String startResult = putStartTime(param);
		if (startResult != null){
			inputStartTimeDate(startResult);
		}
	}
	
	/**
	 *  =========================DEPRECATED===============================================================================
	 */
	
	/**
	 * @deprecated
	 * @param param
	 */
	private void getDeadline(String param) {
		param = stripWhiteSpaces(param);
		try {
			param = DateAndTimeManager.getInstance().parseDate(param);
		} catch (InvalidDateException e) {
			InputManager.outputToGui(e.getMessage());
			_invalidParameters = true;
		}
		inputDeadlineDate(param);
	}
	
	/* Helper methods for parsing delimited strings */
	/**
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void checkAndRemoveDate() {
		String[] splitInput = input.split(STRING_SPACE);
		String newInput = STRING_EMPTY;
		
		for (int i=0; i<splitInput.length; i++){
			if (splitInput[i].toLowerCase().equals("-d")){
				getDeadline(splitInput[i+1]);
				splitInput[i+1] = STRING_EMPTY;
			} else {
				newInput += splitInput[i] + STRING_SPACE;
			}
		}
		input = newInput;
	}
	
	/**
	 * @deprecated
	 * @param dateArray
	 * @return
	 */
	/*
	private ArrayList<String> sortDateArray(ArrayList<String> dateArray) {
		ArrayList<Date> dates = convertStringToDates(dateArray);

		Collections.sort(dates, new Comparator<Date>(){
			@Override
			public int compare(Date d1, Date d2) {
				return d1.compareTo(d2);
			}
		});
		
		return null;
	}
	*/

	/**
	 * @deprecated
	 * @param dateArray
	 * @return
	 */
	/*
	private ArrayList<Date> convertStringToDates(ArrayList<String> dateArray) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		ArrayList<Date> dates = new ArrayList<Date>();
		
		for (int i=0; i<dateArray.size(); i++){
			try {
				dates.add(sdf.parse(dateArray.get(i)));
			} catch (ParseException e) {
				//do nothing
			}
		}
		
		return dates;
	}
	*/
	
	/*
	private static String constructNewInputString(String[] splitInput,
			String newInput, int index) {
		if (index != -1){
			for (int i=0; i<splitInput.length; i++){
				if (splitInput[i].equals(STRING_DASH)){
					continue;
				} else {
					newInput += splitInput[i].trim() + STRING_SPACE;
				}
			}
		}
		return newInput;
	}*/
	
	
	/**
	 * @param timeArray
	 */
	/*
	private void orderTimeArray(ArrayList<String> timeArray) {
		//this method is add temporary to avoid bug
		if (timeArray.size() != 2){
			return;
		}
		
		Collections.sort(timeArray);
		inputStartTime(timeArray.get(0));
		inputEndTime(timeArray.get(1));
	}
	*/

	/**
	 * @param dateArray
	 */
	/*
	private void orderDateArray(ArrayList<String> dateArray) {
		//this method is add temporary to avoid bug
		if (dateArray.size() != 3){
			return;
		}
		
		dateArray = sortDateArray(dateArray);
		inputDeadlineDate(dateArray.get(0));
		inputStartDate(dateArray.get(1));
		inputEndDate(dateArray.get(2));
	}
	*/
	
	/*
	private String[] splitBySpace(String param) {
		return param.split(STRING_SPACE);
	}
	*/
	
	/*
	private boolean descAlreadyEntered(){
		return inputParameters.get(PARAMETER_DESCRIPTION) != STRING_EMPTY;
	}
	*/
	
	/**
	 * @deprecated
	 * @param desc
	 */
	/*
	private void inputDesc(String desc) {
		putOneParameter(PARAMETER_DESCRIPTION, desc);		
	}
	*/
	
	/**
	 * For each input index, if it is date, put in date; if it is time, put in time
	 * Otherwise, string them together as description
	 * @deprecated
	 * @param splitInput
	 */
	/*
	@SuppressWarnings({ "unused"})
	private String extractTimeAndDate(String[] splitInput) {
		ArrayList<String> dateArray = new ArrayList<String>();
		ArrayList<String> timeArray = new ArrayList<String>();
		
		String newInput = STRING_EMPTY;
		
		for (int i=0; i<splitInput.length; i++){
			String inputString = stripWhiteSpaces(splitInput[i]);
			
			DateObject dateObject = DateAndTimeManager.getInstance().findDate(inputString);
			if (dateObject != null){
				dateArray.add(dateObject.getParsedDate());
			} else{
				TimeObject timeObject = DateAndTimeManager.getInstance().findTime(inputString);
				if (timeObject != null){
					timeArray.add(timeObject.getParsedTime());
				} else {
					newInput += inputString + STRING_SPACE;
				}
			}
		}		
		
		orderDateArray(dateArray);
		orderTimeArray(timeArray);
		
		return newInput;
		
	}
	*/

	/**
	 * @deprecated
	 * @param param
	 * @return
	 */
	/*
	private boolean findTimeOrDate(String param){
		param = param.trim();		
		
		DateObject dateObject = DateAndTimeManager.getInstance().findDate(param);
		if (dateObject != null){
			return true;
		} else {
			TimeObject timeObject = DateAndTimeManager.getInstance().findTime(param);
			if (timeObject != null){
				return true;
			}
		}
		return false;
	}
	*/

	/**
	 * @deprecated
	 * @param param
	 */
	/*
	private void getStartDetails(String param){
		String[] inputParams = splitBySpace(param);
		inputParams = findDateTime(inputParams);
		
		inputStartDate(inputParams[0]);
		inputStartTime(inputParams[1]);
	}
	*/
	
	/**
	 * @deprecated
	 * @param param
	 */
	/*
	private void getEndDetails(String param){
		String[] inputParams = splitBySpace(param);
		inputParams = findDateTime(inputParams);
		
		inputEndDate(inputParams[0]);
		inputEndTime(inputParams[1]);
	}
	*/
	
	/**
	 * @deprecated
	 * @param inputParams
	 * @return
	 */
	/*
	private String[] findDateTime(String[] inputParams) {
		String[] dateTime = {STRING_EMPTY, STRING_EMPTY};
		boolean gotDate = false;
		boolean gotTime = false;
		
		for (int i=0; i<inputParams.length; i++){
			if (!gotDate){
				DateObject dateObject = DateAndTimeManager.getInstance().findDate(inputParams[i].trim());
				if (dateObject != null){
					dateTime[0] = dateObject.getParsedDate();
					gotDate = true;
				} else if (!gotTime){
					TimeObject timeObject = DateAndTimeManager.getInstance().findTime(inputParams[i].trim());
					dateTime[1] = timeObject.getParsedTime();
					gotTime = true;
				}
			} 
		}
		
		return dateTime;
	}
	*/
	
	/**
	 * @deprecated
	 */
	/*
	@SuppressWarnings("unused")
	private void checkAndRemoveEnd() {
		String[] splitInput = input.split(STRING_SPACE);
		String newInput = STRING_EMPTY;
		String param = STRING_EMPTY;
		int index = -1;
		
		for (int i=0; i<splitInput.length; i++){
			if (splitInput[i].toLowerCase().equals("-e")){
				index = i;
				splitInput[i] = STRING_DASH;
				if (findTimeOrDate(splitInput[i+1])){
					param += splitInput[i+1] + STRING_SPACE;
					splitInput[i+1] = STRING_DASH;
				} 
				
				if (findTimeOrDate(splitInput[i+2])){
					param += splitInput[i+2] + STRING_SPACE;
					splitInput[i+2] = STRING_DASH;
				} 
			} 
		}
		
		newInput = constructNewInputString(splitInput, newInput, index);

		getEndDetails(param);
		input = newInput;
	}
	*/
	
	/**
	 * @deprecated
	 */
	/*
	@SuppressWarnings("unused")
	private void checkAndInputDesc(){
		if (inputParameters.get(PARAMETER_DESCRIPTION) != STRING_EMPTY){
			inputDesc(input);
		}
	}
	*/
	
	/**
	 * @deprecated
	 */
	/*
	@SuppressWarnings("unused")
	private void checkAndRemoveStart() {
		String[] splitInput = input.split(STRING_SPACE);
		String newInput = STRING_EMPTY;
		String param = STRING_EMPTY;
		int index = -1;
		
		for (int i=0; i<splitInput.length; i++){
			if (splitInput[i].toLowerCase().equals("-s")){
				index = i;
				splitInput[i] = STRING_DASH;
				if (findTimeOrDate(splitInput[i+1])){
					param += splitInput[i+1] + STRING_SPACE;
					splitInput[i+1] = STRING_DASH;
				} 
				
				if (findTimeOrDate(splitInput[i+2])){
					param += splitInput[i+2] + STRING_SPACE;
					splitInput[i+2] = STRING_DASH;
				} 
			} 
		}
		
		newInput = constructNewInputString(splitInput, newInput, index);

		getStartDetails(param);
		input = newInput;
	}
	
	*/
	/* Testing
	public static void main(String[] args){
		checkAndRemoveStart();
	}
	//*/
}
