package com.taskpad.execute;

import java.util.LinkedList;

import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

public class Reminder {
	public static void showReminder() {
		TaskList tasks = getTasksDueToday();
	}

	private static TaskList getTasksDueToday() {
		TaskList allTasks = DataManager.retrieve(DataFileStack.FILE);
		
		for (int i = 0; i < allTasks.size(); i++) {
			Task task = allTasks.get(i);
			String deadline = task.getDeadline();
			
		}
		
		return null;
	}
}
