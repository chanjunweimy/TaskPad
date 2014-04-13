//@author A0105788U

package com.taskpad.execute;

import java.awt.Color;
import java.util.LinkedList;

import com.taskpad.storage.Task;
import com.taskpad.storage.TaskList;
import com.taskpad.ui.GuiManager;

/**
 * OutputToGui
 * 
 * This class is to generate and output feedback (in text or in table view) to
 * GUI component
 * 
 */
public class OutputToGui {
	private static final String FEEDBACK_TITLE_FOR_TASK = "%s. %s";
	private static final String FEEDBACK_DELETE = "'%s' deleted";
	private static final int 	TABLE_NUMBER_OF_COLUMNS = 7;
	private static final String TABLE_SKIP = "-";
	private static final String TABLE_UNDONE = "Not done";
	private static final String TABLE_DONE = "Done";

	private static final String STRING_NEWLINE = "\n";
	
	private static final String FEEDBACK_ADD = "Task Successfully Added!\n";


	/**
	 * output
	 * 
	 * This is to output text
	 * 
	 * @param feedback
	 */
	protected static void output(String feedback) {
		GuiManager.callOutputNoLine(feedback + STRING_NEWLINE);
	}
	
	/**
	 * outputTable
	 * 
	 * This is to output in table view
	 * 
	 * @param candidates
	 * @param listOfTasks
	 */
	protected static void outputTable(LinkedList<Integer> candidates,
			TaskList listOfTasks) {
		String[][] tableArray = generate2dArrayForTasks(candidates, listOfTasks);
		GuiManager.callTable(tableArray);
	}

	/**
	 * generate2dArrayForTasks
	 * 
	 * This is to generate 2d-array for tasks in table view
	 * 
	 * @param candidates
	 * @param listOfTasks
	 * @return 2-d array
	 */
	protected static String[][] generate2dArrayForTasks(
			LinkedList<Integer> candidates, TaskList listOfTasks) {
		int numberOfTasks = candidates.size();
		String[][] result = new String[numberOfTasks][TABLE_NUMBER_OF_COLUMNS];

		int index = 0;
		for (int next : candidates) {
			int taskId = next + 1;
			Task task = listOfTasks.get(next);
			result = fillIn2dArrayForOneTask(result, taskId, index, task);
			index++;
		}

		return result;
	}

	private static String[][] fillIn2dArrayForOneTask(String[][] result,
			int taskId, int index, Task task) {
		result[index][0] = "" + taskId;
		result[index][1] = task.getDescription();

		// deadline
		if (task.getDeadline() == null || task.getDeadline().equals("")) {
			result[index][2] = TABLE_SKIP;
		} else {
			result[index][2] = task.getDeadline();
		}

		// start
		if (task.getStartTime() == null || task.getStartTime().equals("")) {
			if (task.getStartDate() == null || task.getStartDate().equals("")) {
				result[index][3] = TABLE_SKIP;
			} else {
				result[index][3] = task.getStartDate();
			}
		} else {
			if (task.getStartDate() == null || task.getStartDate().equals("")) {
				result[index][3] = task.getStartTime();
			} else {
				result[index][3] = task.getStartTime() + " "
						+ task.getStartDate();
			}
		}

		// end
		if (task.getEndTime() == null || task.getEndTime().equals("")) {
			if (task.getEndDate() == null || task.getEndDate().equals("")) {
				result[index][4] = TABLE_SKIP;
			} else {
				result[index][4] = task.getEndDate();
			}
		} else {
			if (task.getEndDate() == null || task.getEndDate().equals("")) {
				result[index][4] = task.getEndTime();
			} else {
				result[index][4] = task.getEndTime() + " " + task.getEndDate();
			}
		}

		// details
		if (task.getDetails() == null || task.getDetails().trim().equals("")) {
			result[index][5] = TABLE_SKIP;
		} else {
			result[index][5] = task.getDetails();
		}

		// status (done or not)
		if (task.getDone() == 1) {
			result[index][6] = TABLE_DONE;
		} else {
			result[index][6] = TABLE_UNDONE;
		}

		return result;
	}

	/**
	 * outputColorTextForTasks
	 * 
	 * This is to output task information with color markup
	 * 
	 * @param candidates
	 * @param listOfTasks
	 */
	protected static void outputColorTextForTasks(
			LinkedList<Integer> candidates, TaskList listOfTasks) {
		for (int next : candidates) {
			int taskId = next + 1;
			outputColorTextForOneTask(taskId, listOfTasks.get(next));
			output("STRING_NEWLINE + STRING_NEWLINE");
		}
	}

