//@author A0119646X

/**
 * This class creates a List Object
 * 
 * Current syntax for list: list <argument>
 * 
 * Returns input object
 */



package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;

import com.taskpad.dateandtime.DateAndTimeManager;
import com.taskpad.dateandtime.InvalidDateException;

public class List extends Command{

	private static String COMMAND_LIST = "LIST";
	private static String PARAMETER_LIST_KEY = "KEY";
	private static String PARAMETER_DEADLINE = "DEADLINE";
	private String parameterList = "";
	
	private static final String[] PARAMETER_VALID_LIST = {"ALL", "DONE", "UNDONE"};
	private Map<String, String[]> parametersMap;
	
	private static final String MESSAGE_INVALID_PARAMETER = "Error: Invalid List Parameter. Type help if you need! :)";
	
	private static int NUMBER_ARGUMENTS = 1;
	
	private boolean _isDeadline = false;
	
	public List(String input, String fullInput) {
		super(input, fullInput);
	}
	
	@Override
	protected void initialiseOthers(){
		setCOMMAND(COMMAND_LIST);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		
		parametersMap = new HashMap<String, String[]>();
		initialiseParametersMap();
		_isDeadline = false;
	}

	@Override
	protected boolean commandSpecificRun() {
		//input = DateAndTimeManager.getInstance().formatDateAndTimeInString(input);
		if(checkIfDateline()){
			return true; 
		}
		
		if (isInvalidListParameter()){
			outputInvalidParameter();
			return false;
		}else {
			return true;	
		}
	}

	//Initialise default value
	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_LIST_KEY, "");
		putOneParameter(PARAMETER_DEADLINE, "");
	}

	@Override
	protected void putInputParameters() {
		if (_isDeadline){
			putOneParameter(PARAMETER_DEADLINE, parameterList);		
		} else{
			putOneParameter(PARAMETER_LIST_KEY, parameterList);		
		}
	}
	
	@Override
	protected boolean checkIfIncorrectArguments(){
		return false;
	}	

	/* This method sets the default parameter ALL to the list parameter
	 * 	 
	 * @return false 
	 */
	
	@Override
	protected boolean checkIfEmptyString(){
		if(isEmptyString()){
			input = PARAMETER_VALID_LIST[0];
		}
		return false;
	}
	
	private void initialiseParametersMap(){
		initialiseAllVariations();
		initialiseDoneVariations();
		initialiseUndoneVariations();
	}
	
	private boolean isInvalidListParameter(){
		String listVariations[];
		
		for (Map.Entry<String, String[]> entry : parametersMap.entrySet()){
			listVariations = entry.getValue();
			for (int i=0; i<listVariations.length; i++){
				if (isInputFound(listVariations[i])){
					parameterList = entry.getKey();
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean isInputFound(String value){
		if (value.equalsIgnoreCase(input)){
			return true;
		}
		return false;
	}
	
	/**
	 * For listing by deadline 
	 * Check if Dateline then input into map
	 * @return isDeadline
	 */
	private boolean checkIfDateline() {		
		try {
			String deadline = DateAndTimeManager.getInstance().parseDate(input);
			//putOneParameter(PARAMETER_LIST_KEY, deadline);
			parameterList = deadline;
			_isDeadline = true;
		} catch (InvalidDateException e) {
			//GuiManager.callOutput(e.getMessage());
			_isDeadline = false;
		}
		
		return _isDeadline;
	}
	
	private void initialiseAllVariations(){
		String[] allVariations = {"ALL", "EVERYTHING", "WHOLE", "-A", "-AL"};
		parametersMap.put(PARAMETER_VALID_LIST[0], allVariations);
	}
	
	private void initialiseDoneVariations(){
		String[] doneVariations = {"DONE", "COMPLETED", "FINISHED", "FINISH", "-D"};
		parametersMap.put(PARAMETER_VALID_LIST[1], doneVariations);
	}
	
	private void initialiseUndoneVariations(){
		String[] undoneVariations = {"UNDONE", "INCOMPLETE", "UNFINISH", "UNFINISHED", "-UD"};
		parametersMap.put(PARAMETER_VALID_LIST[2], undoneVariations);
	}
	
	private void outputInvalidParameter(){
		InputManager.outputToGui(MESSAGE_INVALID_PARAMETER);
	}

}
