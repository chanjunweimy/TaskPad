package com.TaskPad.inputproc;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Add {
	
	private static String input;
	private static Map<String,String> inputParameters;
	private static Scanner sc = new Scanner(System.in);
	private boolean invalidParameters = false;
	
	private static String MESSAGE_EMPTY_INPUT = "Error: Empty Input. Type 'help' if you need to! :)";
	private static String COMMAND_ADD = "ADD";
	
	private static String PARAMETER_DEADLINE_DAY = "DAY";
	private static String PARAMETER_DEADLINE_MONTH = "MONTH";
	private static String PARAMETER_DEADLINE_YEAR = "YEAR";
	private static String PARAMETER_START = "START";
	private static String PARAMETER_END = "END";
	private static String PARAMETER_VENUE = "VENUE";
	private static String PARAMETER_DESCRIPTION = "DESC";

	public Add(String input){
		this.input = input;
		this.inputParameters = new HashMap<String,String>();
	}
	
	public Map<String,String> run(){
		if (isEmptyString()){
			this.inputParameters.clear();
			return this.inputParameters;
		} 
		
		splitInputParameters();
		
		if (invalidParameters){
			this.inputParameters.clear();
		}
		
		return this.inputParameters;
	}
	
	private boolean isEmptyString(){
		if (this.input.isEmpty()){
			return true;
		}
		return false;
	}
	
	private void splitInputParameters(){
		int count = 0;
		sc = new Scanner(this.input).useDelimiter("\\s-");
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
		default:
			invalidParam();
		}
	}

	private void getDeadline(String param) {
		sc = new Scanner(this.input).useDelimiter("\\s/");
		String day = sc.next();
		String month = sc.next();
		String year = sc.next();
		inputDeadlines(day,month, year);
	}
	
	private void inputDeadlines(String day, String month, String year){
		this.inputParameters.put(PARAMETER_DEADLINE_DAY, day);
		this.inputParameters.put(PARAMETER_DEADLINE_MONTH, month);
		this.inputParameters.put(PARAMETER_DEADLINE_YEAR, year);
	}

	private void inputVenue(String param) {
		this.inputParameters.put(PARAMETER_VENUE, param);		
	}

	private void inputStartTime(String param) {
		this.inputParameters.put(PARAMETER_START, param);	
		
	}

	private void inputEndTime(String param) {
		this.inputParameters.put(PARAMETER_END, param);	
	}

	private void invalidParam() {
		this.invalidParameters = true;
	}
	
	private static String removeFirstChar(String input) {
		return input.replace(getFirstChar(input), "").trim();
	}
	
	private static String getFirstChar(String input) {
		String firstChar = input.trim().split("\\s+")[0];
		return firstChar;
	}

}
