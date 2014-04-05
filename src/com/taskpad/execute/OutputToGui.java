package com.taskpad.execute;

import java.awt.Color;
import java.util.LinkedList;

import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;
import com.taskpad.ui.GuiManager;

public class OutputToGui {
	protected static void output(String feedback) {
		GuiManager.callOutputNoLine(feedback + "\n");
	}
	
	protected static void outputTable(LinkedList<Integer> candidates, TaskList listOfTasks) {
		String[][] tableArray = generate2dArrayForTasks(candidates, listOfTasks);
		GuiManager.callTable(tableArray);
	}
	
	protected static String[][] generate2dArrayForTasks(LinkedList<Integer> candidates, TaskList listOfTasks) {
		int numberOfTasks = candidates.size();
		String[][] result = new String[numberOfTasks][6];
		
		int index = 0;
		for(int next: candidates) {
			Task task = listOfTasks.get(index);
			result[index] = fillIn2dArrayForOneTask(index, task);
		}		
		
		return result;
	}
	
	private static String[] fillIn2dArrayForOneTask(int index, Task task) {
		String[] result = new String[6];
		
		int taskId = index + 1;
		
		result[0] = "" + index + 1;
		result[1] = task.getDescription();
		
		if(task.getDeadline() == null || task.getDeadline().equals("")) {
			result[2] = "-";
		} else {
			result[2] = task.getDeadline();
		}
		
		if(task.getStartTime() == null || task.getStartTime().equals("")) {
			if(task.getStartDate() == null || task.getStartDate().equals("")) {
				result[3] = "-";
			} else {
				result[3] = task.getStartDate();
			}
		} else {
			if(task.getStartDate() == null || task.getStartDate().equals("")) {
				result[3] = task.getStartTime();
			} else {
				result[3] = task.getStartTime() + " " + task.getStartDate();
			}			
		}
		
		if(task.getDetails() == null || task.getDetails().equals("")) {
			result[4] = task.getDetails();
		} else {
			result[4] = "-";
		}
		
		if(task.getDone() == 1) {
			result[5] = "Done";
		} else {
			result[5] = "Not done yet";
		}
		
		return result;
	}

	protected static void outputColorTextForTasks(LinkedList<Integer> candidates, TaskList listOfTasks) {
		for(int next: candidates) {
			int taskId = next + 1;
			outputColorTextForOneTask(taskId, listOfTasks.get(next));
			output("\n\n");
		}	
	}
	protected static void outputColorTextForOneTask(int taskId, Task task) {
		String text = "";
		
		text = "Task ID:\t\t" + taskId;
		GuiManager.showSelfDefinedMessage(text, Color.DARK_GRAY, false); 
		/*
		text =  taskId + "\n";
		GuiManager.showSelfDefinedMessage(text, Color.blue, false);
		*/
		
		/*
		text = "Description:\t";
		GuiManager.showSelfDefinedMessage(text, Color.green, true);
		*/
		text = "Description:\t" + task.getDescription();		
		GuiManager.showSelfDefinedMessage(text, Color.MAGENTA, false);
		
		if (task.getDeadline() != null && !task.getDeadline().equals("")) {
			/*
			text = "Deadline:\t";
			GuiManager.showSelfDefinedMessage(text, Color.pink, true);
			*/
			text = "Deadline:\t\t" + task.getDeadline();
			GuiManager.showSelfDefinedMessage(text, Color.orange, false);
		}
		
		String start = "";
		if (task.getStartTime() != null && !task.getStartTime().equals("")) {
			start += task.getStartTime();
		}
		if (task.getStartDate() != null && !task.getStartDate().equals("")) {
			start += (" " + task.getStartDate());
		}
		if (!start.equals("")) {
			/*
			text += "Start:\t";
			GuiManager.showSelfDefinedMessage(text, Color.yellow, true);
			*/
			text = "Start:\t\t" + start;
			GuiManager.showSelfDefinedMessage(text, Color.blue, false);
		}
		
		String end = "";
		if (task.getEndTime() != null && !task.getEndTime().equals("")) {
			end += task.getEndTime();
		}
		if (task.getEndDate() != null && !task.getEndDate().equals("")) {
			end += (" " + task.getEndDate());
		}
		if (!end.equals("")) {
			/*
			text += "End:\t";
			GuiManager.showSelfDefinedMessage(text, Color.orange, true);
			*/
			text = "End:\t\t" + end;
			GuiManager.showSelfDefinedMessage(text, Color.blue, false);
		}
		
		/*
		if (task.getVenue() != null && !task.getVenue().equals("")) {
			text += "Venue: " + task.getVenue() + "\n";
		}
		*/
		
		if (task.getDetails() != null && !task.getDetails().equals("")) {
			/*
			text = "Details:\t";
			GuiManager.showSelfDefinedMessage(text, Color.red, true);
			*/
			text = "Details:\t\t" + task.getDetails();
			GuiManager.showSelfDefinedMessage(text, new Color(76, 0, 153), false);
		}
		
		/*
		text = "Done or not:\t";
		GuiManager.showSelfDefinedMessage(text, Color.gray, true);
		*/
		if (task.getDone() == 0) {
			text = "Status:\t\tNot done.";
		} else {
			text = "Status:\t\tDone.";
		}
		GuiManager.showSelfDefinedMessage(text, Color.blue, true);
		
	}

	protected static void generateFeedbackForAdd(int taskId, Task taskAdded) {
		//return OutputToGui.generateTextForOneTask(taskId, taskAdded);
		OutputToGui.output("Task Successfully Added!\n");
		OutputToGui.outputColorTextForOneTask(taskId, taskAdded);
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
			text += "Not done.";
		} else {
			text += "Done.";
		}
		
		return text;
	}
}
