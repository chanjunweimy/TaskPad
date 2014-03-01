package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Add {
	
	private static String input;
	private static Map<String,String> inputParameters;
	private static Scanner sc = new Scanner(System.in);
	private static boolean invalidParameters = false;
	
	private static String PARAMETER_DEADLINE_DAY = "DAY";
	private static String PARAMETER_DEADLINE_MONTH = "MONTH";
	private static String PARAMETER_DEADLINE_YEAR = "YEAR";
	private static String PARAMETER_START = "START";
	private static String PARAMETER_END = "END";
	private static String PARAMETER_VENUE = "VENUE";
	private static String PARAMETER_DESCRIPTION = "DESC";
	private static String PARAMETER_CATEGORY = "CATEGORY";

	public Add(String input){
		this.input = input;
		inputParameters = new HashMap<String,String>();
		initialiseParametersToNull();
	}
	
	public static Map<String,String> run(){
		if (isEmptyString()){
			inputParameters.clear();
			return inputParameters;
		} 
		
		splitInputParameters();
		
		if (invalidParameters){
			Add.inputParameters.clear();
		}
		
		return inputParameters;
	}
	
	private static void initialiseParametersToNull(){
		inputParameters.put(PARAMETER_DEADLINE_DAY, "");
		inputParameters.put(PARAMETER_DEADLINE_MONTH, "");
		inputParameters.put(PARAMETER_DEADLINE_YEAR, "");
		inputParameters.put(PARAMETER_DEADLINE_YEAR, "");
		inputParameters.put(PARAMETER_DESCRIPTION, "");
		inputParameters.put(PARAMETER_END, "");
		inputParameters.put(PARAMETER_START, "");
		inputParameters.put(PARAMETER_VENUE, "");
	}
	
	private static boolean isEmptyString(){
		if (input.isEmpty()){
			return true;
		}
		return false;
	}
	
	private static void splitInputParameters(){
		int count = 0;
		sc = new Scanner(input).useDelimiter("\\s-");
		while(sc.hasNext()){
			String nextParam = sc.next();
			if (count == 0){
				inputParameters.put(PARAMETER_DESCRIPTION, nextParam);
			} else {
				parseNextParam(nextParam);
			}
			count++;
		}
	}
	
	private static void parseNextParam(String param){
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

	private static void getDeadline(String param) {
		String[] splitParam = param.split("/", -1);
		String day = splitParam[0];
		String month = splitParam[1];
		String year = splitParam[2];
		inputDeadlines(day,month, year);
	}
	
	private static void inputDeadlines(String day, String month, String year){
		inputParameters.put(PARAMETER_DEADLINE_DAY, day);
		inputParameters.put(PARAMETER_DEADLINE_MONTH, month);
		inputParameters.put(PARAMETER_DEADLINE_YEAR, year);
	}

	private static void inputVenue(String param) {
		inputParameters.put(PARAMETER_VENUE, param);		
	}

	private static void inputStartTime(String param) {
		inputParameters.put(PARAMETER_START, param);	
		
	}

	private static void inputEndTime(String param) {
		inputParameters.put(PARAMETER_END, param);	
	}

	private static void inputCategory(String param){
		inputParameters.put(PARAMETER_CATEGORY, param);
	}
	
	private static void invalidParam() {
		invalidParameters = true;
	}
	
	private static String removeFirstChar(String input) {
		return input.replaceFirst(getFirstChar(input), "").trim();
	}
	
	private static String getFirstChar(String input) {
		String firstChar = input.trim().split("\\s+")[0];
		return firstChar;
	}

}
