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

/**
 * Reminder
 * 
 * This class is to show tasks for 'show reminder', including tasks due today,
 * overdue tasks, and tasks attached with reminder (using addrem command)
 * 
 */
public class Reminder {
	private static final String FEEDBACK_NO_REMINDER_FOR_TODAY = "No reminders for today.\n";
	private static final String FEEDBACK_NO_TASK_DUE_TODAY = "No tasks due today.\n";
	private static final String FEEDBACK_NO_OVERDUE = "No overdue tasks.\n";
	
	private static final String FEEDBACK_NOTHING_TO_SHOW = "Nothing to show.";

	/**
	 * showReminder
	 * 
	 * This is to show tasks due today, overdue tasks, and tasks attached with
	 * reminder (using addrem command)
	 * 
	 */
	protected static void showTasksForReminder() {
		TaskList listOfTasks = DataManager.retrieve(DataFileStack.FILE);
		
		LinkedList<Integer> tasksToShow = new LinkedList<Integer>();
		
		boolean haveTasksToShow = false;
		
		haveTasksToShow = Reminder.findReminderForToday(tasksToShow)
				|| haveTasksToShow;
		haveTasksToShow = Reminder.findReminderForOverdue(tasksToShow)
				|| haveTasksToShow;
		haveTasksToShow = Reminder.findReminderTasks(tasksToShow)
				|| haveTasksToShow;
		
		if (!haveTasksToShow) {
			OutputToGui.output(FEEDBACK_NOTHING_TO_SHOW);
		} else {
			OutputToGui.outputTable(tasksToShow, listOfTasks);
		}
	}
	
	protected static boolean findReminderForToday(LinkedList<Integer> tasksToShow) {
		LinkedList<Integer> tasks = getTasksDueToday();
		if (tasks.size() == 0) {
			return false;
		} else {
			addTasks(tasksToShow, tasks);
			return true;
		}
	}

	protected static boolean findReminderForOverdue(LinkedList<Integer> tasksToShow) {
		LinkedList<Integer> tasks = getOverdueTasks();

		if (tasks.size() == 0) {
			return false;
		} else {
			addTasks(tasksToShow, tasks);
			return true;
		}
	}
	
	/**
	 * findReminderTasks
	 * 
	 * This is to show tasks attached with reminder
	 * 
	 * @return Returns false if no task is found, and true otherwise
	 */
	protected static boolean findReminderTasks(LinkedList<Integer> tasksToShow) {
		LinkedList<Integer> tasks = getReminderTasks();

		if (tasks.size() == 0) {
			return false;
		} else {
			addTasks(tasksToShow, tasks);
			return true;
		}
	}

	/**
	 * Add elements in tasks into tasksToShow, without adding duplicates
	 * 
	 * @param tasksToShow
	 * @param tasks
	 */
	private static void addTasks(LinkedList<Integer> tasksToShow,
			LinkedList<Integer> tasks) {
		for (int task : tasks) {
			if (!tasksToShow.contains(task)) {
				tasksToShow.add(task);
			}
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
			
			if (deadline != null && deadline.contains(dateString)) {
				if (task.getDone() == 0) {
					results.add(i);
				}
			}
		}

		return results;
	}
	
	protected static LinkedList<Integer> getOverdueTasks() {
		TaskList list = DataManager.retrieve(DataFileStack.FILE);
				
		LinkedList<Integer> result = new LinkedList<Integer>();
		
		for(int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			
			int compareToToday = InputManager.compareDateAndTime(task.getDeadline());
			if(compareToToday == -1 || compareToToday == 0) {
				if(task.getDone() == 0) {
					result.add(i);
				}
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
				if(task.getDone() == 0) {
					results.add(i);
				}
			}
		}
		
		return results;
	}
	
}
