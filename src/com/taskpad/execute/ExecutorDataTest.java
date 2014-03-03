package com.taskpad.execute;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.taskpad.data.DataFile;
import com.taskpad.data.DataManager;
import com.taskpad.input.Input;

public class ExecutorDataTest {
	public static void main(String args[]) {
		LinkedList<Task> tasks = new LinkedList<Task>();
		DataManager.storeBack(tasks, DataFile.FILE);
		DataManager.storeBack(tasks, DataFile.FILE_PREV);
		
		String input = "add do homework";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("DESC", "do homework");
		Input inputObj = new Input("ADD", map);
		ExecutorManager.receiveFromInput(inputObj,input);
		
		String input1 = "ls";
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("KEY", "ALL");
		Input inputObj1 = new Input("LIST", map1);
		ExecutorManager.receiveFromInput(inputObj1,input1);
	}
}
