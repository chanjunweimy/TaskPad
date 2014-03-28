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

	/**
	 * In an input string, check if there is valid time
	 * @param inputString
	 * @return time
	 */
	
	protected static TimeObject findTime(String inputString){
		TimeObject timeObject = null;
		
		String parsedTime = isValidTime(inputString);
		if (isNotEmptyParsedString(parsedTime)){
			timeObject = createNewTimeObject(parsedTime, inputString);
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
		DateObject dateObject = null;
		
		String parsedDate = isValidDate(inputString);
		if (isNotEmptyParsedString(parsedDate)){
			dateObject = createDateObject(parsedDate, inputString);
		}
		return dateObject;
	}
	
	/* Helper method for checking valid date in a String */
	private static String isValidDate(String input){
		input = trimInput(input);
		DateParser dateParser = DateParser.getInstance();
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
	
	/**
	 * FOR JUNWEI: 
	 * formatDateAndTimeInString
	 * This method takes in an input string and returns the string 
	 * with all the number words converted to numbers, special words converted to date
	 * and time words converted to time
	 */

	protected static String formatDateAndTimeInString(String input) {
		//step one: convert all number words to numbers using number parser
		//step two: find holiday words and replace with date
		//input = HolidayDates.getInstance().replaceHolidayDate(input);
		//step three: find dayParser words and find words before (i.e. next/prev) and replace with date
		//step four: find dates -- find month words & find number before and after
		//step four b: find dates -- find three consecutive numbers and try parse as date
		//step five: find PM or AM words and find time unit before and replace with time
		
		//return that string to parse in respective Add/Addrem/Alarm classes - already done with return input
		return input;
	}

	
}
