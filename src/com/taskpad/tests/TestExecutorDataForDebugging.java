package com.taskpad.tests;

import java.util.HashMap;
import java.util.LinkedList;
//import java.util.Map;




import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.TaskList;
import com.taskpad.execute.ExecutorManager;
import com.taskpad.input.Input;
import com.taskpad.storage.Task;

/**
 * TestExecutorData
 * 
 * This class is for debugging purpose only
 *
 */
public class TestExecutorDataForDebugging {
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
	
	private static void parameterizedStringTest() {
		String LOGGING_ADDING_TASK = "adding task: %s";
		String description = "sth";
		System.out.println(String.format(LOGGING_ADDING_TASK, description));
	}
	
	private static void addTasks() {
		TaskList list = DataManager.retrieve(DataFileStack.FILE);
		for(int i = 0; i < 20; i++) {		
			list.add(new Task("some task", null, null, null, null, null, null));
		}
		DataManager.storeBack(list, DataFileStack.FILE);
	}
	
	public static void main(String args[]) {
		addTasks();
	}
}
