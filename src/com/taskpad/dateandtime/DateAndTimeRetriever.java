package com.taskpad.dateandtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

/**
 * This class is for us to find the existence of Date and Time in an input String
 * 
 * Supposed to put all the protected methods from DateAndTimeManager here
 * 
 * @author Lynnette & Jun
 *
 */

public class DateAndTimeRetriever{
	
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
		String holidayInput = HolidayDates.getInstance().replaceHolidayDate(input);
		
		//step three: find dayParser words and find words before (i.e. next/prev) and replace with date
		//we can find substrings and put through dayParser, then find the longest substring
		//and find the date
		String dayInput = parseDay(input);
		
		//step four: find dates -- find month words & find number before and after
		//Basically I find every substring and parse through DateParser
		String dateInput = parseDate(input);
		
		//step five: find PM or AM words and find time unit before and replace with time
		String timeInput = parseTime(input);
		
		//"..." deadlinedate endtime startdate starttime
		return input;
	}

	/**
	 * Takes in a string and replaces number written in words with the numeric ones
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
	
	private static String parseDay(String input){
		return input;
	}
		
	/**
	 * Takes in a string and checks to find if there are time 
	 * @param input
	 * @return input with time replaced in HH:mm format
	 */
	private static String parseTime(String input){
		int length = input.length();
		String sub;
		
		for (int i=0; i<length; i++){
			for (int j=1; j<=length-i; j++){
				sub = input.substring(i, i+j);
				try {
					String time = TimeParser.parseTimeInput(sub);
					//input = input.replace(sub, time);
				} catch (TimeErrorException | InvalidTimeException e) {
					//ignore
				}
			}
		}
		return input;
	}
	
	/**
	 * Takes in a string and checks to find if there are dates 
	 * @param input
	 * @return input with date replaced in dd/mm/yyyy format
	 */
	private static String parseDate(String input){
		int length = input.length();
		String sub;
		ArrayList<String> subStrings = new ArrayList<String>();
		
		for (int i=0; i<length; i++){
			for (int j=1; j<=length-i; j++){
				sub = input.substring(i, i+j);
				subStrings.add(sub);
			}
		}
		
		/**
		 * For JUNWEI: I think this part here got problem...
		 * basically i sort the substrings by desc length, so 23-03-2014 will be checked before
		 * 23-03-20
		 * If the date has passed, like 23-03-2014, it ignores, then I dont know how to solve the problem?
		 */
		subStrings = sortSubStringsDescLength(subStrings);
		String date = "";
		for (int i=0; i<subStrings.size(); i++){
			try {
				date = DateParser.getInstance().parseDate(subStrings.get(i).trim());
				input = input.replace(subStrings.get(i), date);
			} catch (InvalidDateException | DatePassedException e) {
				//ignore
			}
		}
		
		return input;
	}
	
	private static ArrayList<String> sortSubStringsDescLength(ArrayList<String> subStrings){
		Collections.sort(subStrings, new Comparator<String>(){
			@Override
			public int compare(String s1, String s2) {
				return s2.length() - s1.length();
			}
		});
		
		return subStrings;
	}
	
	
	public static void main (String[] args){
		System.out.println(createDesc("aaaa"));
		System.out.println(createDesc("\"aaaa"));
		System.out.println(createDesc("\"aaaa\""));          
		System.out.println(parseNumber("one one one aaa one one one"));
		System.out.println(parseNumber("one one one aaa"));
		System.out.println(parseNumber("aaa"));
		System.out.println(parseDate("23-12 hello"));
		System.out.println(parseDate("23 Dec hello"));
		System.out.println(parseTime("8am hello"));
		System.out.println(parseTime("8.15 pm hi"));
	}
}
