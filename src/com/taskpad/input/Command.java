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
	
	protected Command(String input){
		this.input = input;
		inputParameters = new HashMap<String,String>();
	}
	
	protected void run(){
		exitIfEmptyString();
		initialiseParametersToNull();
	}
	
	private void exitIfEmptyString() {
		// TODO Auto-generated method stub
		
	}
	
	private void initialiseParametersToNull(){
		
	}
	
	private static Input createInputObject() {
		clearInputParameters();
		putInputParameters();
		inputObject = new Input(COMMAND, inputParameters);		
		return inputObject;
	}

	private static void putInputParameters(){
		
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
	
}
