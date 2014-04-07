//@author A0105788U

package com.taskpad.execute;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import com.taskpad.input.InputManager;
import com.taskpad.storage.DataFileStack;
import com.taskpad.storage.DataManager;
import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

public class Reminder {
	private static final String FEEDBACK_NO_REMINDER_FOR_TODAY = "No reminders for today.\n";
	private static final String FEEDBACK_NO_TASK_DUE_TODAY = "No tasks due today.\n";
	private static final String FEEDBACK_NO_OVERDUE = "No overdue tasks.\n";
	
	protected static boolean showReminderForToday() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		LinkedList<Integer> tasks = getTasksDueToday();
		if(tasks.size() == 0) {
			// OutputToGui.output(FEEDBACK_NO_TASK_DUE_TODAY);
			return false;
		} else {
			// OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
			OutputToGui.outputTable(tasks, listOfTasks);
			return true;
		}
	}
	
	protected static boolean showReminderForOverdue() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		LinkedList<Integer> tasks = getOverdueTasks();
		
		if(tasks.size() == 0) {
			// OutputToGui.output(FEEDBACK_NO_OVERDUE);
			return false;
		} else {
			// OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
			OutputToGui.outputTable(tasks, listOfTasks);
			return true;
		}
	}
	
	protected static boolean showReminderTasks() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		LinkedList<Integer> tasks = getReminderTasks();
		
		if(tasks.size() == 0) {
			// OutputToGui.output(FEEDBACK_NO_REMINDER_FOR_TODAY);
			return false;
		} else {
			// OutputToGui.outputColorTextForTasks(tasks, listOfTasks);
			OutputToGui.outputTable(tasks, listOfTasks);
			return true;
		}		
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
			if(deadline != null && deadline.contains(dateString)) {
				results.add(i);
			}
		}
		
		return results;
	}
	
	protected static LinkedList<Integer> getOverdueTasks() {
		/*
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String today = sdf.format(date);	
		Task taskDueToday = new Task("dummy", today, "", "", "", "", null);
		*/
		
		TaskList list = DataManager.retrieve(DataFileStack.FILE);
				
		LinkedList<Integer> result = new LinkedList<Integer>();
		
		for(int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			int compareToToday = InputManager.compareDateAndTime(task.getDeadline());
			if(compareToToday == -1 || compareToToday == 0) {
				result.add(i);
			}
		}
		
		return result;
	}

	protected static LinkedList<Integer> getReminderTasks() {
		LinkedList<Integer> results = new LinkedList<Integer>();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateString = sdf.format(date);
		
		TaskList allTasks = DataManager.retrieve(DataFileStack.FILE);
		for (int i = 0; i < allTasks.size(); i++) {
			Task task = allTasks.get(i);
			String reminderDate = task.getReminderDate();
			if(reminderDate != null && reminderDate.contains(dateString)) {
				results.add(i);
			}
		}
		
		return results;
	}
	
}
