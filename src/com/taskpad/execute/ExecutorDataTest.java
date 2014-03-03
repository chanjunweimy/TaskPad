package com.taskpad.execute;

import java.util.HashMap;
import java.util.Map;

import com.taskpad.input.Input;

public class ExecutorDataTest {
	public static void main(String args[]) {
		String input = "add do homework";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("DESC", "do homework");
		Input inputObj = new Input("ADD", map);
		ExecutorManager.receiveFromInput(inputObj,input);
	}
}
