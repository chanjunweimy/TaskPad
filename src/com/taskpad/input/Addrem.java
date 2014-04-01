package com.taskpad.input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.DateObject;
import com.taskpad.dateandtime.DatePassedException;
import com.taskpad.dateandtime.InvalidTimeException;
import com.taskpad.dateandtime.TimeErrorException;
import com.taskpad.dateandtime.TimeObject;
import com.taskpad.ui.GuiManager;

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
	
	private static DateObject _dateObject;
	private static TimeObject _timeObject;
	
	private static Scanner sc;
	private static boolean _invalidParameters = false;
	private static boolean _isFlexiString = false;
	
	boolean _gotTaskID = false;
	boolean _gotDate = false;
	boolean _gotTime = false;

	public Addrem(String input, String fullInput) {
		super(input, fullInput);
	}
	
	@Override
	protected void initialiseOthers(){
		setCOMMAND(COMMAND_ADD_REM);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		
		sc = new Scanner(System.in);
		_dateObject = null;
		_timeObject = null;
	}

	@Override
	protected boolean commandSpecificRun() {		
		if (!_isFlexiString){
			splitInputParameters();
		} else {
			splitInputNoDelimiters();
		}
		
		//putInputParameters();
		
		try {
			checkTimeAndDate();
		} catch (DatePassedException e) {
			GuiManager.callOutput(e.getMessage());
		}
				
		if (_invalidParameters){
			return false;
		} 

		//GuiManager.callOutput("Reminder added! " + " " + _taskID + ": " +  _remDate + " " + _remTime);
		
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
	
	@Override
	protected boolean checkIfIncorrectArguments() throws InvalidParameterException, TaskIDException{
		if(checkIfContainsDelimiters()){
			String inputString[] = input.split(" ");
			
			if (isNotNumberArgs(inputString)){
				throw new InvalidParameterException();
			}
			
			if(isNotValidTaskID(inputString[0])){
				throw new TaskIDException(inputString[0]);
			}
		}
		_isFlexiString = true;
		return false;
	}
	
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
	
	private void splitInputNoDelimiters(){
		//input = DateAndTimeManager.getInstance().formatDateAndTimeInString(input);
		String[] splitInput = input.split(SPACE);
		
		try {
			checkIfInvalidParameters(splitInput.length);
		} catch (InvalidParameterException e) {
			GuiManager.callOutput(e.getMessage());
			return;
		};
		
		extractTimeAndDate(splitInput);
		
		invalidIfNoTaskID();
		invalidIfNoDateOrTime();
	}
	
	private void invalidIfNoTaskID(){
		if (_taskID.equals("")){
			_invalidParameters = true;
			InputManager.outputToGui("Invalid Task ID");
		}
	}
	
	private void invalidIfNoDateOrTime(){
		if(_remDate.equals("") && _remTime.equals("")){
			_invalidParameters = true;
			InputManager.outputToGui("No date or time input");
		}
	}
	
	private void extractTimeAndDate(String[] splitInput){	
		for (int i=0; i<splitInput.length; i++){
			if(!_gotDate && isDateObject(splitInput[i])){
				_remDate = _dateObject.getParsedDate();
				_gotDate = true;
			} else if (!_gotTime && isTimeObject(splitInput[i])){
				_remTime = _timeObject.getParsedTime();
				_gotTime = true;
				
			} else if (!_gotTaskID){
				try {
					enterTaskID(splitInput[i]);
				} catch (TaskIDException e) {
					continue;
				}
			}
		}
	}
	
	private boolean isTimeObject(String input) {
		_timeObject = DateAndTimeManager.getInstance().findTime(input);
		if (_timeObject != null){
			return true;
		}
		return false;
	}

	private boolean isDateObject(String input) {
		_dateObject = DateAndTimeManager.getInstance().findDate(input);
		if (_dateObject != null){
			return true;
		}
		return false;
	}

	private void enterTaskID(String taskID) throws TaskIDException{
		taskID = taskID.trim();
		if (isNotValidTaskID(taskID)){
			_invalidParameters = true;
			throw new TaskIDException(taskID);
		} else{
			_taskID = taskID;
			_gotTaskID = true;
		}
	}
	
	private boolean checkIfInvalidParameters(int length) throws InvalidParameterException{
		if (length == NUMBER_ARGUMENTS || length == NUMBER_ARGUMENTS + 1){
			return true;
		} else{
			throw new InvalidParameterException();
		}
	}
	
	/* deprecated for flexiCommands without delimiters
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
				//ErrorMessages.invalidTimeMessage();
				InputManager.outputToGui(e.getMessage());
				_invalidParameters = true;
				return;
			}
		}
	}
	*/

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
		//_remTime = DateAndTimeManager.getInstance().parseTime(param.trim());
		try {
			_remTime = DateAndTimeManager.getInstance().parseTimeInput(param.trim());
		} catch (TimeErrorException | InvalidTimeException e) {
			//ErrorMessages.timeErrorMessage(_remTime);
			InputManager.outputToGui(e.getMessage());
			return;
		}
	}
	
	/** TO JUNWEI: I think got bug in this method :( 
	 * 
	 * Checks if the rem time and date added is after current time and date
	 * If so, throw exception, and populate invalidParameters
	 * @throws DatePassedException 
	 */
	private void checkTimeAndDate() throws DatePassedException {
		Date now = new Date();
		Date date = null;
		date = parseRemDateAndTime(date);
				
		//It goes in this loop here :( 
		if (now.compareTo(date) > 0){
			_invalidParameters = true;
			throw new DatePassedException();
		}
	}

	private Date parseRemDateAndTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String enteredDateAndTime = formatRemString();
		try {
			date = sdf.parse(enteredDateAndTime);
		} catch (ParseException e) {
			//Do nothing
		}
		return date;
	}
	
	private String formatRemString(){
		String dateString = formatParaDate() + " " + formatParaTime();
		return dateString;
	}
	
	private String formatParaDate(){
		String dateString = "";
		if (_remDate.equals("")){
			dateString += DateAndTimeManager.getInstance().getTodayDate();
		}else {
			dateString += _remDate;
		}
		return dateString;
	}
	
	private String formatParaTime(){
		String timeString = "";
		if(_remTime.equals("")){
			timeString += DateAndTimeManager.getInstance().getTodayTime();
		}else {
			timeString += _remTime;
		}
		
		return timeString;
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
