package com.taskpad.dateandtime;

import java.util.Scanner;

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
		DateAndTimeManager datmParser = DateAndTimeManager.getInstance();
		
		
		String desc = createDesc(input);
		
		//step one: convert all number words to numbers using number parser		
		String numberedInput = parseNumber(input);
		
		//step two: find holiday words and replace with date
		String[] numberInputTokens = numberedInput.split(" ");
		boolean[] isModified = new boolean[numberInputTokens.length];
		StringBuffer holidayString = new StringBuffer();
		
		for (int i = 0; i < isModified.length; i++){
			isModified[i] = false;
		}
		
		for (int i = 0; i < numberInputTokens.length; i++){
			String token = numberInputTokens[i];
			
			//search 1 word
			String holidayInput = HolidayDates.getInstance().replaceHolidayDate(token);
			String pastOneToken, pastTwoToken;
			if (holidayInput != null){
				numberInputTokens[i] = holidayInput;
				isModified[i] = true;
				//holidayString.append(holidayInput + " ");
				continue;
			}
				
			//search 2 words:
			if (i >= 1 && !isModified[i - 1]){
				pastOneToken = numberInputTokens[i - 1];
				holidayInput = HolidayDates.getInstance().replaceHolidayDate(pastOneToken + " " + token);
				if (holidayInput != null){
					numberInputTokens[i] = holidayInput;
					numberInputTokens[i - 1] = null;
					isModified[i] = true;
					isModified[i - 1] = true;
					//holidayString.append(holidayInput + " ");
				}
				continue;
			}
			
			//search 3 words:
			if (i >= 2 && !isModified[i - 2] && !isModified[i - 1]){
				pastOneToken = numberInputTokens[i - 1];
				pastTwoToken = numberInputTokens[i - 2];
				holidayInput = HolidayDates.getInstance().replaceHolidayDate(
						pastTwoToken + " " + 
						pastOneToken + " " + token);
				if (holidayInput != null){
					numberInputTokens[i] = holidayInput;
					numberInputTokens[i - 1] = null;
					numberInputTokens[i - 2] = null;
					isModified[i] = true;
					isModified[i - 1] = true;
					isModified[i - 2] = true;
					//holidayString.append(holidayInput + " ");
				}
				continue;
			}
		}

		//step three: find dayParser words and find words before (i.e. next/prev) and replace with date
		//step four: find dates -- find month words & find number before and after
		//step four b: find dates -- find three consecutive numbers and try parse as date
		//step five: find PM or AM words and find time unit before and replace with time
		
		//return that string to parse in respective Add/Addrem/Alarm classes - already done with return input
		return input;
	}

	/**
	 * @param input
	 * @param datmParser
	 */
	private static String parseNumber(String input) {
		DateAndTimeManager datmParser = DateAndTimeManager.getInstance();
		Scanner sc = new Scanner(input);
		StringBuffer changedString = new StringBuffer();
		StringBuffer numberString = new StringBuffer();
		boolean isNumberContinue = false;
		while (sc.hasNext()){
			String token = sc.next();
			if (!datmParser.isNumber(token)){
				if (isNumberContinue){
					String realNumber = datmParser.parseNumber(numberString.toString().trim());
					changedString.append(realNumber + " ");
					numberString = new StringBuffer();
				}
				
				changedString.append(token + " ");
				isNumberContinue = false;
			} else {
				if (!isNumberContinue){
					isNumberContinue = true;
				}
				numberString.append(token + " ");
			}
		}
		
		String realNumber = datmParser.parseNumber(numberString.toString().trim());
		if (realNumber != null){
			changedString.append(realNumber + " ");
		}

		sc.close();
		return changedString.toString().trim();
	}

	/**
	 * @param input
	 */
	private static String createDesc(String input) {
		String desc = input.trim();
		if (!desc.startsWith("\"")){
			desc = "\"" + desc;
		}
		
		if (!input.endsWith("\"")){
			desc = desc + "\"";
		}
		return desc;
	}
	
	public static void main (String[] args){
		System.out.println(createDesc("aaaa"));
		System.out.println(createDesc("\"aaaa"));
		System.out.println(createDesc("\"aaaa\""));          
		System.out.println(parseNumber("one one one aaa one one one"));
		System.out.println(parseNumber("one one one aaa"));
		System.out.println(parseNumber("aaa"));
	}

	
}
