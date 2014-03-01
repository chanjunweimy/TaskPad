package com.taskpad.execute;

import java.util.Map;

import com.taskpad.input.Input;

public class ExcecutorManager {

	public ExcecutorManager(){
		
	}
	
	public static void receiveFromInput(Input input){
		String commandType = input.getCommand();
		Map<String, String> parameters = input.getParameters();
	}
	
}
