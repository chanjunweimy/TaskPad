package com.taskpad.input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.DateObject;
import com.taskpad.dateandtime.DatePassedException;
import com.taskpad.dateandtime.InvalidDateException;
import com.taskpad.dateandtime.InvalidTimeException;
import com.taskpad.dateandtime.NullTimeUnitException;
import com.taskpad.dateandtime.NullTimeValueException;
import com.taskpad.dateandtime.TimeErrorException;
import com.taskpad.dateandtime.TimeObject;


/**
 * Add syntax
 * with delimiters:
 * add <desc> -d <deadlinedate>,<deadlinetime> -s <start date>,<start time> -e <end date>,<end time>
 * 
 * @author Lynnette
 *
 */

public class Add extends Command {

	private static final String STRING_QUOTE = "\"";
	private static final String STRING_EMPTY = "";
	private static final String STRING_DASH = "-";
	private static final String STRING_SPACE = " ";
	private static final String STRING_COMMA = ",";
	
	private static final String COMMAND_ADD = "ADD";
	private static final int NUMBER_ARGUMENTS = 1;
	
	private static String PARAMETER_DEADLINE_DATE = "DEADLINE DATE";
	private static String PARAMETER_DEADLINE_TIME = "DEADLINE TIME";
	private static String PARAMETER_START_DATE = "START DATE";
	private static String PARAMETER_START_TIME = "START TIME";
	private static String PARAMETER_END_DATE = "END DATE";
	private static String PARAMETER_END_TIME = "END TIME";
	private static String PARAMETER_DESCRIPTION = "DESC";
	//private static String PARAMETER_INFO = "INFO";
	
