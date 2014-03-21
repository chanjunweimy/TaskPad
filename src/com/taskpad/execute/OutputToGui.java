package com.taskpad.execute;

import java.util.LinkedList;

import com.taskpad.data.Task;
import com.taskpad.data.TaskList;

public class OutputToGui {
	protected static String generateTextForTasks(LinkedList<Integer> candidates, TaskList listOfTasks) {
		String text = "";
		for(int next: candidates) {
			int taskId = next + 1;
			text += generateTextForOneTask(taskId, listOfTasks.get(next));
			text += "\n";
		}
		return text;
	}
	
	protected static String generateTitleForOneTask(String taskIdString,
			String description) {
		return taskIdString + ". " + description;
	}
	
	protected static String generateTextForOneTask(int taskId, Task task) {
		String text = "";
		
		text += "Task ID: " + taskId + "\n";
		text += "Description: " + task.getDescription() + "\n";
		
		if (task.getDeadline() != null) {
			text += "Deadline: " + task.getDeadline() + "\n";
		}
		
		String start = "";
		if (task.getStartTime() != null) {
			start += task.getStartTime();
		}
		if (task.getStartDate() != null) {
			start += (" " + task.getStartDate());
		}
		if (!start.equals("")) {
			text += "Start: " + start + "\n";
		}
		
		String end = "";
		if (task.getEndTime() != null) {
			end += task.getEndTime();
		}
		if (task.getEndDate() != null) {
			end += (" " + task.getEndDate());
		}
		if (!end.equals("")) {
			text += "end: " + end + "\n";
		}
		
		if (task.getVenue() != null) {
			text += "Venue: " + task.getVenue() + "\n";
		}
		
		if (task.getDetails() != null) {
			text += "Details: " + task.getDetails() + "\n";
		}
		
		if (task.getDone() == 0) {
			text += "Not done yet.";
		} else {
			text += "Task has been done.";
		}
		
		return text;
	}
}
