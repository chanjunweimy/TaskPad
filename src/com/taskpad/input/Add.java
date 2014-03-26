package com.taskpad.input;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.DatePassedException;
import com.taskpad.dateandtime.InvalidDateException;
import com.taskpad.dateandtime.InvalidTimeException;
import com.taskpad.dateandtime.TimeErrorException;

public class Add extends Command{
	
	private static final String QUOTE = "\"";
	private static final String BLANK = "";
	private static final String DASH = "-";
	private static final String SPACE = " ";
	private static final String EMPTY = "";
	private static final String COMMA = ",";
	
	private static final String COMMAND_ADD = "ADD";
	private static final int NUMBER_ARGUMENTS = 1;
		
	private static String PARAMETER_DEADLINE = "DEADLINE";
	private static String PARAMETER_START_DATE = "START DATE";
	private static String PARAMETER_START_TIME = "START TIME";
	private static String PARAMETER_END_DATE = "END DATE";
	private static String PARAMETER_END_TIME = "END TIME";
	private static String PARAMETER_CATEGORY = "CATEGORY";
	private static String PARAMETER_DESCRIPTION = "DESC";
	private static String PARAMETER_VENUE = "VENUE";
	
	private static String MESSAGE_ERROR_TIME = "Error: Invalid variables for time: %d";
	
	private static int LENGTH_TIME = 2;
	
	private static boolean _invalidParameters = false;
	private static int _count;
	private static String _desc;
	
	private static Scanner _sc;

	public Add(String input, String fullInput) {
		super(input, fullInput);
	}
	
	@Override
	protected void initialiseOthers(){
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_ADD);
		
