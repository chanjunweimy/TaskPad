package com.taskpad.input;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.DateObject;

public class Search extends Command{
	
	private static final String COMMAND_SEARCH = "SEARCH";
	private static final int NUMBER_ARGUMENTS = 1;
	
	private static String PARAMETER_KEYWORD = "KEYWORD";
	private static String _keyword;

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
		
		checkAndInputDeadline();
		
		return true;
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_KEYWORD, "");
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_KEYWORD, _keyword);
	}
	
	@Override
	protected boolean checkIfIncorrectArguments(){
		return false;
	}
	
	private void checkAndInputDeadline(){	
		_keyword += DateAndTimeManager.getInstance().formatDateAndTimeInString(input);
		
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
