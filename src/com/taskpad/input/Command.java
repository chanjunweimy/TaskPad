/* 
 * Abstract class for processing the commands 
 */

package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;

public abstract class Command {

	private static Input inputObject;
	private static Map<String, String> inputParameters;
	private static String input;
	
	private static int NUMBER_ARGUMENTS;
	private static String COMMAND;
	
	private static final String MESSAGE_EMPTY_INPUT = "Error: Empty Input";
	private static final String MESSAGE_INVALID_INPUT = "Error: Invalid input: %s";
	private static final String MESSAGE_INVALID_PARAMETER_NUMBER = "Error: Invalid number of parameters.\nType help if you need! :)";
	
	protected Command(String input){
		this.input = input;
		inputParameters = new HashMap<String,String>();
	}
	
	protected void run(){
		if (checkIfEmptyString() || checkIfIncorrectArguments()){
			return;
		} else {
			initialiseParametersToNull();
			inputObject = commandSpecificRun();
			passObjectToExecutor();
		}
	}
	
	private static Input commandSpecificRun(){
		//Run methods for specific commands 
		Input input = createInputObject();
		return input;
	}
	
	private static boolean checkIfEmptyString() {
		if(isEmptyString()){
			InputManager.outputToGui(MESSAGE_EMPTY_INPUT);
			return true;
		}
		return false;
	}
	
	private static void initialiseParametersToNull(){
		//Children methods will initialise these 
	}
	
	private static boolean checkIfIncorrectArguments(){
		String inputString[] = input.split(" ");
		
		if (isNotNumberArgs(inputString)){
			InputManager.outputToGui(MESSAGE_INVALID_PARAMETER_NUMBER);
			return true;
		}
		
		if(isNotInteger(input) || isInvalidID(input)){
			outputIdError();
			return true;
		}
		return false;
	}
	
	private static Input createInputObject() {
		clearInputParameters();
		putInputParameters();
		inputObject = new Input(COMMAND, inputParameters);		
		return inputObject;
	}

	private static void putInputParameters(){
		//Children will put in input parameters
	}
	
	private static void clearInputParameters(){
		inputParameters.clear();
	}
	
	private static boolean isEmptyString(){
		if (input.isEmpty()){
			return true;
		}
		return false;
	}
	
	private static void putInputParameters(String parameter, String input){
		inputParameters.put(parameter, input);
	}
	
	private static void passObjectToExecutor(){
		InputManager.passToExecutor(inputObject);
	}
	
	private static boolean isNotInteger(String input){
		try{
			Integer.parseInt(input);
		} catch (NumberFormatException e){
			return true;
		}
		return false;
	}
	
	private static boolean isInvalidID(String input){
		int inputNum = Integer.parseInt(input);
		if (inputNum > InputManager.retrieveNumberOfTasks()){
			return true;
		}
		return false;
	}
	
	private static boolean isNotNumberArgs(String[] inputString){
		if (inputString.length != NUMBER_ARGUMENTS){
			return true;
		}
		return false;
	}
	
	private static void outputIdError() {
		String errorMessage = String.format(MESSAGE_INVALID_INPUT, input);
		InputManager.outputToGui(errorMessage);
	}
	
}
