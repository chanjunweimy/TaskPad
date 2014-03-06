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
		
		String input2 = "addinfo 1 with Jean";
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("TASKID", "1");
		map2.put("INFO", "with Jean");
		Input inputObj2 = new Input("ADDINFO", map2);
		ExecutorManager.receiveFromInput(inputObj2,input2);
	}
}
