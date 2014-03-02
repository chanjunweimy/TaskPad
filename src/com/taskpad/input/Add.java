package com.taskpad.input;

import java.util.Scanner;

public class Add extends Command{
	
	private static String COMMAND_ADD = "ADD";
		
	private static String PARAMETER_DEADLINE_DAY = "DAY";
	private static String PARAMETER_DEADLINE_MONTH = "MONTH";
	private static String PARAMETER_DEADLINE_YEAR = "YEAR";
	private static String PARAMETER_START = "START";
	private static String PARAMETER_END = "END";
	private static String PARAMETER_VENUE = "VENUE";
	private static String PARAMETER_DESCRIPTION = "DESC";
	private static String PARAMETER_CATEGORY = "CATEGORY";
	
	private static boolean invalidParameters = false;
	
	private static Scanner sc;

	public Add(String input) {
		super(input);
		setNUMBER_ARGUMENTS(1);
		setCOMMAND(COMMAND_ADD);
		sc = new Scanner(System.in);
	}
	
	@Override
	protected boolean commandSpecificRun() {
		splitInputParameters();
		
		if (invalidParameters){
			return false;
		}
		
		return true;		
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_CATEGORY, "");
		putOneParameter(PARAMETER_DEADLINE_DAY, "");
		putOneParameter(PARAMETER_DEADLINE_MONTH, "");
		putOneParameter(PARAMETER_DEADLINE_YEAR, "");
		putOneParameter(PARAMETER_DEADLINE_YEAR, "");
		putOneParameter(PARAMETER_DESCRIPTION, "");
		putOneParameter(PARAMETER_END, "");
		putOneParameter(PARAMETER_START, "");
		putOneParameter(PARAMETER_VENUE, "");
	}

	@Override
	protected void putInputParameters() {		
	}

	@Override
	protected boolean checkIfIncorrectArguments(){
		return false;
	}
	
	private void splitInputParameters(){
		int count = 0;
		sc = new Scanner(input).useDelimiter("\\s-");
		while(sc.hasNext()){
			String nextParam = sc.next();
			if (count == 0){
				putOneParameter(PARAMETER_DESCRIPTION, nextParam);
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
		case "c":
			inputCategory(param);
			break;
		default:
			invalidParam();
		}
	}
	
	private void getDeadline(String param) {
		String[] splitParam = param.split("/", -1);
		String day = splitParam[0];
		String month = splitParam[1];
		String year = splitParam[2];
		inputDeadlines(day,month, year);
	}
	
	private void inputDeadlines(String day, String month, String year){
		inputParameters.put(PARAMETER_DEADLINE_DAY, day);
		inputParameters.put(PARAMETER_DEADLINE_MONTH, month);
		inputParameters.put(PARAMETER_DEADLINE_YEAR, year);
	}
	
	private void inputVenue(String param) {
		inputParameters.put(PARAMETER_VENUE, param);		
	}

	private void inputStartTime(String param) {
		inputParameters.put(PARAMETER_START, param);	
		
	}

	private void inputEndTime(String param) {
		inputParameters.put(PARAMETER_END, param);	
	}

	private void inputCategory(String param){
		inputParameters.put(PARAMETER_CATEGORY, param);
	}
	
	private void invalidParam() {
		invalidParameters = true;
	}
	
	private String removeFirstChar(String input) {
		return input.replaceFirst(getFirstChar(input), "").trim();
	}
	
	private String getFirstChar(String input) {
		String firstChar = input.trim().split("\\s+")[0];
		return firstChar;
	}

}
