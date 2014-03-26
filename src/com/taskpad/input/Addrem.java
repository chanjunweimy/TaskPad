package com.taskpad.input;

import java.util.Scanner;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidTimeException;
import com.taskpad.dateandtime.TimeErrorException;

public class Addrem extends Command{
	
	private static final String COMMAND_ADD_REM = "ADDREM";
	private static final int NUMBER_ARGUMENTS = 2;
	private static final String SPACE = " ";
		
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
	}
	
	@Override
	protected void initialiseOthers(){
		setCOMMAND(COMMAND_ADD_REM);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		
		sc = new Scanner(System.in);
	}

	//TODO: check for correct date and time format
	@Override
	protected boolean commandSpecificRun() {
		if (checkIfContainsDelimiters()){
			splitInputParameters();
		} else {
			try {
				splitInputNoDelimiters();
			} catch (TaskIDException e) {
				ErrorMessages.invalidTaskIDMessage();
				_invalidParameters = true;
			}
		}
		
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
	
	@Override
	protected boolean isNotNumberArgs(String[] inputString){
		if (inputString.length == getNUMBER_ARGUMENTS() ||
				inputString.length == getNUMBER_ARGUMENTS()+1 || 
				inputString.length == getNUMBER_ARGUMENTS()+2 || 
				inputString.length == getNUMBER_ARGUMENTS()+3){
			return false;
		}
		return true;
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
	
	/**Note To do: Can identify if its a date or time string. 
	*Time will return "-1:-1" if it's not a time string
	*Should make similar for date string 
	*/
	private void splitInputNoDelimiters() throws TaskIDException {		
		String[] splitInput = input.split(SPACE);
		_taskID = splitInput[0];
		if (Integer.parseInt(_taskID) > InputManager.retrieveNumberOfTasks()+1){
			throw new TaskIDException(_taskID);
		}
		_remDate = splitInput[1];
		if (splitInput.length == 3){
			//_remTime = splitInput[2];		//deprecated for flexi commands
			try {
				_remTime = DateAndTimeManager.getInstance().parseTimeInput(splitInput[2].trim());
			} catch (TimeErrorException | InvalidTimeException e) {
				ErrorMessages.invalidTimeMessage();
				_invalidParameters = true;
				return;
			}
		}
	}

	private boolean checkIfContainsDelimiters() {
		return input.contains("-d")||input.contains("-t");
	}
	
	private void getDeadline(String param) {
		param = stripWhiteSpaces(param);
		_remDate = param;
	}
	
	private void inputTime(String param) {
		param = stripWhiteSpaces(param);
		//_remTime = param;		//deprecated for flexi commands
		System.out.println(param);
		//_remTime = DateAndTimeManager.getInstance().parseTime(param.trim());
		try {
			_remTime = DateAndTimeManager.getInstance().parseTimeInput(param.trim());
		} catch (TimeErrorException | InvalidTimeException e) {
			ErrorMessages.timeErrorMessage(_remTime);
			return;
		}
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
