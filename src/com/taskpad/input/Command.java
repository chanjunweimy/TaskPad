/* 
 * Abstract class for processing the commands 
 */

package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;

public abstract class Command {

	protected static Input inputObject;
	protected static Map<String, String> inputParameters;
	protected static String input;
	
	protected static int NUMBER_ARGUMENTS;
	protected static String COMMAND;
	
	protected static final String MESSAGE_EMPTY_INPUT = "Error: Empty Input";
	protected static final String MESSAGE_INVALID_INPUT = "Error: Invalid input: %s";
	protected static final String MESSAGE_INVALID_PARAMETER_NUMBER = "Error: Invalid number of parameters.\nType help if you need! :)";
	
	public Command(String input){
		Command.input = input;
		inputParameters = new HashMap<String,String>();
	}
	
	public void run(){
		if (checkIfEmptyString() || checkIfIncorrectArguments()){
			return;
		} 
		
		clearInputParameters();
		initialiseParametersToNull();
		if (commandSpecificRun()){
			putInputParameters();
			createInputObject();
			passObjectToExecutor();
		} else {
			return;
		}
	}
	
	protected abstract boolean commandSpecificRun();
	
	protected boolean checkIfEmptyString() {
		if(isEmptyString()){
			InputManager.outputToGui(MESSAGE_EMPTY_INPUT);
			return true;
		}
		return false;
	}
	
	protected abstract void initialiseParametersToNull();
	
	protected boolean checkIfIncorrectArguments(){
		String inputString[] = input.split(" ");
		
		if (isNotNumberArgs(inputString)){
			invalidParameterError();
			return true;
		}
		
		if (isNotValidTaskID(inputString[0])){
			return true;
		}
		
		return false;
	}
	
	protected boolean isNotValidTaskID(String taskID){
		if(isNotInteger(input) || isInvalidID(input)){
			outputIdError();
			return true;
		}
		return false;
	}
	protected Input createInputObject() {
		clearInputParameters();
		putInputParameters();
		inputObject = new Input(getCOMMAND(), inputParameters);		
		return inputObject;
	}

	protected abstract void putInputParameters();
	
	protected static void clearInputParameters(){
		inputParameters.clear();
	}
	
	protected boolean isEmptyString(){
		if (input.isEmpty()){
			return true;
		}
		return false;
	}
	
	protected void putOneParameter(String parameter, String input){
		inputParameters.put(parameter, input);
	}
	
	protected void passObjectToExecutor(){
		InputManager.passToExecutor(inputObject);
	}
	
	protected boolean isNotInteger(String input){
		try{
			Integer.parseInt(input);
		} catch (NumberFormatException e){
			return true;
		}
		return false;
	}
	
	protected boolean isInvalidID(String input){
		int inputNum = Integer.parseInt(input);
		if (inputNum > InputManager.retrieveNumberOfTasks()){
			return true;
		}
		return false;
	}
	
	protected boolean isNotNumberArgs(String[] inputString){
		if (inputString.length != getNUMBER_ARGUMENTS()){
			return true;
		}
		return false;
	}
	
	protected void invalidParameterError(){
		String errorMessage = String.format(MESSAGE_INVALID_PARAMETER_NUMBER);
		InputManager.outputToGui(errorMessage);
	}
	
	protected void outputIdError() {
		String errorMessage = String.format(MESSAGE_INVALID_INPUT, input);
		InputManager.outputToGui(errorMessage);
	}

	protected int getNUMBER_ARGUMENTS() {
		return NUMBER_ARGUMENTS;
	}

	protected void setNUMBER_ARGUMENTS(int nUMBER_ARGUMENTS) {
		NUMBER_ARGUMENTS = nUMBER_ARGUMENTS;
	}

	protected String getCOMMAND() {
		return COMMAND;
	}

	protected void setCOMMAND(String cOMMAND) {
		COMMAND = cOMMAND;
	}
	
}