		_sc = new Scanner(System.in);
		_count = 0;
		_invalidParameters = false;
	}
	
	@Override
	protected boolean commandSpecificRun() {
		String inputDesc = putDescToFirst();
		
		if (inputDesc.equals(EMPTY)){
			try {
				checkIfExistDesc();
			} catch (EmptyDescException e) {
				InputManager.outputToGui(e.getMessage());
				return false;
			}
		} else {
			input = inputDesc;
		}
		
		if(!_invalidParameters){
			splitInputParameters();
		}
		
		/* Deprecated
		if (isNotDelimitedString()){
			getDescInQuotes();
			removeDesc();
			parseTheRest();
		}
		*/
		
		if (_invalidParameters){
			return false;
		}
		
		return true;		
	}

	
	private void checkIfExistDesc() throws EmptyDescException {
		if (!isFirstBitDesc()){
			_invalidParameters = true;
			throw new EmptyDescException();
		}
	}
	
	private boolean isFirstBitDesc(){
		int indexFirstDash = fullInput.indexOf(DASH);
		int indexFirstWhite = fullInput.indexOf(SPACE);
		if (indexFirstDash == -1){
			return true;
		} else if(fullInput.substring(indexFirstWhite+1, indexFirstDash).equals("")){
			return false;
		}
		
		return true;
	}

	/**
	 * putDescToFirst: move the description that is with
	 * " " to the first place in order to perform the
	 * following methods.
	 * @return
	 */
	public String putDescToFirst() {
		//scanner that omits all white space character
		_sc = new Scanner(input);
		
		StringBuffer tempDesc = null;
		StringBuffer normalString = new StringBuffer(BLANK);
		boolean isStarted = false;
		boolean isFinish = false;
		
		while (_sc.hasNext()){
			String buildString = _sc.next();
			if (!isFinish){
				if (!isStarted){
					if (buildString.startsWith(QUOTE)){
						isStarted = true;
						tempDesc = new StringBuffer(buildString);
					} else {
						normalString.append(SPACE + buildString);
					}
				} else {
					tempDesc.append(SPACE + buildString);
					if (buildString.endsWith(QUOTE)){
						isFinish = true;
					}
				}
			} else {
				normalString.append(SPACE + buildString);
			}
		}
		
		if (tempDesc == null){
			//invalidParam();
			tempDesc = new StringBuffer(BLANK);
			return tempDesc.toString();
		}
	
		_sc.close();
		return tempDesc.append(normalString.toString()).toString();
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_CATEGORY, BLANK);
		putOneParameter(PARAMETER_DEADLINE, BLANK);
		putOneParameter(PARAMETER_DESCRIPTION, BLANK); 
		putOneParameter(PARAMETER_START_DATE, BLANK);
		putOneParameter(PARAMETER_END_DATE, BLANK);
		putOneParameter(PARAMETER_END_TIME, BLANK);
		putOneParameter(PARAMETER_START_TIME, BLANK);
		putOneParameter(PARAMETER_VENUE, BLANK);
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_DESCRIPTION, _desc);
	}

	@Override
	protected boolean checkIfIncorrectArguments(){
		return false;
	}
	
	
	private void splitInputParameters(){
		checkAndRemoveDate();
		
		_sc = new Scanner(input).useDelimiter("\\s-");
		while(_sc.hasNext()){
			String nextParam = _sc.next();
			if (_count == 0){
				_desc = nextParam.trim();
			} else {
				parseNextParam(nextParam.trim());
			}
			_count++;
		}
		_sc.close();
	}
	
	private void checkAndRemoveDate() {
		String[] splitInput = input.split(SPACE);
		String newInput = EMPTY;
		
		for (int i=0; i<splitInput.length; i++){
			if (splitInput[i].toLowerCase().equals("-d")){
				getDeadline(splitInput[i+1]);
				splitInput[i+1] = EMPTY;
			} else {
				newInput += splitInput[i] + " ";
			}
		}
		input = newInput;
	}

	private void parseNextParam(String param){
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
			inputStartTime(param);
			break;
		case "e": 
			inputEndTime(param);
			break;
		case "c":
			inputCategory(param);
			break;
		default:
			invalidParam();
		}
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
	
	private void inputDeadline(String deadline){
		putOneParameter(PARAMETER_DEADLINE, deadline);
	}
	
	private void inputVenue(String param) {
		putOneParameter(PARAMETER_VENUE, param);		
	}

	//TODO: Check if splitParam[0] is valid time and splitParam[1] is valid Date
	private void inputStartTime(String param) {
		String[] splitParam = param.split(COMMA);
		
		if (isValidTimeArgs(splitParam)){
			//deprecated for flexi commands 
			//putOneParameter(PARAMETER_START_TIME, stripWhiteSpaces(splitParam[0]));
			
			String startTime = EMPTY;
			try {
				startTime = DateAndTimeManager.getInstance().parseTimeInput(stripWhiteSpaces(splitParam[0]));
			} catch (TimeErrorException | InvalidTimeException e) {
				outputErrorTimeMessage(startTime);
				_invalidParameters = true;
				return;
			}
			putOneParameter(PARAMETER_START_TIME, startTime);
		
			String startDate = EMPTY;
			if (splitParam.length == LENGTH_TIME){
				try {
					startDate = DateAndTimeManager.getInstance().parseDate(stripWhiteSpaces(splitParam[1]));
				} catch (InvalidDateException | DatePassedException e) {
					InputManager.outputToGui(e.getMessage()); 
					_invalidParameters = true;
					return;
				}
				putOneParameter(PARAMETER_START_DATE, startDate);
			}
		}
	}
	
	private void outputErrorTimeMessage(String input) {
		ErrorMessages.timeErrorMessage(input);
	}

	@SuppressWarnings("unused")
	private void getDescInQuotes(){
		Pattern pattern = Pattern.compile(".*(\\\"|\\\')(.*)(\\\"|\\\').*"); 
		Matcher m = pattern.matcher(input);
		while (m.find()){
			_desc = m.group(2);
		}
	}

	private void inputEndTime(String param) {
		String[] splitParam = param.split(",");
		
		if (isValidTimeArgs(splitParam)){
			//deprecated for flexi commands
			//putOneParameter(PARAMETER_END_TIME, stripWhiteSpaces(splitParam[0])); 
			String endTime = EMPTY;
			
			try {
				endTime = DateAndTimeManager.getInstance().parseTimeInput(stripWhiteSpaces(splitParam[0]));
			} catch (TimeErrorException | InvalidTimeException e) {
				outputErrorTimeMessage(endTime);
				_invalidParameters = true;
				return;
			}
			
			putOneParameter(PARAMETER_END_TIME, endTime);
			
			String endDate = EMPTY;
			if (splitParam.length == LENGTH_TIME){
				try {
					endDate = DateAndTimeManager.getInstance().parseDate(stripWhiteSpaces(splitParam[1]));
				} catch (DatePassedException | InvalidDateException e) {
					InputManager.outputToGui(e.getMessage());
					_invalidParameters = true;
					return;
				}
				putOneParameter(PARAMETER_END_DATE, endDate);
			}
		}
	}

	private void inputCategory(String param){
		putOneParameter(PARAMETER_CATEGORY, param);
	}
	
	private void invalidParam() {
		_invalidParameters = true;
	}
	
	@SuppressWarnings("unused")
	private boolean isNotDelimitedString(){
		if (_count == 1){
			return true;
		}
		return false;
	}
	
	private String removeFirstChar(String input) {
		return input.replaceFirst(getFirstChar(input), Add.BLANK).trim();
	}
	
	private String getFirstChar(String input) {
		String firstChar = input.trim().split("\\s+")[0];
		return firstChar;
	}
	
	private boolean isValidTimeArgs(String[] args){
		if (args.length > LENGTH_TIME){
			outputTimeArgsError(args.length);
			return false;
		} else {
			return true;
		}
	}
	
	private String outputTimeArgsError(int length){
		String errorMessage = String.format(MESSAGE_ERROR_TIME, length);
		InputManager.outputToGui(errorMessage);
		return errorMessage;
	}
	
	private String stripWhiteSpaces(String input){
		return input.replaceAll(Add.SPACE, Add.BLANK);
	}

}
