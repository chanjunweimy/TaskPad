//@author A0112084U

package com.taskpad.input;

import com.taskpad.alarm.AlarmManager;
import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.NullTimeUnitException;
import com.taskpad.dateandtime.NullTimeValueException;


/**
 *
 * To implement an alarm
 * 
 * Syntax: alarm <desc> <time count> <time unit>
 * 
 * 
 */


public class Alarm{	


	private static final String SPACE = " ";

	
	private static final String MESSAGE_NUMBER_ERROR = "Error: Invalid time format %s";
		
	public Alarm(String input, String fullInput) {
		initializeAlarm(input, fullInput);
	}

	private void initializeAlarm(String input, String fullInput) {
		String numberString = null;
		int time = -1;
		
		numberString = findTimeUnit(input);
		//numberString = successParseTime(input, numberString);
		if (numberString == null){
			return;
		}
		
		time = successParseInt(numberString, time);
		
		if (time == -1){
			return;
		}
		
		String desc = findDesc(fullInput);
		
		InputManager.outputToGui("Creating alarm... " + fullInput);
		
		AlarmManager.initializeAlarm(desc, time);		
	}

	private String findTimeUnit(String input) {
		String numberString = "";
		String[] splitInput = input.split(SPACE);
		
		try {
			numberString = successParseTime(splitInput[splitInput.length-1], numberString);
		} catch (NullTimeUnitException | NullTimeValueException e) {
			String newNumberString = "";
			//Currently alarm only supports 1m and 1s and not 1y etc.
			try{
				newNumberString = splitInput[splitInput.length-2] + " " + splitInput[splitInput.length-1];
			} catch (Exception e2){
				//InputManager.outputToGui("Error: Not a valid Alarm format");
				return numberString;
			}
			try {
				numberString = successParseTime(newNumberString, numberString);
			} catch (NullTimeUnitException | NullTimeValueException e1) {
				InputManager.outputToGui(e.getMessage());
			}
		}
		
		return numberString;
	}

	private String successParseTime(String input, String numberString) throws NullTimeUnitException, NullTimeValueException {
		DateAndTimeManager parser = DateAndTimeManager.getInstance();
		numberString = parser.convertToSecond(input);

		return numberString;
	}

	//error happens when time = -1 
	public int successParseInt(String numberString, int time) {
		try{
			time = Integer.parseInt(numberString);
		} catch (NumberFormatException e){
			InputManager.outputToGui(String.format(MESSAGE_NUMBER_ERROR, numberString));
			time = -1;
		}
		return time;
	}

	private String findDesc (String fullInput){
		String inputString[] = fullInput.split(SPACE);
		int length = inputString.length;
		
		String description = "";
		if (length == 4){
			for (int i = 1; i < length - 2; i++){
				description = description + inputString[i] + SPACE;
			}
		} else if (length == 3){
			for (int i = 1; i < length - 1; i++){
				description = description + inputString[i] + SPACE;
			}
		} 

		description = description.trim(); 
		return description;
	}
}