	private static Scanner _sc; 
	private static boolean _invalidParameters;
	
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
	}

	@Override
	protected boolean commandSpecificRun() {		
		if(checkIfDelimitedString()){
			//this line is only useful if it is delimited
			String temp = putDescInQuotesFirst(input);
			
			if (!temp.trim().isEmpty()){
				input = temp;
			}
			//System.out.println("DDE " + input);
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
		putOneParameter(PARAMETER_DEADLINE_TIME, STRING_EMPTY);
		putOneParameter(PARAMETER_DESCRIPTION, STRING_EMPTY); 
		putOneParameter(PARAMETER_START_DATE, STRING_EMPTY);
		putOneParameter(PARAMETER_END_DATE, STRING_EMPTY);
		putOneParameter(PARAMETER_END_TIME, STRING_EMPTY);
		//putOneParameter(PARAMETER_INFO, STRING_EMPTY);
		putOneParameter(PARAMETER_START_TIME, STRING_EMPTY);
	}

	@Override
	protected void putInputParameters() {
		//do nothing
	}
	
	/**
	 * putDescInQuotesFirst: find description within " "
	 * @return input string without description or empty string if " " not found
	 */
	private String putDescInQuotesFirst(String input){
		_sc = new Scanner(input);
		
		StringBuffer tempDesc = null;
		StringBuffer normalString = new StringBuffer(STRING_EMPTY);
		boolean isStarted = false;
		boolean isFinish = false;
		
		while (_sc.hasNext()){
			String buildString = _sc.next();
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
	
		_sc.close();
		
		//System.out.println(tempDesc.toString());
		
		return tempDesc.toString();
	}
	
	private void checkIfExistDesc() throws EmptyDescException {
		/**
		 * should have null? 
		 * Jun Wei
		 */
		if (inputParameters.get(PARAMETER_DESCRIPTION).trim().isEmpty()
				|| (inputParameters.get(PARAMETER_DESCRIPTION) == null)){
			_invalidParameters = true;
			throw new EmptyDescException();
		}		
	}

	private void parseNonDelimitedString() {
		//"..." deadlinedate deadlintime startdate starttime enddate endtime
		
		String inputNew = DateAndTimeManager.getInstance().formatDateAndTimeInString(input);
		System.out.println(inputNew);
		String[] splitInput = inputNew.split(STRING_SPACE);
		int size = splitInput.length;
		putOneParameter(PARAMETER_END_TIME, splitInput[size]);
		putOneParameter(PARAMETER_END_DATE, splitInput[size-1]);
		putOneParameter(PARAMETER_START_TIME, splitInput[size-2]);
		putOneParameter(PARAMETER_START_DATE, splitInput[size-3]);
		putOneParameter(PARAMETER_DEADLINE_DATE, splitInput[size-4]);
		putOneParameter(PARAMETER_DEADLINE_TIME, splitInput[size-4]);
		
		String desc = STRING_EMPTY;
		for (int i=0; i<size-5; i++){
			desc += splitInput[i] + STRING_SPACE;
		}
		
		putOneParameter(PARAMETER_DESCRIPTION, desc);
		
		/* deprecated
		String descString = extractTimeAndDate(splitInput);

		if (!descAlreadyEntered()){
			//inputDesc(descString);
			System.out.println("DEBUG: " + fullInput);
			inputDesc(input);
		}
		*/
	}

	/**
	 * For each input index, if it is date, put in date; if it is time, put in time
	 * Otherwise, string them together as description
	 * @param splitInput
	 */
	@SuppressWarnings("unused")
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

	/**
	 * Lynnette, why you so sure that timeArray got 2 elements?
	 * I think this is 1 of the bug.
	 * From: Jun Wei
	 * @param timeArray
	 */
	private void orderTimeArray(ArrayList<String> timeArray) {
		//this method is add temporary to avoid bug
		if (timeArray.size() != 2){
			return;
		}
		
		Collections.sort(timeArray);
		inputStartTime(timeArray.get(0));
		inputEndTime(timeArray.get(1));
	}

	/**
	 * Lynnette, same here, why you so sure that 
	 * dateArray got 3 elements?
	 * @param dateArray
	 */
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

	/**
	 * Lynnette,
	 * I have helped you modified something here:
	 * get the description out first before using delimeter
	 * 
	 * parseDelimitedString: a method that parses
	 * input that has a description in front.
	 * @throws EmptyDescException 
	 */
	private void parseDelimitedString(){
		//checkAndRemoveDate();
		
		//System.out.println("lalala   " + input);

		_sc = new Scanner(input);
		
		StringBuffer description = new StringBuffer();
		StringBuffer otherPart = new StringBuffer();
		String tempInput = null;
		
		tempInput = retrieveDescription(_sc, description, tempInput);
		otherPart = restructureOtherPart(otherPart, tempInput);
		
		//System.out.println(tempInput.toString() + " " + otherPart.toString() + " " + input);
		
		_sc.close();
		
		
		_sc = new Scanner(otherPart.toString().trim());
		//devide the useDelimiter to prevent resource leak;
		_sc.useDelimiter("\\s-");
		
		while(_sc.hasNext()){
			String nextParam = _sc.next().trim();
			
			nextParam = nextParam.replaceFirst("-", STRING_EMPTY);
			//System.out.println("YYY: " + nextParam);
			
			parseNextParam(nextParam.trim());
		}
		_sc.close();
		
		/* Reverting to the old bit
		checkAndRemoveStart();
		checkAndRemoveEnd();
		checkAndInputDesc();
		*/
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
			putDeadline(param);
			break;
		case "s":
			putStartTime(param);
			break;
		case "e": 
			putEndTime(param);
			break;
		}
	}

	private boolean checkIfDelimitedString() {
		String inputToCheck = input.toLowerCase();
		if (inputToCheck.contains("-d") || inputToCheck.contains("-s") || inputToCheck.contains("-e")){
			return true;
		}
		
		return false;
	}
	
	/* Helper methods for parsing delimited strings */
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
	}
	
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
	
	@SuppressWarnings("unused")
	private void checkAndInputDesc(){
		if (inputParameters.get(PARAMETER_DESCRIPTION) != STRING_EMPTY){
			inputDesc(input);
		}
	}
	
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
	
	private void getStartDetails(String param){
		String[] inputParams = splitBySpace(param);
		inputParams = findDateTime(inputParams);
		
		inputStartDate(inputParams[0]);
		inputStartTime(inputParams[1]);
	}
	
	private void getEndDetails(String param){
		String[] inputParams = splitBySpace(param);
		inputParams = findDateTime(inputParams);
		
		inputEndDate(inputParams[0]);
		inputEndTime(inputParams[1]);
	}

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

	private String[] splitBySpace(String param) {
		return param.split(STRING_SPACE);
	}
	
	@SuppressWarnings("unused")
	private boolean descAlreadyEntered(){
		return inputParameters.get(PARAMETER_DESCRIPTION) != STRING_EMPTY;
	}

	private void inputDeadlineDate(String deadline) {
		putOneParameter(PARAMETER_DEADLINE_DATE, deadline);		
	}
	
	private void inputDeadlineTime(String deadline){
		putOneParameter(PARAMETER_DEADLINE_TIME, deadline);
	}
	
	private void inputDesc(String desc) {
		putOneParameter(PARAMETER_DESCRIPTION, desc);		
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
	
	private String removeFirstChar(String input) {
		return input.replaceFirst(getFirstChar(input), STRING_EMPTY).trim();
	}
	
	private String getFirstChar(String input) {
		String firstChar = input.trim().split("\\s+")[0];
		return firstChar;
	}
	
	private void putDeadline(String param) {
		param = stripWhiteSpaces(param);
		String[] splitParam = param.split(STRING_COMMA);
		
		String deadlineDate = STRING_EMPTY;
		String deadlineTime = STRING_EMPTY;
		
		if (isValidTimeArgs(splitParam)){
			for (int i=0; i<splitParam.length; i++){
				deadlineDate = checkIfIsDate(splitParam[i]);
				if (notEmptyDateString(deadlineDate)){
					inputDeadlineDate(deadlineDate);
				} else {
					deadlineTime = checkIfIsTime(splitParam[i]);
					if (notEmptyTimeString(deadlineTime)){
						inputDeadlineTime(deadlineTime);
					}
				}
			}	
		}
		
		checkEmptyDateTimeString(deadlineDate, deadlineTime, param);
	}
	
	private void putStartTime(String param) {
		String[] splitParam = param.split(STRING_COMMA);
		
		String startTime = STRING_EMPTY;
		String startDate = STRING_EMPTY;
				
		if (isValidTimeArgs(splitParam)){
			for (int i=0; i<splitParam.length; i++){
				startDate = checkIfIsDate(splitParam[i]);
				if (notEmptyDateString(startDate)){
					inputDeadlineDate(startDate);
				} else {
					startTime = checkIfIsTime(splitParam[i]);
					if (notEmptyTimeString(startTime)){
						inputDeadlineTime(startTime);
					}
				}
			}
		}
		checkEmptyDateTimeString(startDate, startTime, param);
	}
	
	private void putEndTime(String param) {
		String[] splitParam = param.split(",");
		String endTime = STRING_EMPTY;
		String endDate = STRING_EMPTY;

		if (isValidTimeArgs(splitParam)){
			for (int i=0; i<splitParam.length; i++){
				endDate = checkIfIsDate(splitParam[i]);
				if (notEmptyDateString(endDate)){
					inputDeadlineDate(endDate);
				} else {
					endTime = checkIfIsTime(splitParam[i]);
					if (notEmptyTimeString(endTime)){
						inputDeadlineTime(endTime);
					}
				}
			}
		}
		checkEmptyDateTimeString(endDate, endTime, param);

	}
	
	private boolean isValidTimeArgs(String[] args){
		if (args.length > 2){
			InputManager.outputToGui("Error in number of time arguments: args.length");
			return false;
		} else {
			return true;
		}
	}
	
	private String checkIfIsTime(String string){
		string.trim();
		String timeString = STRING_EMPTY;
		
		try {
			timeString = DateAndTimeManager.getInstance().parseTime(string);
		} catch (NullTimeUnitException | NullTimeValueException
				| TimeErrorException | InvalidTimeException e) {
			//do nothing
		}
		
		return timeString;
	}
	
	private boolean notEmptyTimeString(String timeString){
		return !timeString.equals(STRING_EMPTY);
	}

	private String checkIfIsDate(String string){
		string.trim();
		String dateString = STRING_EMPTY;
		
		try {
			dateString = DateAndTimeManager.getInstance().parseDate(string);
		} catch (InvalidDateException e) {
			//do nothing
		}
		return dateString;
	}
	
	private boolean notEmptyDateString(String dateString){
		return !dateString.equals(STRING_EMPTY);
	}
	
	private void checkEmptyDateTimeString(String date, String time, String param){
		if (date.equals(STRING_EMPTY) && time.equals(STRING_EMPTY)){
			_invalidParameters = true;
			InputManager.outputToGui(String.format(MESSAGE_INVALID_INPUT, param));
			return;
		}
	}
	
	/* Testing
	public static void main(String[] args){
		checkAndRemoveStart();
	}
	//*/
}
