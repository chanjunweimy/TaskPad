package com.taskpad.execute;

import java.util.LinkedList;

import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;

public class OutputToGui {
	protected static String generateFeedbackForAdd(int taskId, Task taskAdded) {
		return OutputToGui.generateTextForOneTask(taskId, taskAdded);
	}
	
	protected static String generateFeedbackForDelete(Task taskDeleted) {
		return "'" + taskDeleted.getDescription() + "' " + "deleted."; 
	}
	
	protected static String generateTextForTasks(LinkedList<Integer> candidates, TaskList listOfTasks) {
		String text = "";
		for(int next: candidates) {
			int taskId = next + 1;
			text += generateTextForOneTask(taskId, listOfTasks.get(next));
			text += "\n\n";
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
		
		if (task.getDeadline() != null && !task.getDeadline().equals("")) {
			text += "Deadline: " + task.getDeadline() + "\n";
		}
		
		String start = "";
		if (task.getStartTime() != null && !task.getStartTime().equals("")) {
			start += task.getStartTime();
		}
		if (task.getStartDate() != null && !task.getStartDate().equals("")) {
			start += (" " + task.getStartDate());
		}
		if (!start.equals("")) {
			text += "Start: " + start + "\n";
		}
		
		String end = "";
		if (task.getEndTime() != null && !task.getEndTime().equals("")) {
			end += task.getEndTime();
		}
		if (task.getEndDate() != null && !task.getEndDate().equals("")) {
			end += (" " + task.getEndDate());
		}
		if (!end.equals("")) {
			text += "End: " + end + "\n";
		}
		
		if (task.getVenue() != null && !task.getVenue().equals("")) {
			text += "Venue: " + task.getVenue() + "\n";
		}
		
		if (task.getDetails() != null && !task.getDetails().equals("")) {
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
