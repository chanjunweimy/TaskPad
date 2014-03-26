package com.taskpad.dateandtime;

/**
 * This class is for us to find the existence of Date and Time in an input String
 * 
 * Supposed to put all the protected methods from DateAndTimeManager here
 * 
 * @author Lynnette
 *
 */

public class DateAndTimeRetriever {
	
	private static final String STRING_EMPTY = "";
	private static final String STRING_SPACE = " ";

	/**
	 * In an input string, check if there is valid time
	 * @param inputString
	 * @return time
	 */
	
	protected static TimeObject findTime(String inputString){
		String[] splitInput = inputString.split(STRING_SPACE);
		TimeObject timeObject = null;
		
		for (int i=0; i<splitInput.length; i++){
			String parsedTime = isValidTime(splitInput[i]);
			if (isNotEmptyParsedString(parsedTime)){
				timeObject = createNewTimeObject(parsedTime, splitInput[i]);
			}
		}
		return timeObject;
	}
	
	/* Helper methods for checking valid time in a String */
	private static String isValidTime(String input){
		input = trimInput(input);
		try {
			return TimeParser.parseTimeInput(input);
		} catch (TimeErrorException | InvalidTimeException e) {
			return STRING_EMPTY;
		}
	}
	
	private static TimeObject createNewTimeObject(String parsedTime, String inputTime){
		return new TimeObject(parsedTime.trim(), inputTime.trim());
	}
	
	/**
	 * In an input string, check if there is valid date 
	 * @param inputString
	 * @return date 
	 */
	protected static DateObject findDate(String inputString){
		String[] splitInput = inputString.split(STRING_SPACE);
		DateObject dateObject = null;
		
		for (int i=0; i<splitInput.length; i++){
			String parsedDate = isValidDate(splitInput[i]);
			if (isNotEmptyParsedString(parsedDate)){
				dateObject = createDateObject(parsedDate, splitInput[i]);
			}
		}	
		return dateObject;
	}
	
	/* Helper method for checking valid date in a String */
	private static String isValidDate(String input){
		input = trimInput(input);
		SimpleDateParser dateParser = SimpleDateParser.getInstance();
		try {
			return dateParser.parseDate(input);
		} catch (DatePassedException | InvalidDateException e) {
			return STRING_EMPTY;
		}
	}

	private static String trimInput(String input) {
		input = input.trim();
		return input;
	}
	
	private static DateObject createDateObject(String parsedDate, String input){
		return new DateObject(parsedDate, input.trim());
	}
	
	private static boolean isNotEmptyParsedString(String parsedString) {
		return !parsedString.equals(STRING_EMPTY);
	}

	
}
