package com.TaskPad.inputproc;

import java.util.HashMap;
import java.util.Map;

public class Add {
	
	private static String input;
	private static Map<String,String> inputParameters;
	
	private static String MESSAGE_EMPTY_INPUT = "Error: Empty Input. Type 'help' if you need to! :)";
	private static String COMMAND_ADD = "ADD";
	
	private static String PARAMETER_DEADLINE_DAY = "DAY";
	private static String PARAMETER_DEADLINE_MONTH = "MONTH";
	private static String PARAMETER_START = "START";
	private static String PARAMETER_END = "END";
	private static String PARAMETER_VENUE = "VENUE";
	private static String PARAMETER_DESCRIPTION = "DESC";

	public Add(String input){
		this.input = input;
		this.inputParameters = new HashMap<String,String>();
	}
	
	public Map<String,String> run(){
		if (isEmptyString()){
			this.inputParameters.clear();
			return this.inputParameters;
		} 
		
		splitInputParameters();
				
		return this.inputParameters;
	}
	
	private boolean isEmptyString(){
		if (this.input.isEmpty()){
			return true;
		}
		return false;
	}
	
	private void splitInputParameters(){
		
	}
	
}
