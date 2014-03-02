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
	
	protected Command(String input){
		this.input = input;
		inputParameters = new HashMap<String,String>();
	}
	
	protected void run(){
		exitIfEmptyString();
		initialiseParametersToNull();
		inputObject = commandSpecificRun();
		passObjectToExecutor();
	}
	
	private static Input commandSpecificRun(){
		//Run methods for specific commands 
		Input input = createInputObject();
		return input;
	}
	
	private static void exitIfEmptyString() {
		if(isEmptyString()){
			InputManager.outputToGui(MESSAGE_EMPTY_INPUT);
		}
		
	}
	
	private static void initialiseParametersToNull(){
		//Children methods will initialise these 
	}
	
	private static void checkTaskID(){
		if (isValidTaskIDInput()){
			inputObject = createInputObject();
			passObjectToExecutor();
		} else {
			return;
		}
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
	
}
