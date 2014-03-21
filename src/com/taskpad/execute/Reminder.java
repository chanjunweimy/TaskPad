package com.taskpad.execute;

import java.util.LinkedList;

import com.taskpad.data.DataFileStack;
import com.taskpad.data.DataManager;

public class Reminder {
	public static void showReminder() {
		LinkedList<Task> tasks = getTasksDueToday();
	}

	private static LinkedList<Task> getTasksDueToday() {
		LinkedList<Task> allTasks = DataManager.retrieve(DataFileStack.FILE);
		
		for (Task task: allTasks) {
			String deadline = task.getDeadline();
			
		}
		
		return null;
	}
}
