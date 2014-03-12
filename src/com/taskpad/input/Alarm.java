package com.taskpad.input;

import com.taskpad.alarm.AlarmManager;
import com.taskpad.dateandtime.NumberParser;

/**
 * 
 * @author Jun and Lynnette
 *
 * To implement an alarm
 * 
 * Syntax: alarm <desc> <time count> <time unit>
 * 
 * Let Alarm not to be implemented with other commands 1st
 * Implement Alarm to Executor later.
 * 
 */
public class Alarm{	

	private static final int HOUR = 60 * 60;
	private static final int MINUTE = 60;
	private static final int SECOND = 1;
	private static final String SPACE = " ";
	private static final Exception EXCEPTION_INVALID_INPUT = new Exception();//don't know choose which
	private final String ERROR = "ERROR!";
	private int _multiple = 0;
	
	private static final String MESSAGE_NUMBER_ERROR = "Error: Invalid time format %s";
		
	public Alarm(String input, String fullInput) {
		try {
			initializeAlarm(input, fullInput);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private void initializeAlarm(String input, String fullInput) throws Exception{
		
		String inputString[] = fullInput.split(SPACE);
		int length = inputString.length;
		
		String unit = inputString[length - 1];
		calculateMultiple(unit);

		//String numberString = computeNumberString(inputString, length);
		String numberString = inputString[length-2].trim();
		numberString = parseNumber(numberString);
		
		int time = 0;
		try{
			time = Integer.parseInt(numberString);
		} catch (NumberFormatException e){
			InputManager.outputToGui(String.format(MESSAGE_NUMBER_ERROR, numberString));
			System.err.println(e.getMessage());
			return;
		}
		
		time *= _multiple;
		
		String desc = findDesc(inputString, length);
			
		InputManager.outputToGui("Creating alarm... " + fullInput);
		
		AlarmManager.initializeAlarm(desc, time);		
	}

	private String parseNumber(String numberString) throws NullPointerException{
		NumberParser parser = new NumberParser();
		return parser.parseTheNumbers(numberString);
	}

	private String findDesc (String[] inputString, int length){
		String description = "";
		for (int i = 1; i < length - 2; i++){
			description = description + inputString[i] + SPACE;
		}
		description = description.trim(); 
		return description;
	}
	
	/* Deprecated - can use for finding desc instead
	private String computeNumberString(String[] inputString, int length) {
		String numberString = "";
		for (int i = 1; i < length - 1; i++){
			numberString = numberString + inputString[i] + SPACE;
		}
		numberString = numberString.trim(); 
		return numberString;
	}
	*/
	
	private void calculateMultiple(String unit) throws Exception {
		switch (unit.toLowerCase()){
		case "s":
		case "second":
		case "seconds":
		case "sec":
		case "secs":
			setMultiple(SECOND);
			break;

		case "m":
		case "minute":
		case "minutes":
		case "min":
		case "mins":
			setMultiple(MINUTE);
			break;

		case "h":
		case "hour":
		case "hours":
		case "hr":
		case "hrs":
			setMultiple(HOUR);
			break;

		default:
			InputManager.outputToGui(ERROR);
			throw EXCEPTION_INVALID_INPUT;
		}
	}

	private void setMultiple(int multiple) {
		_multiple = multiple;
	}
}
