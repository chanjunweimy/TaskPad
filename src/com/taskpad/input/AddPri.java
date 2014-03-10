package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;

public class AddPri extends Command{
	
	private static final String COMMAND_ADDPRI = "ADDPRI";
	private static final int NUMBER_ARGUMENTS = 2;
	
	private static String PARAMETER_PRIORITY_KEY = "KEY";
	private static String parameterPri = "NONE";			//Default no priority 
	
	private static final String[] PARAMETER_VALID_PRI = {"HIGH", "MEDIUM", "LOW", "NONE"};
	private static Map<String, String[]> parametersMap;
	
	private static final String MESSAGE_INVALID_PARAMETER = "Error: Invalid Priority Parameter. Type help if you need! :) ";

	public AddPri(String input, String fullInput) {
		super(input, fullInput);
		setCOMMAND(COMMAND_ADDPRI);
		setNUMBER_ARGUMENTS(NUMBER_ARGUMENTS);
		
		parametersMap = new HashMap<String, String[]>();
		initialiseParametersMap();
	}

	@Override
	protected boolean commandSpecificRun() {
		if (isInvalidPriParameter()){
			outputInvalidParameter();
			return false;
		}else {
			return true;	
		}
	}

	@Override
	protected void initialiseParametersToNull() {
		putOneParameter(PARAMETER_PRIORITY_KEY, parameterPri);
	}

	@Override
	protected void putInputParameters() {
		putOneParameter(PARAMETER_PRIORITY_KEY, parameterPri);
	}

	private void initialiseParametersMap(){
		initialiseNoneVariations();
		initialiseHighVariations();
		initialiseMediumVariations();
		initialiseLowVariations();
	}
	
	private void initialiseNoneVariations(){
		String[] noneVariations = {"NONE", "NO"};
		parametersMap.put(PARAMETER_VALID_PRI[3], noneVariations);
	}
	
	private void initialiseHighVariations(){
		String[] highVariations = {"HIGH", "CRITICAL", "IMPORTANT", "H", "IMPT"};
		parametersMap.put(PARAMETER_VALID_PRI[0], highVariations);
	}
	
	private void initialiseMediumVariations(){
		String[] medVariations = {"MEDIUM", "MED", "M"};
		parametersMap.put(PARAMETER_VALID_PRI[1], medVariations);
	}
	
	private void initialiseLowVariations(){
		String[] lowVariations = {"LOW", "L", "NOTIMPT", "NOTIMPORTANT", "NONCRITICAL"};
		parametersMap.put(PARAMETER_VALID_PRI[2], lowVariations);
	}
	
	private void outputInvalidParameter() {
		InputManager.outputToGui(MESSAGE_INVALID_PARAMETER);
	}

	private boolean isInvalidPriParameter() {
		String priVariations[];
		
		for (Map.Entry<String, String[]> entry : parametersMap.entrySet()){
			priVariations = entry.getValue();
			for (int i=0; i<priVariations.length; i++){
				if (isInputFound(priVariations[i])){
					parameterPri = entry.getKey();
					return false;
				}
			}
		}
		return true;
	}

	private boolean isInputFound(String value) {
		if (value.equalsIgnoreCase(input)){
			return true;
		}
		return false;
	}
}
