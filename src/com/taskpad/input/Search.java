//@author A0119646X

package com.taskpad.input;



import java.util.ArrayList;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidQuotesException;

public class Search extends Command{
	
	private static final String STRING_DELIMITERS = "&";
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
		
		extractTime();
		
		//checkAndInputDeadline();
		
		return true;
	}

	/**
	 * replaced checkAndInputDeadline();
	 * get _time varaible
	 */
	private void extractTime() {
		DateAndTimeManager datm = DateAndTimeManager.getInstance();
		try {
			ArrayList<String> timesArray = datm.searchTimeAndDate(input);
			_time = getTime(timesArray);
		} catch (InvalidQuotesException e) {
			_time = null;
		}
		//System.out.println(_time);
	}

	private String getTime(ArrayList<String> timesArray) {
		StringBuffer timeBuilder = new StringBuffer();
		for (int i = 0; i < timesArray.size(); i++){
			String token = timesArray.get(i);
			timeBuilder.append(token + STRING_DELIMITERS);
		}
		int deleteIdx = timeBuilder.lastIndexOf(STRING_DELIMITERS);
		
		if (deleteIdx > -1){
			timeBuilder.deleteCharAt(deleteIdx);
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
