//@author A0105788U

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
		String[][] result = new String[numberOfTasks][7];
		/*
		for(int i = 0; i < numberOfTasks; i++) {
			for(int j = 0; j < 6; j++) {
				result[i][j] = "";
			}
		}
		*/
		int index = 0;
		for(int next: candidates) {
			int taskId = next + 1;
			Task task = listOfTasks.get(next);
			result = fillIn2dArrayForOneTask(result, taskId, index, task);
			index++;
		}		
		
		return result;
	}
	
	private static String[][] fillIn2dArrayForOneTask(String[][] result, int taskId, int index, Task task) {		
		result[index][0] = "" + taskId;
		result[index][1] = task.getDescription();
		
		if(task.getDeadline() == null || task.getDeadline().equals("")) {
			result[index][2] = "-";
		} else {
			result[index][2] = task.getDeadline();
		}
		
		if(task.getStartTime() == null || task.getStartTime().equals("")) {
			if(task.getStartDate() == null || task.getStartDate().equals("")) {
				result[index][3] = "-";
			} else {
				result[index][3] = task.getStartDate();
			}
		} else {
			if(task.getStartDate() == null || task.getStartDate().equals("")) {
				result[index][3] = task.getStartTime();
			} else {
				result[index][3] = task.getStartTime() + " " + task.getStartDate();
			}			
		}

		if(task.getEndTime() == null || task.getEndTime().equals("")) {
			if(task.getEndDate() == null || task.getEndDate().equals("")) {
				result[index][4] = "-";
			} else {
				result[index][4] = task.getEndDate();
			}
		} else {
			if(task.getEndDate() == null || task.getEndDate().equals("")) {
				result[index][4] = task.getEndTime();
			} else {
				result[index][4] = task.getEndTime() + " " + task.getEndDate();
			}			
		}
		
		if(task.getDetails() == null || task.getDetails().trim().equals("")) {
			result[index][5] = "-";
		} else {
			result[index][5] = task.getDetails();
		}
		
		if(task.getDone() == 1) {
			result[index][6] = "Done";
		} else {
			result[index][6] = "Not done";
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
		GuiManager.showSelfDefinedMessage(text, new Color(16,78,139), false); 
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
		//OutputToGui.output("Task Successfully Added!\n");
		GuiManager.showSelfDefinedMessage("Task Successfully Added!\n", new Color(25,20,147), false);
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
