package com.taskpad.input;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Add extends Command{
	
	private static final String QUOTE = "\"";
	private static final String BLANK = "";
	private static final String DASH = "-";
	private static final String SPACE = " ";
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
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_ADD);
		_sc = new Scanner(System.in);
		_count = 0;
		_invalidParameters = false;
	}
	
	@Override
	protected boolean commandSpecificRun() {
		input = putDescToFirst();
		
		if(!_invalidParameters){
			splitInputParameters();
		}
		
		/* Not ready
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
			invalidParam();
			tempDesc = new StringBuffer(BLANK);
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
	
	
	/**
	 * Lynnette, bug here:
	 * if user keys in "11-11-2014" then this method will be failed.
	 * Delete these comments when u see it.
	 * Change it if possible. :) 
	 * 
	 * Jun Wei
	 */
	@SuppressWarnings("resource")
	private void splitInputParameters(){
		_sc = new Scanner(input).useDelimiter("\\s-");
		while(_sc.hasNext()){
			String nextParam = _sc.next();
			if (_count == 0){
				_desc = nextParam;
			} else {
				parseNextParam(nextParam);
			}
			_count++;
		}
		_sc.close();
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
		String[] splitParam = param.split(",");
		
		if (isValidTimeArgs(splitParam)){
			putOneParameter(PARAMETER_START_TIME, stripWhiteSpaces(splitParam[0]));
			if (splitParam.length == LENGTH_TIME){
				putOneParameter(PARAMETER_START_DATE, stripWhiteSpaces(splitParam[1]));
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void getDescInQuotes(){
		Pattern pattern = Pattern.compile(".*(\\\"|\\\')(.*)(\\\"|\\\').*"); 
		Matcher m = pattern.matcher(input);
		while (m.find()){
			_desc = m.group(2);
		}

//		System.out.println(input.split("\"")[1]);
	}

	private void inputEndTime(String param) {
		String[] splitParam = param.split(",");
		
		if (isValidTimeArgs(splitParam)){
			putOneParameter(PARAMETER_END_TIME, stripWhiteSpaces(splitParam[0]));
			if (splitParam.length == LENGTH_TIME){
				putOneParameter(PARAMETER_END_DATE, stripWhiteSpaces(splitParam[1]));
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
	
	@SuppressWarnings("unused")
	private void removeDesc(){
		input.replace(_desc, Add.BLANK);
	}
	
	//To be completed: PARSE The rest for date & time
	@SuppressWarnings("unused")
	private void parseTheRest(){
		
	}
	
	private String stripWhiteSpaces(String input){
		return input.replaceAll(Add.SPACE, Add.BLANK);
	}

}