	protected static void outputColorTextForOneTask(int taskId, Task task) {
		String text = "";

		// task id
		text = "Task ID:\t\t" + taskId;
		GuiManager.showSelfDefinedMessage(text, new Color(16, 78, 139), false);

		// description
		text = "Description:\t" + task.getDescription();
		GuiManager.showSelfDefinedMessage(text, Color.MAGENTA, false);

		// deadline
		if (task.getDeadline() != null && !task.getDeadline().equals("")) {
			text = "Deadline:\t\t" + task.getDeadline();
			GuiManager.showSelfDefinedMessage(text, new Color(255, 165, 0),
					false);
		}

		// start
		String start = "";
		if (task.getStartTime() != null && !task.getStartTime().equals("")) {
			start += task.getStartTime();
		}
		if (task.getStartDate() != null && !task.getStartDate().equals("")) {
			start += (" " + task.getStartDate());
		}
		if (!start.equals("")) {
			text = "Start:\t\t" + start;
			GuiManager.showSelfDefinedMessage(text, Color.blue, false);
		}

		// end
		String end = "";
		if (task.getEndTime() != null && !task.getEndTime().equals("")) {
			end += task.getEndTime();
		}
		if (task.getEndDate() != null && !task.getEndDate().equals("")) {
			end += (" " + task.getEndDate());
		}
		if (!end.equals("")) {
			text = "End:\t\t" + end;
			GuiManager.showSelfDefinedMessage(text, Color.blue, false);
		}

		if (task.getDetails() != null && !task.getDetails().equals("")) {
			text = "Details:\t\t" + task.getDetails();
			GuiManager.showSelfDefinedMessage(text, new Color(76, 0, 153),
					false);
		}

		// status (done or not)
		if (task.getDone() == 0) {
			text = "Status:\t\tNot done.";
		} else {
			text = "Status:\t\tDone.";
		}
		GuiManager.showSelfDefinedMessage(text, Color.blue, true);

	}

	protected static void generateFeedbackForAdd(int taskId, Task taskAdded) {
		GuiManager.showSelfDefinedMessage(FEEDBACK_ADD, new Color(25,20,147), false);
		OutputToGui.outputColorTextForOneTask(taskId, taskAdded);
	}
	
	protected static String generateFeedbackForDelete(Task taskDeleted) {
		return String.format(FEEDBACK_DELETE, taskDeleted.getDescription());
	}
	
	protected static String generateTextForTasks(LinkedList<Integer> candidates, TaskList listOfTasks) {
		String text = "";
		for(int next: candidates) {
			int taskId = next + 1;
			text += generateTextForOneTask(taskId, listOfTasks.get(next));
			text += STRING_NEWLINE + STRING_NEWLINE;
		}
		return text;
	}
	
	protected static String generateTitleForOneTask(String taskIdString,
			String description) {
		return String.format(FEEDBACK_TITLE_FOR_TASK, taskIdString, description);
	}
	
	/**
	 * generateTextForOneTask
	 * 
	 * This is to generate plain text (without color) including task information
	 * 
	 * @param taskId
	 * @param task
	 * @return generated text
	 */
	protected static String generateTextForOneTask(int taskId, Task task) {
		String text = "";
		
		// task id
		text += "Task ID: " + taskId + STRING_NEWLINE;
		
		// description
		text += "Description: " + task.getDescription() + STRING_NEWLINE;
		
		// deadline
		if (task.getDeadline() != null && !task.getDeadline().equals("")) {
			text += "Deadline: " + task.getDeadline() + STRING_NEWLINE;
		}
		
		// start
		String start = "";
		if (task.getStartTime() != null && !task.getStartTime().equals("")) {
			start += task.getStartTime();
		}
		if (task.getStartDate() != null && !task.getStartDate().equals("")) {
			start += (" " + task.getStartDate());
		}
		if (!start.equals("")) {
			text += "Start: " + start + STRING_NEWLINE;
		}
		
		// end
		String end = "";
		if (task.getEndTime() != null && !task.getEndTime().equals("")) {
			end += task.getEndTime();
		}
		if (task.getEndDate() != null && !task.getEndDate().equals("")) {
			end += (" " + task.getEndDate());
		}
		if (!end.equals("")) {
			text += "End: " + end + STRING_NEWLINE;
		}
		
		// venue
		if (task.getVenue() != null && !task.getVenue().equals("")) {
			text += "Venue: " + task.getVenue() + STRING_NEWLINE;
		}
		
		// details
		if (task.getDetails() != null && !task.getDetails().equals("")) {
			text += "Details: " + task.getDetails() + STRING_NEWLINE;
		}
		
		// status
		if (task.getDone() == 0) {
			text += "Not done.";
		} else {
			text += "Done.";
		}
		
		return text;
	}
}
