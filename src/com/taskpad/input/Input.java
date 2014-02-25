package com.taskpad.input;

//import java.util.HashMap;
import java.util.Map;

public class Input {
	
	private static Map<String,String> parameters;
	private static String command;
	
	public Input(String command, Map<String,String> parameters){
		Input.parameters = parameters;
		Input.command = command;
	}
	
	public String getCommand(){
		return Input.command;
	}
	
	public Map<String,String> getParameters(){
		return Input.parameters;
	}
	
	public void addParameter(String parameter, String value){
		Input.parameters.put(parameter, value);
	}
	
}
