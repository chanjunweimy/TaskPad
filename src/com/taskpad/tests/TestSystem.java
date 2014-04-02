package com.taskpad.tests;

import com.taskpad.input.InputManager;
import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.TaskList;

public class TestSystem {
	public static void main(String args[]) {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		int oldSize = listOfTasks.size();
		InputManager.receiveFromGui("add watch movie");
		listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		System.out.println(listOfTasks.size());
	}
	
}
