package com.taskpad.tests;

//@author A0105788U

import com.taskpad.input.InputManager;
import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.TaskList;

public class TestSystem {
	public static void main(String args[]) {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		int oldSize = listOfTasks.size();
		InputManager.receiveFromGui("add watch movie -d 31/05/2015");
		listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		System.out.println(listOfTasks.size());
	}
}
