package com.taskpad.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.DateObject;
import com.taskpad.dateandtime.DatePassedException;
import com.taskpad.dateandtime.InvalidDateException;
import com.taskpad.dateandtime.TimeObject;

public class Add extends Command {

	private static final String STRING_QUOTE = "\"";
	private static final String STRING_EMPTY = "";
	private static final String STRING_DASH = "-";
	private static final String STRING_SPACE = " ";
	private static final String STRING_COMMA = ",";
	
	private static final String COMMAND_ADD = "ADD";
	private static final int NUMBER_ARGUMENTS = 1;
	
	private static String PARAMETER_DEADLINE = "DEADLINE";
	private static String PARAMETER_START_DATE = "START DATE";
	private static String PARAMETER_START_TIME = "START TIME";
	private static String PARAMETER_END_DATE = "END DATE";
	private static String PARAMETER_END_TIME = "END TIME";
	private static String PARAMETER_DESCRIPTION = "DESC";
	private static String PARAMETER_INFO = "INFO";
	private static String PARAMETER_VENUE = "VENUE";
	
	private static Scanner _sc; 
	private static boolean _invalidParameters = false;
	private static boolean noDescInQuotes = false;
	
	public Add(String input, String fullInput) {
		super(input, fullInput);
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
		String inputDesc = putDescInQuotesFirst(input);
		
		if (inputDesc.equals(STRING_EMPTY)){
			//means no description in quotes 
			noDescInQuotes = true;
		} else {
			//continue parsing the rest - what's not date and time is info
			noDescInQuotes = false;
		}
		
		if(checkIfDelimitedString()){
			parseDelimitedString();
		}else {
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
		putOneParameter(PARAMETER_DEADLINE, STRING_EMPTY);
		putOneParameter(PARAMETER_DESCRIPTION, STRING_EMPTY); 
		putOneParameter(PARAMETER_START_DATE, STRING_EMPTY);
		putOneParameter(PARAMETER_END_DATE, STRING_EMPTY);
		putOneParameter(PARAMETER_END_TIME, STRING_EMPTY);
		putOneParameter(PARAMETER_INFO, STRING_EMPTY);
		putOneParameter(PARAMETER_START_TIME, STRING_EMPTY);
		putOneParameter(PARAMETER_VENUE, STRING_EMPTY);		
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
			putOneParameter(PARAMETER_DESCRIPTION, tempDesc.toString());
		}
	
		_sc.close();
		return normalString.toString().toString();
	}
	
	private void checkIfExistDesc() throws EmptyDescException {
		if (inputParameters.get(PARAMETER_DESCRIPTION).equals(STRING_EMPTY)){
			_invalidParameters = true;
			throw new EmptyDescException();
		}		
	}

	private void parseNonDelimitedString() {
		//input = DateAndTimeManager.getInstance().formatDateAndTimeInString(input);
		String[] splitInput = input.split(STRING_SPACE);
		String descString = extractTimeAndDate(splitInput);

		if (descAlreadyEntered()){
			inputInfo(descString);
		} else {
			inputDesc(descString);
		}
	}

	/**
	 * For each input index, if it is date, put in date; if it is time, put in time
	 * Otherwise, string them together as description
	 * @param splitInput
	 */
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


	private void orderTimeArray(ArrayList<String> timeArray) {
		Collections.sort(timeArray);
		inputStartTime(timeArray.get(0));
		inputEndTime(timeArray.get(1));
	}

	private void orderDateArray(ArrayList<String> dateArray) {
		Collections.sort(dateArray);
		inputDeadline(dateArray.get(0));
		inputStartDate(dateArray.get(1));
		inputEndDate(dateArray.get(2));
	}

	private void parseDelimitedString() {
		checkAndRemoveDate();
		int count = 0;
		
		_sc = new Scanner(input).useDelimiter("\\s-");
		while(_sc.hasNext()){
			String nextParam = _sc.next();
			if (count == 0){
				inputDesc(nextParam.trim());
			} else {
				parseNextParam(nextParam.trim());
			}
			count++;
		}
	}

	private boolean checkIfDelimitedString() {
		String inputToCheck = input.toLowerCase();
		if (inputToCheck.contains("-d") || inputToCheck.contains("-s") || 
				inputToCheck.contains("-e") || inputToCheck.contains("-v")){
			return true;
		}
		
		return false;
	}
	
	/* Helper methods for parsing delimited strings */
	private void checkAndRemoveDate() {
		String[] splitInput = input.split(STRING_SPACE);
		String newInput = STRING_EMPTY;
		
		for (int i=0; i<splitInput.length; i++){
			if (splitInput[i].toLowerCase().equals("-d")){
				getDeadline(splitInput[i+1]);
				splitInput[i+1] = STRING_EMPTY;
			} else {
				newInput += splitInput[i] + " ";
			}
		}
		input = newInput;
	}

	private void getDeadline(String param) {
		param = stripWhiteSpaces(param);
		try {
			param = DateAndTimeManager.getInstance().parseDate(param);
		} catch (InvalidDateException | DatePassedException e) {
			InputManager.outputToGui(e.getMessage());
			_invalidParameters = true;
		}
		inputDeadline(param);
	}
	
	private void parseNextParam(String param) {
		String firstChar = getFirstChar(param);
		param = removeFirstChar(param);

		switch (firstChar){
		case "d":
			getDeadline(param);
			break;
		case "v":
			inputVenue(param);
			break;
		case "s":
			getStartDetails(param);
			break;
		case "e": 
			getEndDetails(param);
			break;
		default:
			_invalidParameters = true;
		}		
	}
	
	private void getStartDetails(String param){
		String[] splitParam = param.split(STRING_SPACE);
	}
	
	private void getEndDetails(String param){
		
	}

	private String removeFirstChar(String input) {
		return input.replaceFirst(getFirstChar(input), STRING_EMPTY).trim();
	}
	
	private String getFirstChar(String input) {
		String firstChar = input.trim().split("\\s+")[0];
		return firstChar;
	}
	
	private boolean descAlreadyEntered(){
		return inputParameters.get(PARAMETER_DESCRIPTION) != STRING_EMPTY;
	}

	private void inputDeadline(String deadline) {
		putOneParameter(PARAMETER_DEADLINE, deadline);		
	}
	
	private void inputDesc(String desc) {
		putOneParameter(PARAMETER_DESCRIPTION, desc);		
	}
	
	private void inputInfo(String info){
		putOneParameter(PARAMETER_INFO, info);
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
	
	private void inputVenue(String venue){
		putOneParameter(PARAMETER_VENUE, venue);
	}
	
	private String stripWhiteSpaces(String input){
		return input.replaceAll(STRING_SPACE, STRING_EMPTY);
	}

}
