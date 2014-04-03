package com.taskpad.execute;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

public class Reminder {
	
	protected static void showReminderForToday() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		LinkedList<Integer> tasks = getTasksDueToday();
		OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
	}

	protected static LinkedList<Integer> getTasksDueToday() {
		LinkedList<Integer> results = new LinkedList<Integer>();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = sdf.format(date);
		
		TaskList allTasks = DataManager.retrieve(DataFileStack.FILE);
		for (int i = 0; i < allTasks.size(); i++) {
			Task task = allTasks.get(i);
			String deadline = task.getDeadline();
			if(deadline != null && deadline.equals(dateString)) {
				results.add(i);
			}
		}
		
		return results;
	}
	
}
