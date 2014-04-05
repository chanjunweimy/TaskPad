package com.taskpad.input;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidQuotesException;

public class Search extends Command{
	
	private static final String STRING_NULL = "null";
	private static final String STRING_SPACE = " ";
	private static final String COMMAND_SEARCH = "SEARCH";
	private static final int NUMBER_ARGUMENTS = 1;
	
	private static String PARAMETER_KEYWORD = "KEYWORD";
	private static String _keyword;
	
	private static String PARAMETER_TIME = "TIME";
	private static String _time = null;

	public Search(String input, String fullInput) {
		super(input, fullInput);
	}
	
	@Override
	protected void initialiseOthers(){
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		setCOMMAND(COMMAND_SEARCH);
	}

	@Override
	public void run(){
		try {
			if (checkIfEmptyString()){
				return; 
			}
		} catch (EmptyStringException e) {
			InputManager.outputToGui(e.getMessage());
			return;
		}
		
		initialiseParametersToNull();
		commandSpecificRun();
		putInputParameters();
		createInputObject();
		passObjectToExecutor();
	}
	
	@Override
	protected boolean commandSpecificRun() {
		_keyword = input;
		
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		try {
			String timeString = datm.formatDateAndTimeInString(input);
			_time = getTime(timeString);
		} catch (InvalidQuotesException e) {
			_time = null;
		}
		
		//checkAndInputDeadline();
		
		return true;
	}

	private String getTime(String timeString) {
		String[] timeTokens = timeString.split(STRING_SPACE);
		StringBuffer timeBuilder = new StringBuffer();
		for (int i = 0; i < timeTokens.length; i++){
			String token = timeTokens[i];
			if (!STRING_NULL.equals(token)){
				timeBuilder.append(token + STRING_SPACE);
			}
		}
		return timeBuilder.toString().trim();
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_KEYWORD, "");
		putOneParameter(PARAMETER_TIME, "");
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_KEYWORD, _keyword);
		
		if (_time != null){
			putOneParameter(PARAMETER_TIME, _time);
		}
	}
	

	@Override
	protected boolean checkIfIncorrectArguments(){
		return false;
	}
	
	/**
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void checkAndInputDeadline(){	
		try {
			_keyword += DateAndTimeManager.getInstance().formatDateAndTimeInString(input);
		} catch (InvalidQuotesException e) {
			InputManager.outputToGui(e.getMessage());
			return;
		}
		
		/*
		for (int i=0; i<splitInput.length; i++){
			dateObject = DateAndTimeManager.getInstance().findDate(splitInput[i].trim());
			if (dateObject != null){
				_keyword += " " + dateObject.getParsedDate();
			}
		}
		*/
	}
}
