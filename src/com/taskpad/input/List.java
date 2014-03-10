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

public class List extends Command{

	private static String COMMAND_LIST = "LIST";
	private static String PARAMETER_LIST_KEY = "KEY";
	private static String parameterList = "";
	
	private static final String[] PARAMETER_VALID_LIST = {"ALL", "DONE", "UNDONE"};
	private static Map<String, String[]> parametersMap;
	
	private static final String MESSAGE_INVALID_PARAMETER = "Error: Invalid List Parameter. Type help if you need! :)";
	
	private static int NUMBER_ARGUMENTS = 1;
	
	public List(String input, String fullInput) {
		super(input, fullInput);
		setCOMMAND(COMMAND_LIST);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		
		parametersMap = new HashMap<String, String[]>();
		initialiseParametersMap();
	}

	@Override
	protected boolean commandSpecificRun() {
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
		putOneParameter(PARAMETER_LIST_KEY, parameterList);
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_LIST_KEY, parameterList);		
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
	
	private void initialiseAllVariations(){
		String[] allVariations = {"ALL", "EVERYTHING", "WHOLE"};
		parametersMap.put(PARAMETER_VALID_LIST[0], allVariations);
	}
	
	private void initialiseDoneVariations(){
		String[] doneVariations = {"DONE", "COMPLETED", "FINISHED", "FINISH"};
		parametersMap.put(PARAMETER_VALID_LIST[1], doneVariations);
	}
	
	private void initialiseUndoneVariations(){
		String[] undoneVariations = {"UNDONE", "INCOMPLETE", "UNFINISH", "UNFINISHED"};
		parametersMap.put(PARAMETER_VALID_LIST[2], undoneVariations);
	}
	
	private void outputInvalidParameter(){
		InputManager.outputToGui(MESSAGE_INVALID_PARAMETER);
	}

}
