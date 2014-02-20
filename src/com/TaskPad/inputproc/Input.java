package com.TaskPad.inputproc;

import java.util.HashMap;
import java.util.Map;

public class Input {
	
	private static Map<String,String> parameters;
	private static String command;
	
	public Input(String command){
		parameters = new HashMap<String, String>();
		this.command = command;
	}
	
	public String getCommand(){
		return this.command;
	}
	
	public Map<String,String> getParameters(){
		return this.parameters;
	}
	
	public void addParameter(String parameter, String value){
		this.parameters.put(parameter, value);
	}
	
}
