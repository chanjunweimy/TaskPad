package com.taskpad.tests;

import java.util.HashMap;
import java.util.LinkedList;
//import java.util.Map;



import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.execute.ExecutorManager;
import com.taskpad.input.Input;
import com.taskpad.storage.Task;

public class TestExecutorData {
	private static void commandTest() {
		LinkedList<Task> tasks = new LinkedList<Task>();
		//DataManager.storeBack(tasks, DataFileStack.FILE);
		// DataManager.storeBack(tasks, DataFileStack.FILE_PREV);
		
		String input = "add do homework";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("DESC", "do homework");
		Input inputObj = new Input("ADD", map);
		ExecutorManager.receiveFromInput(inputObj,input);
		
		/*
		String input1 = "ls";
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("KEY", "ALL");
		Input inputObj1 = new Input("LIST", map1);
		ExecutorManager.receiveFromInput(inputObj1,input1);
		
		String input2 = "addinfo 1 with Jean";
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("TASKID", "1");
		map2.put("INFO", "with Jean");
		Input inputObj2 = new Input("ADDINFO", map2);
		ExecutorManager.receiveFromInput(inputObj2,input2);
		*/
		
		String input3 = "undo";
		HashMap<String, String> map3 = new HashMap<String, String>();
		Input inputObj3 = new Input("UNDO", map3);
		ExecutorManager.receiveFromInput(inputObj3,input3);		
		
		String input4 = "add do your homework";
		HashMap<String, String> map4 = new HashMap<String, String>();
		map4.put("DESC", "do your homework");
		Input inputObj4 = new Input("ADD", map4);
		ExecutorManager.receiveFromInput(inputObj4,input4);
		
		String input1 = "ls";
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("KEY", "ALL");
		Input inputObj1 = new Input("LIST", map1);
		ExecutorManager.receiveFromInput(inputObj1,input1);
	}
	
	private static void dataRetrievalTest() {
		DataManager.retrieve("test_data.xml");
	}
	
	private static void dataStoreBackTest() {
		LinkedList<Task> tasks = new LinkedList<Task>();
		//DataManager.storeBack(tasks, "test_data.xml");
	}
	
	public static void main(String args[]) {
		DataManager.retrieve("");
	}
}
