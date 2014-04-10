//@author A0112084U

package com.taskpad.input;

import java.util.logging.Logger;

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
	private final static Logger LOGGER = Logger.getLogger("TaskPad");

	private static final String SPACE = " ";

	
	private static final String MESSAGE_NUMBER_ERROR = "Error: Invalid time format %s";
		
	public Alarm(String input, String fullInput) {
		initializeAlarm(input, fullInput);
	}

	private void initializeAlarm(String input, String fullInput) {
		String numberString = null;
		int time = -1;
		
		String[] splitInput = input.split(SPACE);
		numberString = findTimeUnit(splitInput);
		//numberString = successParseTime(input, numberString);
		LOGGER.info("initializing alarm......");
		LOGGER.info("numberString is : " + numberString);
		
		if (numberString == null){
			LOGGER.severe("can't find time unit...");
			return;
		}
		
		time = successParseInt(numberString);
		
		if (time == -1){
			LOGGER.severe("can't parse numberString to int... time = -1");
			return;
		}
		
		LOGGER.info("time is " + time);
		
		//String desc = findDesc(fullInput);
		String desc = findDesc(splitInput);
		
		LOGGER.info("desc is " + desc);
		
		InputManager.outputToGui("Creating alarm... " + fullInput);
		
		AlarmManager.initializeAlarm(desc, time);		
	}
	


	/**
	 * @param numberString
	 * @param time
	 * @return
	 */
	//error happens when time = -1 
	public int successParseInt(String numberString) {
		int time = -1;
		try{
			time = Integer.parseInt(numberString);
		} catch (NumberFormatException e){
			InputManager.outputToGui(String.format(MESSAGE_NUMBER_ERROR, numberString));
			time = -1;
		}
		return time;
	}

	/**
	 * @param splitInput
	 * @return
	 */
	private String findTimeUnit(String[] splitInput) {
		int sec = 0;
		
		LOGGER.info("findTimeUnit returns seconds...... Now starting to find! ");
		
		for (int i = splitInput.length - 1; i >= 0; i--){
			String numberString = successParseTime(splitInput[i]);
			
			LOGGER.info("1st: numberString is " + numberString);
			
			if (i == 0 && numberString == null){
				break;
			}
			
			if (numberString == null){
				String newNumberString = splitInput[i - 1] + " " + splitInput[i];
				numberString = successParseTime(newNumberString);
				
				LOGGER.info("2nd: numberString is " + numberString);
				
				if (numberString == null){
					LOGGER.info("has ntg");
					continue;
				} else {
					int num = successParseInt(numberString);
					sec += num;
					
					splitInput[i] = null;
					splitInput[i - 1] = null;
					
					LOGGER.info("num is " + num);
					LOGGER.info("sec is " + sec);
					i--;
				}
			} else {
				splitInput[i] = null;
				int num = successParseInt(numberString);
				sec += num;
				LOGGER.info("num is " + num);
				LOGGER.info("sec is " + sec);
			}
			
		}

		LOGGER.info("Overall time is " + sec);
		/* DEPRECATED
		try {
			numberString = successParseTime(splitInput[splitInput.length - 1], numberString);
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
		*/
		
		return "" + sec;
	}

	/**
	 * 
	 * @param input
	 * @param numberString
	 * @return
	 * @throws NullTimeUnitException
	 * @throws NullTimeValueException
	 */
	private String successParseTime(String input){
		DateAndTimeManager parser = DateAndTimeManager.getInstance();
		String numberString = "";
		try {
			LOGGER.info("parsing time word. Input is " + input);
			numberString = parser.convertToSecond(input);
		} catch (NullTimeUnitException | NullTimeValueException e) {
			LOGGER.severe("Parse failed! :( return null!");
			return null;
		}
		LOGGER.info("PARSED! numberString is " + numberString);

		return numberString;
	}
	
	/**
	 * findDesc(String[]) replaced findDesc(String)
	 * @param splitInput
	 * @return
	 */
	private String findDesc(String[] splitInput) {
		StringBuffer descBuilder = new StringBuffer();
		
		for (String token : splitInput){
			if (token != null){
				descBuilder.append(token + SPACE);			
			}
		}
		return descBuilder.toString().trim();
	}

	/**
	 * replaced by findDesc(String[])
	 * @deprecated
	 * @param fullInput
	 * @return
	 */
	@SuppressWarnings("unused")
	private String findDesc (String fullInput){
		LOGGER.info("finding description in... " + fullInput);
		
		String inputString[] = fullInput.split(SPACE);
		int length = inputString.length;
		
		LOGGER.info("Size of InputString[] is " + inputString.length);
		
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
		
		LOGGER.info("description is " + description);

		description = description.trim(); 
		return description;
	}
}
