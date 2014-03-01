/* 
 * This class creates a Delete object
 * 
 * Current syntax for delete: del <taskID>
 */

package com.taskpad.input;

import java.util.HashMap;
import java.util.Map;

public class Delete {

	private static Input inputObject;
	private static String input;
	private static Map<String,String> inputParameters;
	
	private static final int LENGTH_DELETE = 1;

	private static final String COMMAND_DELETE = "DELETE";
	private static final String PARAMETER_TASK_ID = "TASKID";
	
	protected Delete(String input){
		this.input = input;
		inputParameters = new HashMap<String, String>();
	}
	
	protected static void run(){		
		if (isValidTaskIDInput(input)){
			inputObject = createDeleteObject(input);
			passObjectToExecutor();
		} else {
			return;
		}
	}
	
	private static Input createDeleteObject(String input) {
		clearInputParameters();
		putInputParameters(PARAMETER_TASK_ID, input);
		inputObject = new Input(COMMAND_DELETE, inputParameters);		
		return inputObject;
	}
	
	private static boolean isValidTaskIDInput(String input){
		String errorMessage = "";
		
//		if (isEmptyInput(input)){
//			errorMessage = String.format(MESSAGE_EMPTY_INPUT);
//			InputManager.outputToGui(errorMessage);
//			return false;
//		}
//		
//		String inputString[] = input.split(" ");
//		
//		if (isNotNumberArgs(inputString)){
//			InputManager.outputToGui(MESSAGE_INVALID_PARAMETER_NUMBER);
//			return false;
//		
//		if(isNotInteger(input) || isInvalidID(input)){
//			outputIdError(input);
//			return false;
//		}
		
		return true;
	}
	
	private static void putInputParameters(String parameter, String input){
		inputParameters.put(parameter, input);
	}
	
	private static void clearInputParameters(){
		inputParameters.clear();
	}
	
	private static void passObjectToExecutor(){
		InputManager.passToExecutor(inputObject);
	}
	
}
