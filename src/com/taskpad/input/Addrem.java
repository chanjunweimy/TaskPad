package com.taskpad.input;

import java.util.Scanner;

public class Addrem extends Command{
	
	private static final String COMMAND_ADD_REM = "ADDREM";
	private static final int NUMBER_ARGUMENTS = 2;
	
	private static final String PARAMETER_TASK_ID = "TASKID";
	private static final String PARAMETER_REM_DATE = "DATE";
	private static final String PARAMETER_REM_TIME = "TIME";
	
	private static String _taskID = "";
	private static String _remDate = "";
	private static String _remTime = "";
	
	private static Scanner sc;
	private static boolean _invalidParameters = false;

	public Addrem(String input, String fullInput) {
		super(input, fullInput);
		setCOMMAND(COMMAND_ADD_REM);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		sc = new Scanner(System.in);
	}

	//TODO: check for correct date and time format
	@Override
	protected boolean commandSpecificRun() {
		splitInputParameters();
		
		if (_invalidParameters){
			return false;
		}

		return true;
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_TASK_ID, _taskID);	
		putOneParameter(PARAMETER_REM_DATE, _remDate);
		putOneParameter(PARAMETER_REM_TIME, _remTime);
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_TASK_ID, _taskID);	
		putOneParameter(PARAMETER_REM_DATE, _remDate);
		putOneParameter(PARAMETER_REM_TIME, _remTime);		
	}
	
	/**
	 * I think the bug for addrem is come from here also ><
	 * JunWei
	 */
	@Override
	protected boolean isNotNumberArgs(String[] inputString){
		if (inputString.length != getNUMBER_ARGUMENTS() ||
				inputString.length != getNUMBER_ARGUMENTS()+1	){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("resource")
	private void splitInputParameters(){
		int count = 0;
		sc = new Scanner(input).useDelimiter("\\s-");
		while(sc.hasNext()){
			String nextParam = sc.next();
			if (count == 0){
				_taskID = nextParam;
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
		case "t":
			inputTime(param);
			break;
		default:
			invalidParam();
		}
	}
	
	private void getDeadline(String param) {
		param = stripWhiteSpaces(param);
		_remDate = param;
	}
	
	private void inputTime(String param) {
		param = stripWhiteSpaces(param);
		_remTime = param;
	}
	
	private String removeFirstChar(String input) {
		return input.replaceFirst(getFirstChar(input), "").trim();
	}
	
	private String getFirstChar(String input) {
		String firstChar = input.trim().split("\\s+")[0];
		return firstChar;
	}
	
	private String stripWhiteSpaces(String input){
		return input.replaceAll(" ", "");
	}
	
	private void invalidParam() {
		_invalidParameters = true;
	}
	
}
