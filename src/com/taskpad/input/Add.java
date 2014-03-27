package com.taskpad.input;

import java.util.Scanner;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.DatePassedException;
import com.taskpad.dateandtime.InvalidDateException;

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
	private static String PARAMETER_CATEGORY = "CATEGORY";
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
		putOneParameter(PARAMETER_CATEGORY, STRING_EMPTY);
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
		// TODO Auto-generated method stub
		
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

	private void inputDeadline(String deadline) {
		putOneParameter(PARAMETER_DEADLINE, deadline);		
	}
	
	private void inputDesc(String desc) {
		putOneParameter(PARAMETER_DESCRIPTION, desc);		
	}
	
	private void parseNextParam(String trim) {
		// TODO Auto-generated method stub
		
	}
	
	private String stripWhiteSpaces(String input){
		return input.replaceAll(STRING_SPACE, STRING_EMPTY);
	}

}
