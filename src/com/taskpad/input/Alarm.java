package com.taskpad.input;

import com.taskpad.alarm.AlarmExecutor;
import com.taskpad.dateandtime.NumberParser;
import com.taskpad.ui.GuiManager;

/**
 * 
 * @author Jun
 *
 * Created by Jun Wei
 * to analyze Alarm Task
 * 
 * Let Alarm not to be implemented with other commands 1st
 * Implement Alarm to Executor later.
 * 
 */
public class Alarm{	

	private static final String SPACE = " ";
	private static final Exception EXCEPTION_INVALID_INPUT = new Exception();//don't know choose which
	private final String ERROR = "ERROR!";
	private int _multiple = 0;

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

		String numberString = computeNumberString(inputString, length);
		numberString = parseNumber(numberString);
		
		int time = Integer.parseInt(numberString);
		time *= _multiple;
	
		InputManager.outputToGui("Creating alarm1... " + fullInput);
		
		AlarmExecutor.initializeAlarm(time);		
	}

	private String parseNumber(String numberString) throws NullPointerException{
		NumberParser parser = new NumberParser();
		return parser.parseTheNumbers(numberString);
	}

	private String computeNumberString(String[] inputString, int length) {
		String numberString = "";
		for (int i = 1; i < length - 1; i++){
			numberString = numberString + inputString[i] + SPACE;
		}
		numberString = numberString.trim(); 
		return numberString;
	}

	private void calculateMultiple(String unit) throws Exception {
		switch (unit.toLowerCase()){
		case "s":
		case "second":
		case "seconds":
			setMultiple(1);
			break;

		case "m":
		case "minute":
		case "minutes":
			setMultiple(60);
			break;

		case "h":
		case "hour":
		case "hours":
			setMultiple(60 * 60);
			break;

		default:
			GuiManager.callOutput(ERROR);
			throw EXCEPTION_INVALID_INPUT;
		}
	}

	private void setMultiple(int multiple) {
		_multiple = multiple;
	}
}
